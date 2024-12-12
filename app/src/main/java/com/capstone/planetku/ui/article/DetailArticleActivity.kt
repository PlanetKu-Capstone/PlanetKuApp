package com.capstone.planetku.ui.article

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.planetku.data.DataItem
import com.capstone.planetku.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private val viewModel: ArticleViewModel by viewModels() // Use ViewModelProvider for accessing ViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slug = intent.getStringExtra(EXTRA_SLUG)

        if (!slug.isNullOrEmpty()) {
            fetchArticleDetails(slug)
        } else {
            Toast.makeText(this, "Invalid article slug", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun fetchArticleDetails(slug: String) {
        viewModel.getArticleBySlug(slug)

        viewModel.articleDetail.observe(this) { result ->
            when {
                result.isSuccess -> {
                    val article = result.getOrNull()
                    displayArticleDetails(article)
                    binding.progressBar.visibility = View.GONE
                }
                result.isFailure -> {
                    val errorMessage = result.exceptionOrNull()?.message
                    Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun displayArticleDetails(article: DataItem?) {
        if (article != null) {
            binding.tvTitle.text = Html.fromHtml(article.title ?: "", Html.FROM_HTML_MODE_COMPACT)
            binding.tvContent.text = Html.fromHtml(article.description ?: "", Html.FROM_HTML_MODE_COMPACT)

            Glide.with(this)
                .load("https://planetku.reviens.id/storage/public/${article.image}")
                .centerCrop()
                .into(binding.ivArticle)
        } else {
            Toast.makeText(this, "Article not found", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        const val EXTRA_SLUG = "extra_article_slug"
    }
}

