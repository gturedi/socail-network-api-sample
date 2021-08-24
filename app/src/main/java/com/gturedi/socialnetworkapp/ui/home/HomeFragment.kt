package com.gturedi.socialnetworkapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.databinding.FragmentHomeBinding
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    //private val authViewModel: AuthViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by viewModel()
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
        observe()
    }

    private fun init() = with(binding) {
        loginBtn.setOnClickListener {
            homeViewModel.loginBtnClicked()
        }

        checkinsAdapter = CheckinsAdapter {
            //findNavController().navigate(R.id.homeToDetail)
            //findNavController().navigate(R.id.DetailFragment, DetailFragmentArgs("4b880ac4f964a52036db31e3").toBundle())
            findNavController().navigate(HomeFragmentDirections.homeToDetail(it.venue.id))
        }
        with(itemsRv) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = checkinsAdapter
        }
    }

    private fun observe() {
        homeViewModel.loginBtnTextResId.observe(viewLifecycleOwner) {
            binding.loginBtn.text = getString(it)
        }
        homeViewModel.checkins.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.stateful.showLoading()
                is Resource.Success -> {
                    if (it.data?.response?.checkins?.items?.isNullOrEmpty() == true) {
                        binding.stateful.showError(R.string.emptyCheckinsMsg, null)
                    } else {
                        binding.stateful.showContent()
                        checkinsAdapter?.submitList(it.data?.response?.checkins?.items?.toMutableList())
                    }
                }
                is Resource.Failure -> {
                    binding.stateful.showError(it.message.orEmpty()) {
                        homeViewModel.getCheckins()
                    }
                }
                is Resource.Empty -> checkinsAdapter?.submitList(mutableListOf())
            }
        }
        authViewModel.authCode.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank().not()) {
                handleAuthorizationCode(it)
            }
        }
    }

    private fun handleAuthorizationCode(code: String) {
        authViewModel.handleAuthorizationCode(code).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.stateful.showLoading()
                is Resource.Success -> {
                    binding.loginBtn.text = getString(R.string.logout)
                    homeViewModel.getCheckins()
                }
                is Resource.Failure -> {
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
