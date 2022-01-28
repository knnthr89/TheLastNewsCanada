package com.example.thelastnewscanada.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thelastnewscanada.adapters.TitlesAdapter
import com.example.thelastnewscanada.databinding.FragmentNewsListBinding
import com.example.thelastnewscanada.repository.NewsRepository
import com.example.thelastnewscanada.viewmodels.NewsViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener


class NewsListFragment : Fragment() {

    private val viewModel by activityViewModels<NewsViewModel>()
    private lateinit var binding : FragmentNewsListBinding

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

        binding.leadersSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshListNews()
            Thread.sleep(3000)
            if(viewModel.errorMessage.value!!.isNullOrBlank()){
               Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Error: ${viewModel.errorMessage.value}\n Try again later!", Toast.LENGTH_SHORT).show()
            }
            binding.leadersSwipeRefreshLayout.isRefreshing = false
        }

        viewModel.articles.observe(viewLifecycleOwner){ articles ->
            binding.titlesRv.layoutManager = LinearLayoutManager(context)
            binding.titlesRv.adapter = TitlesAdapter(articles)
       }

        return binding.root
    }

    fun updateNewsList(theme : String){
        viewModel.refreshListNews(theme)
        binding.titlesRv.adapter?.notifyDataSetChanged()
    }
}