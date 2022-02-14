package com.example.thelastnewscanada.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thelastnewscanada.adapters.SearchesAdapter
import com.example.thelastnewscanada.adapters.TitlesAdapter
import com.example.thelastnewscanada.databinding.FragmentNewsListBinding
import com.example.thelastnewscanada.viewmodels.NewsViewModel

class NewsListFragment : Fragment() {

    private val viewModel by activityViewModels<NewsViewModel>()
    private lateinit var binding: FragmentNewsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNewsListBinding
            .inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }

        binding.newsSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshListNews()
            if (viewModel.errorMessage.value == null || viewModel.errorMessage.value == "") {
                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "Error: ${viewModel.errorMessage.value}\n Try again later!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.newsSwipeRefreshLayout.isRefreshing = false
        }

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            binding.titlesRv.layoutManager = LinearLayoutManager(context)
            binding.titlesRv.adapter = TitlesAdapter(articles)
        }

        viewModel.searches.observe(viewLifecycleOwner) { searches ->
            binding.recentSearchesRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            binding.recentSearchesRv.adapter = SearchesAdapter(searches.asReversed())
        }

        return binding.root
    }

    fun updateNewsList(theme: String) {
        viewModel.refreshListNews(theme)
        binding.titlesRv.adapter?.notifyDataSetChanged()
    }

    fun insertValueToRoom(text : String){
        viewModel.insertSearchToRoom(text)
    }
}