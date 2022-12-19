package com.example.unsplashhomework.presentation

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.data.api.SCOPE
import com.example.unsplashhomework.databinding.FragmentAuthBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment


class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override fun initBinding(inflater: LayoutInflater) = FragmentAuthBinding.inflate(inflater)
    private val viewModel by viewModels<AuthViewModel>()
    private val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://unsplash.com/oauth/authorize" +
                "?client_id=Z3U277lVBSGT_ISIrpUmkhTRsG97PiRgGjAk7UJ8tr8" +
                "&redirect_uri=unsplashhomework://com.example.unsplashhomework/callback" +
                "&response_type=code" +
                "&scope="+SCOPE)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        binding.btnAuth.setOnClickListener {
            startActivity(intent)

        }


    }


}