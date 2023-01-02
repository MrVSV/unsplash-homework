package com.example.unsplashhomework.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.api.ONBOARDING_IS_SHOWN
import com.example.unsplashhomework.data.api.TOKEN_ENABLED_KEY
import com.example.unsplashhomework.data.api.TOKEN_SHARED_NAME
import com.example.unsplashhomework.databinding.FragmentMainBinding
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun initBinding(inflater: LayoutInflater)=
        FragmentMainBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("fragment", "стартовый")

        val prefs = requireContext().getSharedPreferences(TOKEN_SHARED_NAME, Context.MODE_PRIVATE)
        val toOnboardingFragment = MainFragmentDirections.actionMainFragmentToNavigationOnboarding()
        val toAuthFragment = MainFragmentDirections.actionMainFragmentToAuthFragment()
        val toPhotosFragment = MainFragmentDirections.actionMainFragmentToNavigationPhotos()

        if(!prefs.getBoolean(ONBOARDING_IS_SHOWN, false))
            findNavController().navigate(toOnboardingFragment)
        else{
            if(!prefs.getBoolean(TOKEN_ENABLED_KEY, false))
                findNavController().navigate(toAuthFragment)
            else  findNavController().navigate(toPhotosFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment", "onDestroy: стартовый")
    }

}