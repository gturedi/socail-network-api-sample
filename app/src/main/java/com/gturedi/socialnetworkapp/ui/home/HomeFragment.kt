package com.gturedi.socialnetworkapp.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.databinding.FragmentHomeBinding
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.toast
import kotlinx.coroutines.flow.collect

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private var checkinsAdapter: CheckinsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.text = getString(if (PrefService.accessToken().isNullOrBlank()) R.string.login else R.string.logout)
        binding.login.setOnClickListener {
            if (PrefService.accessToken().isNullOrBlank()) {
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(requireContext(), Uri.parse(AppConst.AUTH_URL))
            } else {
                PrefService.accessToken("")
                binding.login.text = getString(R.string.login)
                checkinsAdapter?.submitList(mutableListOf())
            }
        }

        if (PrefService.accessToken().isNullOrBlank().not()) {
            getCheckins()
        }

        checkinsAdapter = CheckinsAdapter {
            //findNavController().navigate(R.id.homeToDetail)
            //findNavController().navigate(R.id.DetailFragment, DetailFragmentArgs("4b880ac4f964a52036db31e3").toBundle())
            findNavController().navigate(HomeFragmentDirections.homeToDetail(it.venue.id))
        }
        binding.items.adapter = checkinsAdapter
        binding.items.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        lifecycleScope.launchWhenCreated {
            authViewModel.authCode.collect {
                if (it.isNullOrBlank().not())
                    handleAuthorizationCode(it)
            }
        }
    }

    private suspend fun handleAuthorizationCode(code: String) {
        authViewModel.handleAuthorizationCode(code).collect {
            when (it) {
                is NetworkResult.Loading -> binding.stateful.showLoading()
                is NetworkResult.Success -> {
                    PrefService.accessToken(it.data?.token.orEmpty())
                    binding.login.text = getString(R.string.logout)
                    getCheckins()
                }
                is NetworkResult.Failure -> {
                    binding.stateful.showError(it.message.orEmpty()) {
                        lifecycleScope.launchWhenCreated {
                            handleAuthorizationCode(code)
                        }
                    }
                }
            }
            //toast("handleAuthorizationCode $it")
        }
    }

    private fun getCheckins() {
        lifecycleScope.launchWhenCreated {
            homeViewModel.retrieveCheckins().collect {
                when (it) {
                    is NetworkResult.Loading -> binding.stateful.showLoading()
                    is NetworkResult.Success -> {
                        if (it.data?.response?.checkins?.items?.isNullOrEmpty() == true) {
                            binding.stateful.showError(R.string.errorMessage) {
                                getCheckins()
                            }
                        } else {
                            binding.stateful.showContent()
                            checkinsAdapter?.submitList(it.data?.response?.checkins?.items?.toMutableList())
                        }
                    }
                    is NetworkResult.Failure -> {
                        binding.stateful.showError(it.message.orEmpty()) {
                            getCheckins()
                        }
                    }
                }
                //toast("retrieveCheckins $it")
            }
        }
    }

}