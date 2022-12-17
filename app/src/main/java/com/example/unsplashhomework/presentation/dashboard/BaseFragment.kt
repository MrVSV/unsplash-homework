package com.example.unsplashhomework.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B:ViewBinding>:Fragment() {

    protected var _binding:B?=null
    val binding get()= _binding!!

    abstract fun initBinding(inflater: LayoutInflater):B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initBinding(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}