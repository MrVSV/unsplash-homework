package com.example.unsplashhomework.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.unsplashhomework.databinding.FragmentAuthBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment


private const val CLIENT_ID = "mj_2vu5NRd4iwh6pYzpqOymkmK79_WqclNutm5-O2UQ"
private const val REDIRECT_URI = "unsplashhomework://com.example.unsplashhomework/callback"
private const val RC_AUTH = 1 //для респонс кода



class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentAuthBinding.inflate(inflater)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener() {
            onClick()
        }
    }

    private fun onClick() {

    }
}
