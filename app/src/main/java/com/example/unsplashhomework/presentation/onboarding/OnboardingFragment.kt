package com.example.unsplashhomework.presentation.onboarding

import android.graphics.BitmapFactory
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
        /** весь список можно вынести в рессуры и не создавать его тут а просто к переменно присвоть
         * список из ресурсов */
        val texts =
            listOf(
                getString(R.string.first_onboarding),
                getString(R.string.second_onboarding),
                getString(R.string.third_onboarding)
            )

        val adapter = ViewPagerAdapter(texts)
        /** я уже вроде писал что биндинг нужен для того что бы вот так не писать
         * меньше переменных всегда лучше каждое объявленная переменная отъедает память */
        val viewPager = binding.viewPager
        val tabs = binding.tabs
        /** вот тут можно написать сразу binding.viewPager.adapter = ViewPagerAdapter(texts)
         * тем самым мы избавимся от переменной адаптер которую мы больше нигде не юзаем */
        viewPager.adapter = adapter

        /** вариант рабочий но мы тут дергаем ContextCompat не страшно,
         * тут не какойто сложный таб, просто точка я как пример передел хml файл и запись в коде
         * сократиться*/

/*
        TabLayoutMediator(tabs, viewPager) { tab, _ ->
            tab.icon = ContextCompat.getDrawable(
                requireContext().applicationContext,
                R.drawable.ic_baseline_circle_24
            )
        }.attach()*/
        /** опять такж не стесняйся юзать биндинг это лучше чем переменную объявлять переменную
         * с темже успехом можно findViewByID юзать*/
        TabLayoutMediator(tabs, viewPager) { _, _ -> }.attach()


        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }
}