package com.example.thelastnewscanada.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thelastnewscanada.adapters.NewViewModelFactory
import com.example.thelastnewscanada.adapters.SearchesAdapter
import com.example.thelastnewscanada.adapters.TitlesAdapter
import com.example.thelastnewscanada.databinding.FragmentNewsListBinding
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.viewmodels.NewsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

   // private val viewModel by activityViewModels<NewsViewModel>()
    private val viewModel by viewModels<NewsViewModel> { NewViewModelFactory(context = requireContext()) }
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

        /*viewModel.articles.observe(viewLifecycleOwner) { articles ->
            binding.titlesRv.layoutManager = LinearLayoutManager(context)
            binding.titlesRv.adapter = TitlesAdapter(articles)
        }*/

        val article = Article(
            author = "",
            title = "",
            description = "",
            publishedAt = "",
            content = "",
            urlToImage = ""
        )


        val adapter = TitlesAdapter()
        binding.titlesRv.layoutManager = LinearLayoutManager(context)
        binding.titlesRv.adapter = adapter

        lifecycleScope.launch {
            /*viewModel.allArticles.collectLatest { pagingData ->
               adapter.submitData(pagingData)
            }*/

            viewModel.articles.collectLatest { pagingData ->
                adapter.submitData(pagingData = pagingData)
            }
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