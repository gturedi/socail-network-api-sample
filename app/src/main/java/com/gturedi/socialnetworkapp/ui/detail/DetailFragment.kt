package com.gturedi.socialnetworkapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.databinding.FragmentDetailBinding
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.ui.BaseFragment
import com.gturedi.socialnetworkapp.util.openCustomTab
import com.gturedi.socialnetworkapp.util.underline
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

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
                is NetworkResult.Loading -> binding.stateful.showLoading()
                is NetworkResult.Success -> {
                    binding.stateful.showContent()
                    bindData(it)
                }
                is NetworkResult.Failure -> {
                    binding.stateful.showError(it.message, null)
                }
            }
        }
        detailViewModel.retrieveVenue(args.itemId)
    }

    private fun bindData(it: NetworkResult.Success<SocialNetworkResponse<VenueResponseModel>>) {
        it.data?.response?.venue?.let { x ->
            with(binding) {
                Picasso.get()
                    .load(x.imageUrl())
                    .placeholder(R.drawable.ic_baseline_arrow_circle_down_24)
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(binding.image)

                name.text = x.name

                canonicalUrl.text = x.canonicalUrl
                canonicalUrl.underline()
                canonicalUrl.setOnClickListener {
                    context?.openCustomTab(x.canonicalUrl.orEmpty())
                }
                categories.text = x.categories()
            }
        }
    }

}