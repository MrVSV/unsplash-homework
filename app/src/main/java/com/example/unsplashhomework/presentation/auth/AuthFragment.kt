package com.example.unsplashhomework.presentation.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.api.TOKEN_SHARED_NAME
import com.example.unsplashhomework.data.api.call
import com.example.unsplashhomework.presentation.auth.state.LoadState
import com.example.unsplashhomework.databinding.FragmentAuthBinding
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override fun initBinding(inflater: LayoutInflater) = FragmentAuthBinding.inflate(inflater)
    private val viewModel by viewModels<AuthViewModel>()
    private val args by navArgs<AuthFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        startAuth()

        tokenObserve(createSharedPreference(TOKEN_SHARED_NAME))

        loadingObserve()

        viewModel.createToken(args.code)

    }

    private fun startAuth(){
        binding.btnAuth.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(call))
            startActivity(intent)
        }
    }

    private fun tokenObserve(preferences: SharedPreferences) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.token.collect { token ->
                preferences.edit().putString("Token", token).apply()
            }
        }
    }

    private fun loadingObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadState ->
                when (loadState) {
                    LoadState.START ->
                        setLoadState(buttonIsEnabled = true, textIsVisible = false, progressIsVisible = false)
                    LoadState.LOADING -> setLoadState(buttonIsEnabled = false, textIsVisible = false, progressIsVisible = true)
                    LoadState.SUCCESS -> {
                        setLoadState(buttonIsEnabled = false, textIsVisible = true, progressIsVisible = false)
                        findNavController().navigate(R.id.action_authFragment_to_navigation_photos)
                    }
                    LoadState.ERROR -> {
                        setLoadState(buttonIsEnabled = true, textIsVisible = true, progressIsVisible = false)
                        binding.text.text = loadState.message
                    }
                }
            }
        }
    }

    private fun setLoadState(
        buttonIsEnabled: Boolean,
        textIsVisible: Boolean,
        progressIsVisible: Boolean
    ) {
        binding.btnAuth.isEnabled = buttonIsEnabled
        binding.text.isVisible = textIsVisible
        binding.progressBar.isVisible = progressIsVisible
    }
}