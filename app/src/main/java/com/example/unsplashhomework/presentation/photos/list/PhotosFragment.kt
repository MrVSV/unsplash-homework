package com.example.unsplashhomework.presentation.photos.list

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.presentation.photos.list.adapter.ClickableView
import com.example.unsplashhomework.presentation.photos.list.adapter.PagingPhotoAdapter
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()

    private val adapter by lazy {
        PagingPhotoAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        when (buttonState) {
            ClickableView.PHOTO -> {
                findNavController().navigate(
                    PhotosFragmentDirections.actionNavigationPhotosToNavigationPhotoDetails(
                        item.id
                    )
                )
            }
            ClickableView.LIKE -> {
                viewModel.like(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_menu, menu)
                val search = menu.findItem(R.id.search_tool)
                val searchView = search.actionView as SearchView
                searchView.setOnQueryTextListener(object : OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        Log.d(TAG, "onQueryTextSubmit: $query")
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {



                Log.d(TAG, "onMenuItemSelected: ")
                return true
            }
        })

        observe()
        binding.photoRecycler.adapter = adapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect {
                adapter.submitData(it)
            }
        }
    }
}
