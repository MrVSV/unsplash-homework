package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.databinding.FragmentDashboardBinding

class DashboardExampleFragment:BaseFragment<FragmentDashboardBinding>() {

    override fun initBinding(inflater: LayoutInflater)= FragmentDashboardBinding.inflate(inflater)

   private val viewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

   private fun observe(){
        viewModel.text.observe(viewLifecycleOwner){
            binding.textDashboard.text = it
        }
    }

}




