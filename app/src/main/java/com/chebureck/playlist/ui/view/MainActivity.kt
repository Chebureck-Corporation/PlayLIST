package com.chebureck.playlist.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.chebureck.playlist.R
import com.chebureck.playlist.ui.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this)
        presenter.onCreate()
    }

    fun replaceRootFragmentByFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_fragment, fragment)
            .commit()
    }

    fun replaceRootFragmentByFragmentBackStack(fragment: Fragment, backStackName: String?) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_fragment, fragment)
            .addToBackStack(backStackName)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    fun <L> findListener(): L {
        return presenter as L
    }
}
