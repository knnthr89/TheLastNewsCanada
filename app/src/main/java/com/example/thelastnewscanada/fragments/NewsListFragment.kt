package com.example.thelastnewscanada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thelastnewscanada.adapters.TitlesAdapter
import com.example.thelastnewscanada.databinding.FragmentNewsListBinding
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.viewModels.NewsViewModel

class NewsListFragment : Fragment() {

    private val viewModel by activityViewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentNewsListBinding
            .inflate(inflater, container, false)
            .apply {
               lifecycleOwner = viewLifecycleOwner
            }

        binding.leadersSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshListNews()
        }

        viewModel.articles.observe(viewLifecycleOwner){ articles ->
            binding.titlesRv.layoutManager = LinearLayoutManager(context)
            binding.titlesRv.adapter = TitlesAdapter(articles)
       }

        return binding.root
    }
}