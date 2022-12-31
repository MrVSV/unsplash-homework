package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.databinding.FragmentDigestBinding
import com.example.unsplashhomework.domain.model.Digest
import com.example.unsplashhomework.presentation.collections.adapter.DigestPagingAdapter
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestFragment : BaseFragment<FragmentDigestBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentDigestBinding.inflate(inflater)

    private val viewModel by viewModels<DigestViewModel>()
    private val adapter by lazy { DigestPagingAdapter { item -> onClick(item) } }

    private fun onClick(item: Digest){
        findNavController().navigate(
            DigestFragmentDirections.actionNavigationDigestToDigestDetailsFragment(item.id))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        settingAdapter()
        initRefresher()
        loadStateItemsObserve()

    }

    private fun observe(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getDigest().collect {
                adapter.submitData(it)
            }
        }
    }

    private fun settingAdapter() {
        binding.digestRecycler.adapter = adapter
        binding.digestRecycler.itemAnimator?.changeDuration = 0
    }

    private fun refresh(){
        binding.digestRecycler.isVisible = true
        adapter.refresh()
    }

    private fun initRefresher(){
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

// не работает почему-то. пока не стал вникать
    private fun loadStateItemsObserve() {
        adapter.addLoadStateListener { loadState ->
            binding.error.isVisible =
                loadState.refresh is androidx.paging.LoadState.Error
        }
    }
}






