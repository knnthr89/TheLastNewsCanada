package com.example.thelastnewscanada.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thelastnewscanada.R
import com.example.thelastnewscanada.models.Article
import com.squareup.picasso.Picasso

class TitlesAdapter(private val articles : List<Article>) : RecyclerView.Adapter<TitlesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_title_item, parent, false))
    }

    override fun onBindViewHolder(holder: TitlesAdapter.ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title

        Picasso.get().load(article.urlToImage).into(holder.image)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.tvTitle)
        val image : ImageView = view.findViewById(R.id.imgNew)
    }
}