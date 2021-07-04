package com.gturedi.socialnetworkapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.gturedi.socialnetworkapp.databinding.FragmentFirstBinding
import com.gturedi.socialnetworkapp.util.AppConst

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            //findNavController().navigate(R.id.action_FirstFragment_to_AuthFragment)
            CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(AppConst.AUTH_URL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}