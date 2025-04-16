package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.rbs.pokemonapps.R
import com.rbs.pokemonapps.databinding.ActivityRegisterBinding
import com.rbs.pokemonapps.presentation.viewmodel.PokeViewModel
import com.rbs.pokemonapps.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RegisterViewModel>()
    private var name = ""
    private var username = ""
    private var password = ""

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
        getFullName()
        getUsername()
        getPassword()
        register()
    }

    private fun getFullName() {
        with(binding.etFullname) {
            doAfterTextChanged {
                name = it.toString()
                validate()
            }
        }
    }

    private fun getUsername() {
        with(binding.etUsername) {
            doAfterTextChanged {
                username = it.toString()
                validate()
            }
        }
    }

    private fun getPassword() {
        with(binding.etPassword) {
            doAfterTextChanged {
                password = it.toString()
                validate()
            }
        }
    }

    private fun validate() {
        binding.btnRegister.isEnabled = viewModel.validate(name, username, password)
    }

    private fun register() {
        binding.btnRegister.setOnClickListener {
            viewModel.register(name, username, password)
        }
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.viewState.collectLatest {
                if (it) finish() else showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Error register", Toast.LENGTH_SHORT).show()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    view.clearFocus()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}