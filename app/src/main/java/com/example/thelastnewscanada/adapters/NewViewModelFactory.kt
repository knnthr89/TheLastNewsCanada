package com.example.thelastnewscanada.adapters

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.thelastnewscanada.database.NewsDatabase
import com.example.thelastnewscanada.repository.NewsRepository
import com.example.thelastnewscanada.viewmodels.NewsViewModel

class NewViewModelFactory(
    private val context : Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") // Guaranteed to succeed at this point.
            return NewsViewModel(context) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}