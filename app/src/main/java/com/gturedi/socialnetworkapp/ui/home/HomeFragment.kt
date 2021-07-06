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
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.databinding.FragmentHomeBinding
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.util.AppConst
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

        binding.token.text = AppConst.accessToken

        checkinsAdapter = CheckinsAdapter {
            //toast("item $it")
            //findNavController().navigate(R.id.homeToDetail)
            //findNavController().navigate(R.id.DetailFragment, DetailFragmentArgs("4b880ac4f964a52036db31e3").toBundle())
            findNavController().navigate(HomeFragmentDirections.homeToDetail(it.venue.id))
        }
        binding.items.adapter = checkinsAdapter

        lifecycleScope.launchWhenCreated {
            authViewModel.authCode.collect {
                toast("frg code $it")
                //viewModel.handleAuthorizationCode(it)
                authViewModel.handleAuthorizationCode(it).collect {
                    when(it) {
                        is NetworkResult.Loading -> showLoading()
                        is NetworkResult.Success -> {
                            hideLoading()
                            //toast("token ${it.data}")
                            //todo save to pref
                            AppConst.accessToken = it.data?.token.orEmpty()
                            binding.token.text = AppConst.accessToken
                        }
                        is NetworkResult.Failure -> {
                            hideLoading()
                            toast("err ${it.message}")
                        }
                    }
                    toast("handleAuthorizationCode $it")
                }
            }
        }

        binding.buttonFirst.setOnClickListener {
            CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(AppConst.AUTH_URL))
        }

        binding.buttonSecond.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                homeViewModel.retrieveCheckins().collect {
                    when(it) {
                        is NetworkResult.Loading -> showLoading()
                        is NetworkResult.Success -> {
                            hideLoading()
                            //toast("items ${it.data}")
                            //todo empty list
                            checkinsAdapter?.submitList(it.data?.response?.checkins?.items?.toMutableList())
                        }
                        is NetworkResult.Failure -> {
                            hideLoading()
                            toast("err ${it.message}")
                        }
                    }
                    //toast("retrieveCheckins $it")
                }
            }
        }
    }

}