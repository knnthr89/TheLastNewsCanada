package com.example.thelastnewscanada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thelastnewscanada.databinding.ActivityMainBinding;
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding
    private var mFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        mFragment = NewsListFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mFragment as NewsListFragment).commit()

    }
}