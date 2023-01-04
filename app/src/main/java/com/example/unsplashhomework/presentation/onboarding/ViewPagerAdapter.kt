package com.example.unsplashhomework.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.databinding.ItemViewpagerBinding

class ViewPagerAdapter(
    private val allOnboardingTexts: Array<String>,
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    /** ctrl+l нажимайте чаще*/

/*    class ViewPagerHolder(private val itemBinding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
       *//** эээ...вот тут я чет тормажу и не понимаю как у тебя это тут работает
        * что то прям странно как то выглядть все это*//*
        fun bind(): TextView {
            return itemBinding.onboardingTexts
        }
    }*/

    class ViewPagerHolder(private val itemBinding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        /** эээ...вот тут я чет тормажу и не понимаю как у тебя это тут работает
         * что то прям странно как то выглядть все это*/
        fun bind(item:String) {
            itemBinding.onboardingTexts.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(
            ItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
       // val item = allOnboardingTexts[position]
        /** аналогичное не понимание проуемсов вот тут*/
       // holder.bind().text = item
        /**сверху перепиыанный метод
         * я понял что ты сделаа , но вопрос, как бы ты поступила если было 10 вьюв в верхнем вьюхлдере? */
        holder.bind(allOnboardingTexts[position])
    }

    override fun getItemCount(): Int {
        return allOnboardingTexts.size
    }
}