package com.example.thelastnewscanada.extensions

import android.app.Application
import android.content.Context
import com.example.thelastnewscanada.R
import com.example.thelastnewscanada.enums.ResultStatus

fun ResultStatus.getErrorMessage(context: Context) = when (this) {
    ResultStatus.NetworkException -> context.resources.getString(R.string.network_exception_message)
    ResultStatus.RequestException -> context.resources.getString(R.string.request_exception_message)
    ResultStatus.GeneralException -> context.resources.getString(R.string.general_exception_message)
    ResultStatus.NotFoudResult -> context.resources.getString(R.string.not_found_exception_message)
    else -> null
}