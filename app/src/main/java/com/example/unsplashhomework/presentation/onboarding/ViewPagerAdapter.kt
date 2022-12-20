package com.example.unsplashhomework.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.databinding.ItemViewpagerBinding

class ViewPagerHolder(val binding: ItemViewpagerBinding) :
    RecyclerView.ViewHolder(binding.root)

class ViewPagerAdapter(
    private val texts: List<String>
) : RecyclerView.Adapter<ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(
            ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val item = texts[position]
        with(holder.binding) {
            onboardingTexts.text = item
        }
    }

    override fun getItemCount(): Int {
        return texts.size
    }
}