package com.example.thelastnewscanada

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.example.thelastnewscanada.databinding.ActivityMainBinding
import com.example.thelastnewscanada.fragments.NewsListFragment
import com.jakewharton.rxbinding.widget.RxTextView
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private var mFragment: NewsListFragment = NewsListFragment()
    private lateinit var textToSave: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        with(activityMainBinding) {
            autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            autocompleteTv.doOnTextChanged { text, start, before, count ->
                if (text.toString().isNotEmpty()) {
                    autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_cross_out,
                        0
                    )
                    mFragment.updateNewsList(text.toString())
                } else {
                    hideKeyboard(view = activityMainBinding.root)
                }
            }

            autocompleteTv.setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= autocompleteTv.right - autocompleteTv.totalPaddingRight) {
                        autocompleteTv.setText("")
                        autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        return@OnTouchListener true
                    }
                }
                false
            })

            RxTextView.textChanges(autocompleteTv)
                .debounce(4, TimeUnit.SECONDS)
                .subscribe {
                    if(autocompleteTv.text.length > 3)
                        mFragment.insertValueToRoom(autocompleteTv.text.toString())
                }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mFragment).commit()

    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}