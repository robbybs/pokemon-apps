package com.rbs.pokemonapps.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rbs.pokemonapps.R
import com.rbs.pokemonapps.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeUI()
        subscribeObserver()
    }

    private fun subscribeUI() {
        login()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener { MainActivity.launch(this) }
    }

    private fun subscribeObserver() {

    }
}