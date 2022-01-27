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


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding
    private var mFragment : NewsListFragment = NewsListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        with(activityMainBinding) {
            autocompleteTv.doOnTextChanged { text, start, before, count ->
                mFragment.updateNewsList(text.toString())
            }
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mFragment as NewsListFragment).commit()

    }
}