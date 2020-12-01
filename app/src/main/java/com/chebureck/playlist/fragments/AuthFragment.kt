package com.chebureck.playlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.chebureck.playlist.R

class AuthFragment : Fragment() {
    interface AuthListener {
        fun onSignInPressed()
    }

    private var listener: AuthListener? = null
    fun setListener(listener: AuthListener?) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_authentification,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authButton = view.findViewById<Button>(R.id.btn_sign)
        authButton.setOnClickListener {
            listener?.onSignInPressed()
        }
    }
}
