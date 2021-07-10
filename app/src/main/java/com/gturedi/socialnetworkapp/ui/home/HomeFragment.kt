package com.gturedi.socialnetworkapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.databinding.FragmentHomeBinding
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.openCustomTab
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private var checkinsAdapter: CheckinsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        homeViewModel.checkins.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> binding.stateful.showLoading()
                is NetworkResult.Success -> {
                    if (it.data?.response?.checkins?.items?.isNullOrEmpty() == true) {
                        binding.stateful.showError(R.string.errorMessage) {
                            homeViewModel.checkins
                        }
                    } else {
                        binding.stateful.showContent()
                        checkinsAdapter?.submitList(it.data?.response?.checkins?.items?.toMutableList())
                    }
                }
                is NetworkResult.Failure -> {
                    binding.stateful.showError(it.message.orEmpty()) {
                        homeViewModel.checkins
                    }
                }
            }
        }

        authViewModel.authCode.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank().not()) {
                handleAuthorizationCode(it)
            }
        }
    }

    fun init() = with(binding) {
        loginBtn.text = getString(if (authViewModel.getAccessToken().isNullOrBlank()) R.string.login else R.string.logout)
        loginBtn.setOnClickListener {
            if (authViewModel.getAccessToken().isNullOrBlank()) {
                context?.openCustomTab(AppConst.URL_AUTH)
            } else {
                authViewModel.setAccessToken("")
                loginBtn.text = getString(R.string.login)
                checkinsAdapter?.submitList(mutableListOf())
            }
        }

        checkinsAdapter = CheckinsAdapter {
            //findNavController().navigate(R.id.homeToDetail)
            //findNavController().navigate(R.id.DetailFragment, DetailFragmentArgs("4b880ac4f964a52036db31e3").toBundle())
            findNavController().navigate(HomeFragmentDirections.homeToDetail(it.venue.id))
        }
        itemsRv.setHasFixedSize(true)
        itemsRv.adapter = checkinsAdapter
        itemsRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun handleAuthorizationCode(code: String) {
        authViewModel.handleAuthorizationCode(code).observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> binding.stateful.showLoading()
                is NetworkResult.Success -> {
                    authViewModel.setAccessToken(it.data?.token.orEmpty())
                    binding.loginBtn.text = getString(R.string.logout)
                    homeViewModel.checkins
                }
                is NetworkResult.Failure -> {
                    binding.stateful.showError(it.message.orEmpty()) {
                        lifecycleScope.launchWhenCreated {
                            handleAuthorizationCode(code)
                        }
                    }
                }
            }
        }
    }
}
