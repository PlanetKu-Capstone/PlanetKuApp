package com.capstone.planetku.ui.article

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.planetku.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        viewModel.fetchArticles()
        viewModel.fetchLatestArticles()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter { article ->
            startActivity(Intent(requireContext(), DetailArticleActivity::class.java).apply {
                putExtra(DetailArticleActivity.EXTRA_SLUG, article.slug)
            })
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupObservers() {
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            binding.progressBar.visibility = View.GONE
            articleAdapter.submitList(articles)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.latestArticles.observe(viewLifecycleOwner) { result ->
            when {
                result.isSuccess -> {
                    val articles = result.getOrNull()
                    articles?.let {
                        Log.d("LatestArticles", "Latest articles: $articles")
                    }
                }
                result.isFailure -> {
                    val error = result.exceptionOrNull()?.message
                    Toast.makeText(requireContext(), "Error fetching latest articles: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}