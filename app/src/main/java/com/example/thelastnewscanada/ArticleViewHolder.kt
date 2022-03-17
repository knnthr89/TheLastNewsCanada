package com.example.thelastnewscanada

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.sealeds.ArticleListItem

class ArticleViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder (
    LayoutInflater.from(parent.context).inflate(R.layout.news_title_item, parent, false)
) {
    var article : Article? = null
        private set
    private val nameView = itemView.findViewById<TextView>(R.id.textTitleTv)

    fun bindTo(item: Article?) {
        Log.e("article", "${item?.title}")
        /*if (item is ArticleListItem.Separator) {
            nameView.text = "${item.name} Cheeses"
            nameView.setTypeface(null, Typeface.BOLD)
        } else {
            nameView.text = item?.name
            nameView.setTypeface(null, Typeface.NORMAL)
        }*/
        nameView.text = item?.title
        nameView.setTypeface(null, Typeface.NORMAL)
        article = (item as? ArticleListItem.Item)?.article
        nameView.text = item?.title
    }
}