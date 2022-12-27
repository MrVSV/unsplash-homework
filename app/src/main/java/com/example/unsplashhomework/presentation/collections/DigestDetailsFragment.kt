package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.databinding.FragmentDigestDetailsBinding
import com.example.unsplashhomework.tools.BaseFragment

class DigestDetailsFragment : BaseFragment<FragmentDigestDetailsBinding>() {


    private val viewModel by viewModels<DigestDetailsViewModel>()

    override fun initBinding(inflater: LayoutInflater) =
        FragmentDigestDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}