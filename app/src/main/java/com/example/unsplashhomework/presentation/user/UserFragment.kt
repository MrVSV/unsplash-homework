package com.example.unsplashhomework.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.databinding.FragmentUserBinding
import com.example.unsplashhomework.tools.BaseFragment

/**это просто заготовка, переименовала Notifications в User
 */
class UserFragment : BaseFragment<FragmentUserBinding>() {

    override fun initBinding(inflater: LayoutInflater)= FragmentUserBinding.inflate(inflater)

    private val viewModel by viewModels<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe(){
        viewModel.text.observe(viewLifecycleOwner){
            binding.textUser.text = it
        }
    }
}