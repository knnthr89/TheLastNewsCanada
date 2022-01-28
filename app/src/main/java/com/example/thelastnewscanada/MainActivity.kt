package com.example.thelastnewscanada

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
                }
                mFragment.updateNewsList(text.toString())
            }

            autocompleteTv.setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if(event.rawX >= autocompleteTv.right - autocompleteTv.totalPaddingRight) {
                        autocompleteTv.setText("")
                        return@OnTouchListener true
                    }
                }
                false
            })

        }



        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mFragment as NewsListFragment).commit()

    }
}