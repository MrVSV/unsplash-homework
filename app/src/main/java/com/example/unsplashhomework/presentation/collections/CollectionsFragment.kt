package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.databinding.FragmentCollectionsBinding
import com.example.unsplashhomework.tools.BaseFragment

class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentCollectionsBinding.inflate(inflater)

    private val viewModel by viewModels<CollectionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {

    }
}






