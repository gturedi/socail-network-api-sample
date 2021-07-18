package com.gturedi.socialnetworkapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gturedi.socialnetworkapp.databinding.FragmentDetailBinding
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.util.loadImageUrl
import com.gturedi.socialnetworkapp.util.openCustomTab
import com.gturedi.socialnetworkapp.util.underline
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelAssistedFactory: DetailViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(viewModelAssistedFactory, args.itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.revenue.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.stateful.showLoading()
                is Resource.Success -> {
                    binding.stateful.showContent()
                    bindData(it)
                }
                is Resource.Failure -> {
                    binding.stateful.showError(it.message) {
                        detailViewModel.getVenue()
                    }
                }
            }
        }
        //detailViewModel.getVenue(args.itemId)
    }

    private fun bindData(it: Resource.Success<SocialNetworkResponse<VenueResponseModel>>) {
        it.data?.response?.venue?.let { x ->
            with(binding) {
                coverIv.loadImageUrl(x.imageUrl())
                nameTv.text = x.name
                with(canonicalUrlTv) {
                    text = x.canonicalUrl
                    underline()
                    setOnClickListener {
                        context?.openCustomTab(x.canonicalUrl.orEmpty())
                    }
                }
                categoriesTv.text = x.categories()
            }
        }
    }
}
