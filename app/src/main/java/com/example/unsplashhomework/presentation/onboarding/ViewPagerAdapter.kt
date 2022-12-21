package com.example.unsplashhomework.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.databinding.ItemViewpagerBinding

class ViewPagerAdapter(
    private val allOnboardingTexts: Array<String>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    class ViewPagerHolder(private val itemBinding: ItemViewpagerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(): TextView {
        return itemBinding.onboardingTexts
    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(
            ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val item = allOnboardingTexts[position]
        holder.bind().text = item
    }

    override fun getItemCount(): Int {
        return allOnboardingTexts.size
    }
}