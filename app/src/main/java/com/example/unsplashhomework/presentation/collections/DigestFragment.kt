package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.model.Digest
import com.example.unsplashhomework.databinding.FragmentDigestBinding
import com.example.unsplashhomework.presentation.collections.adapter.DigestPagingAdapter
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestFragment : BaseFragment<FragmentDigestBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentDigestBinding.inflate(inflater)

    private val viewModel by viewModels<DigestViewModel>()
    private val adapter by lazy { DigestPagingAdapter { item -> onClick(item) } }

    private fun onClick(item: Digest){
        findNavController().navigate(DigestFragmentDirections.actionNavigationDigestToDigestDetailsFragment())
        Toast.makeText(requireContext(), "есть пробитие", Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDigest()
        binding.digestRecycler.adapter = adapter

        observe()

    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.a.collect {
                adapter.submitData(it)
            }
        }
    }

}






