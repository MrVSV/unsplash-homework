package com.example.unsplashhomework.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.R
import com.example.unsplashhomework.databinding.FragmentOnboardingBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentOnboardingBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val texts =
            listOf(
                getString(R.string.first_onboarding),
                getString(R.string.second_onboarding),
                getString(R.string.third_onboarding)
            )
        val adapter = ViewPagerAdapter(texts)
        val viewPager = binding.viewPager
        val tabs = binding.tabs

        viewPager.adapter = adapter

        TabLayoutMediator(tabs, viewPager) { tab, _ ->
            tab.icon = ContextCompat.getDrawable(
                requireContext().applicationContext,
                R.drawable.ic_baseline_circle_24
            )
        }.attach()

        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }
}