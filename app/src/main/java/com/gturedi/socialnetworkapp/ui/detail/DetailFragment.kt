package com.gturedi.socialnetworkapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gturedi.socialnetworkapp.databinding.FragmentDetailBinding
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.util.toast
import kotlinx.coroutines.flow.collect

class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            detailViewModel.retrieveVenue(args.itemId).collect {
                when(it) {
                    is NetworkResult.Loading -> showLoading()
                    is NetworkResult.Success -> {
                        hideLoading()
                        //toast("items ${it.data}")
                        it.data?.response?.venue?.let { x ->
                            binding.name.text = x.name
                            binding.url.text = x.url
                            binding.canonicalUrl.text = x.canonicalUrl
                            binding.categories.text = x.categories.joinToString(", ") { y -> y.name }
                        }
                    }
                    is NetworkResult.Failure -> {
                        hideLoading()
                        toast("err ${it.message}")
                    }
                }
                //toast("retrieveVenue $it")
            }
        }
    }

}