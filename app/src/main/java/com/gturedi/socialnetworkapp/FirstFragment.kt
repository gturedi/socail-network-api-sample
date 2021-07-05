package com.gturedi.socialnetworkapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.gturedi.socialnetworkapp.databinding.FragmentFirstBinding
import com.gturedi.socialnetworkapp.network.NetworkResult
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.toast
import kotlinx.coroutines.flow.collect

class FirstFragment : BaseFragment() {

    private lateinit var binding: FragmentFirstBinding
    //private val viewModel: MainViewModel by viewModels()
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launchWhenCreated {
            viewModel.authCode.collect {
                toast("frg code $it")
                //viewModel.handleAuthorizationCode(it)
                viewModel.handleAuthorizationCode2(it).collect {
                    when(it) {
                        is NetworkResult.Loading -> showLoading()
                        is NetworkResult.Success -> {
                            hideLoading()
                            toast("token ${it.data}")
                        }
                        is NetworkResult.Failure -> {
                            hideLoading()
                            toast("err ${it.error}")
                        }
                    }
                    toast("frg token $it")
                }
            }
        }

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //findNavController().navigate(R.id.action_FirstFragment_to_AuthFragment)
            CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(AppConst.AUTH_URL))
        }
    }

}