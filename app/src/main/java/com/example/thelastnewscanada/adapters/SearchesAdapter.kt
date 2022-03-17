package com.example.thelastnewscanada.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thelastnewscanada.ArticleViewHolder
import com.example.thelastnewscanada.R
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.models.Search
import com.example.thelastnewscanada.sealeds.ArticleListItem

class SearchesAdapter(private val searches : List<Search>) : RecyclerView.Adapter<SearchesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchesAdapter.ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.searches_item, parent, false))
    }

    override fun onBindViewHolder(holder: SearchesAdapter.ViewHolder, position: Int) {
        val item = searches[position]

        holder.searchTv.text = item.name
    }

    override fun getItemCount(): Int {
        return searches.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val searchTv : TextView = view.findViewById(R.id.itemTv)
    }
}