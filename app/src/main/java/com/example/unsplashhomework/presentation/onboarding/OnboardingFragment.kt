package com.example.unsplashhomework.presentation.onboarding

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.api.ONBOARDING_IS_SHOWN
import com.example.unsplashhomework.data.api.TOKEN_SHARED_NAME
import com.example.unsplashhomework.databinding.FragmentOnboardingBinding
import com.example.unsplashhomework.tools.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentOnboardingBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("fragment", "онбординг")

        binding.viewPager.adapter =
            ViewPagerAdapter(resources.getStringArray(R.array.onboarding_texts_array))

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    binding.ellipseImage
                        .translationY = (positionOffset+position)*100
                }
            }
        )

        TabLayoutMediator(binding.tabs, binding.viewPager) { _, _ -> }.attach()

        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }

        val prefs = requireContext().getSharedPreferences(TOKEN_SHARED_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(ONBOARDING_IS_SHOWN, true).apply()
        Log.d(TAG, "onboarding is shown: ${prefs.getBoolean(ONBOARDING_IS_SHOWN, false)}")
    }
}