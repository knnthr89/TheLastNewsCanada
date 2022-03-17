package com.example.thelastnewscanada.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thelastnewscanada.ArticleViewHolder
import com.example.thelastnewscanada.R
import com.example.thelastnewscanada.models.Article
import com.example.thelastnewscanada.sealeds.ArticleListItem
import com.squareup.picasso.Picasso

/*class TitlesAdapter(private val articles : List<Article>) : RecyclerView.Adapter<TitlesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_title_item, parent, false))
    }

    override fun onBindViewHolder(holder: TitlesAdapter.ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title

        if(article.urlToImage.isNotBlank())
            Picasso.get().load(article.urlToImage).into(holder.image)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.textTitleTv)
        val image : ImageView = view.findViewById(R.id.imgNew)
    }
}*/

class TitlesAdapter : PagingDataAdapter<Article, ArticleViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(parent)
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         *
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see DiffUtil
         */
        val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return if (oldItem is Article && newItem is Article) {
                    oldItem.id == newItem.id
                }/* else if (oldItem is ArticleListItem.Separator && newItem is ArticleListItem.Separator) {
                    oldItem.name == newItem.name
                }*/ else {
                    oldItem == newItem
                }
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}