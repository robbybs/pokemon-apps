package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rbs.pokemonapps.R
import com.rbs.pokemonapps.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
    class MainActivity : AppCompatActivity() {
        private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
        private lateinit var navController: NavController

        companion object {
            @JvmStatic
            fun launch(context: Context) {
                Intent(context, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    context.startActivity(this)
                }
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            subscribeUI()
        }

        private fun subscribeUI() {
            navController = supportFragmentManager
                .findFragmentById(R.id.navHostFragment)
                ?.findNavController()
                ?: throw IllegalStateException("NavHostFragment not found")

            binding.bottomNav.setupWithNavController(navController)
        }
    }