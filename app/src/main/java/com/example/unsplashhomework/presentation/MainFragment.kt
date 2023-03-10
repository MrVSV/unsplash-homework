package com.example.unsplashhomework.presentation

import android.os.Bundle
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

        val prefs = createSharedPreference(TOKEN_SHARED_NAME)
        val toOnboardingFragment = MainFragmentDirections.actionMainFragmentToNavigationOnboarding()
        val toAuthFragment = MainFragmentDirections.actionMainFragmentToAuthFragment()
        val toPhotosFragment = MainFragmentDirections.actionMainFragmentToNavigationPhotos()

//        binding.auth.setOnClickListener {
//            findNavController().navigate(toAuthFragment)
//        }
//
//        binding.onBoarding.setOnClickListener {
//            findNavController().navigate(toOnboardingFragment)
//        }
//
//        binding.asShouldBe.setOnClickListener {
            if (!prefs.getBoolean(ONBOARDING_IS_SHOWN, false))
                findNavController().navigate(toOnboardingFragment)
            else {
                if (!prefs.getBoolean(TOKEN_ENABLED_KEY, false))
                    findNavController().navigate(toAuthFragment)
                else findNavController().navigate(toPhotosFragment)
            }
//        }
    }
}