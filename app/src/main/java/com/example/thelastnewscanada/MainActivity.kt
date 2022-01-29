package com.example.thelastnewscanada

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.thelastnewscanada.databinding.ActivityMainBinding;
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.thelastnewscanada.fragments.NewsListFragment
import android.widget.Toast

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import androidx.core.widget.doOnTextChanged
import android.content.Intent

import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding
    private var mFragment : NewsListFragment = NewsListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)



        with(activityMainBinding) {
            autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

            autocompleteTv.doOnTextChanged { text, start, before, count ->
                if(text.toString().isNotEmpty()){
                    autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross_out, 0)
                    mFragment.updateNewsList(text.toString())
                }else{
                    hideKeyboard(view = activityMainBinding.root)
                }
            }

            autocompleteTv.setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if(event.rawX >= autocompleteTv.right - autocompleteTv.totalPaddingRight) {
                        autocompleteTv.setText("")
                        autocompleteTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        return@OnTouchListener true
                    }
                }
                false
            })

        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mFragment).commit()

    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}