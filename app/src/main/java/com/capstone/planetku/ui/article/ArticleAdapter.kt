package com.capstone.planetku.ui.article

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.planetku.data.DataItem
import com.capstone.planetku.databinding.ItemArticleBinding

class ArticleAdapter(
    private val onItemClick: (DataItem) -> Unit
) : ListAdapter<DataItem, ArticleAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(article: DataItem) {
            binding.apply {

                tvTitle.text = Html.fromHtml(article.title ?: "", Html.FROM_HTML_MODE_COMPACT)
                tvExcerpt.text = Html.fromHtml(article.excerpt ?: "", Html.FROM_HTML_MODE_COMPACT)

                Glide.with(itemView.context)
                    .load("https://planetku.reviens.id/storage/public/${article.image}")
                    .centerCrop()
                    .into(ivArticle)
            }
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}