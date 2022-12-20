package com.example.unsplashhomework.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.databinding.ItemViewpagerBinding

/** смотри ниже...binding должен быть приватным
 * ия бы всетаки для холера отдельный класс создал*/

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
        /** вот тут не надо так делать... у тебя во вью холдера должна быьть просто функция bind
         * которя делать тоже самое а тут запись должна быть holder.bind а binding вообще должен
         * быть приватным приватный
         * и смотри езще момент у тебя действие вот тут конкретно в одну строчку зачем тут лишняя
         * вложеность в виде with? если у тебя 7 действий с биндингом допустим то так даже нужно
         * тут нет*/
        with(holder.binding) {
            onboardingTexts.text = item
        }
    }

    /** ну это на вкус и цвет как говориться
     *  override fun getItemCount(): =texts.size
     *  такая запись лично мне нравится больше...А там смотрите сами. В проекте должно быть все
     *  написано однотипно*/
    override fun getItemCount(): Int {
        return texts.size
    }

}