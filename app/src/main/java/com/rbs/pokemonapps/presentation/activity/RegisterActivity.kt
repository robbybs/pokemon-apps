package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rbs.pokemonapps.R
import com.rbs.pokemonapps.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            Intent(context, RegisterActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeUI()
        subscribeObserver()
    }

    private fun subscribeUI() {
        register()
    }

    private fun register() {
        binding.btnRegister.setOnClickListener { finish() }
    }

    private fun subscribeObserver() {

    }
}