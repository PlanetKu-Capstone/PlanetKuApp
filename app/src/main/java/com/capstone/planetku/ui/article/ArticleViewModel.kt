package com.capstone.planetku.ui.article

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.planetku.data.ArticleRepository
import com.capstone.planetku.data.DataItem
import kotlinx.coroutines.launch

class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository()

    private val _articles = MutableLiveData<List<DataItem>>()
    val articles: LiveData<List<DataItem>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _articleDetail = MutableLiveData<Result<DataItem>>()
    val articleDetail: LiveData<Result<DataItem>> get() = _articleDetail

    fun fetchArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getArticles()
                .onSuccess { articleList ->
                    _articles.value = articleList
                }
                .onFailure { exception ->
                    _error.value = exception.message
                }

            _isLoading.value = false
        }
    }

    private val _latestArticles = MutableLiveData<Result<List<DataItem>>>()
    val latestArticles: LiveData<Result<List<DataItem>>> get() = _latestArticles

    fun fetchLatestArticles() {
        viewModelScope.launch {
            val result = repository.getLatestArticles()
            _latestArticles.postValue(result)
        }
    }

    fun getArticleBySlug(slug: String): LiveData<Result<DataItem>> {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getArticleBySlug(slug)
            _articleDetail.postValue(result)

            _isLoading.value = false
        }
        return _articleDetail
    }
}