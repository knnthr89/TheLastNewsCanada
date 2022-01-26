package com.example.thelastnewscanada.extensions

import android.app.Application
import com.example.thelastnewscanada.R
import com.example.thelastnewscanada.repository.NewsRepository.ResultStatus

fun ResultStatus.getErrorMessage(application: Application) = when (this) {
    ResultStatus.NetworkException -> application.resources.getString(R.string.network_exception_message)
    ResultStatus.RequestException -> application.resources.getString(R.string.request_exception_message)
    ResultStatus.GeneralException -> application.resources.getString(R.string.general_exception_message)
    else -> null
}