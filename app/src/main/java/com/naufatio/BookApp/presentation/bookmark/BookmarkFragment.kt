package com.naufatio.BookApp.presentation.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufatio.BookApp.data.local.room.Book
import com.naufatio.BookApp.databinding.FragmentBookmarkBinding
import com.naufatio.BookApp.notification.NotificationService
import com.naufatio.BookApp.presentation.bookmark.adapter.BookmarkAdapter

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: BookmarkViewModel? = null
    private val viewModel get() = _viewModel as BookmarkViewModel


    private lateinit var mAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        _viewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
        mAdapter = BookmarkAdapter()

        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        viewModel.getBookmark().observe(viewLifecycleOwner){
            checkDataIsEmpty(it)
            mAdapter.setData(it)
        }
        binding.rvBookmark.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun checkDataIsEmpty(data: List<Book>) {
        binding.apply {
            if (data.isEmpty()) {
                imgEmptyBookmark.visibility = View.VISIBLE
                rvBookmark.visibility = View.INVISIBLE
            } else {
                imgEmptyBookmark.visibility = View.INVISIBLE
                rvBookmark.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}