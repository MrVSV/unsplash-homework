package com.example.unsplashhomework.presentation.auth

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.unsplashhomework.databinding.FragmentAuthBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment
import com.example.unsplashhomework.presentation.user.NotificationsFragment
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues


private const val CLIENT_ID = "mj_2vu5NRd4iwh6pYzpqOymkmK79_WqclNutm5-O2UQ"
private const val REDIRECT_URI = "unsplashhomework://com.example.unsplashhomework/callback"
private const val RC_AUTH = 1 //для респонс кода

var serviceConfig = AuthorizationServiceConfiguration(
    Uri.parse("https://unsplash.com/oauth/authorize"),  // authorization endpoint
    Uri.parse("https://unsplash.com/oauth/token")
) // token endpoint


class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentAuthBinding.inflate(inflater)

    val authRequestBuilder = AuthorizationRequest.Builder(
        serviceConfig,  // the authorization service configuration
        CLIENT_ID,  // the client ID, typically pre-registered and static
        ResponseTypeValues.CODE,  // the response_type value: we want a code
        Uri.parse(REDIRECT_URI)
    ) // the redirect URI to which the auth response is sent

    val authRequest = authRequestBuilder
        .setScope("public read_user read_photos write_likes write_followers read_collections")
        //.setLoginHint("jdoe@user.example.com")
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener() {
            onClick()
        }
    }

    private fun onClick() {

        val intent = Intent(requireActivity().applicationContext, NotificationsFragment::class.java)

        val authService = AuthorizationService(requireContext())

        authService.performAuthorizationRequest(
            authRequest,
            PendingIntent.getActivity(requireContext(), 0, intent, 0),
            PendingIntent.getActivity(requireContext(), 0, intent, 0)
        )

    }
}
