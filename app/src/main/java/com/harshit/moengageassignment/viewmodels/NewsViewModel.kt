package com.harshit.moengageassignment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshit.moengageassignment.models.Article
import com.harshit.moengageassignment.repositories.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//  ViewModel for managing news data.
class NewsViewModel:ViewModel() {
    private val repository = Repository()
    private val _newsList = MutableLiveData<List<Article>>()

//  Making the newsList as LiveData to observe changes
    val newsList: LiveData<List<Article>> = _newsList
    init {
        fetchNews()
    }
//  Fetches news data from the repository
    private fun fetchNews(){
        viewModelScope.launch (Dispatchers.Default){
            val result = repository.fetchNews()
            if (result.isNotEmpty()) {
                _newsList.postValue(result)
            }
        }
    }
}