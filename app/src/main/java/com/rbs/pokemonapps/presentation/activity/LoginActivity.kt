package com.rbs.pokemonapps.presentation.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
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
import com.rbs.pokemonapps.data.local.datastore.DataStoreManager
import com.rbs.pokemonapps.databinding.ActivityLoginBinding
import com.rbs.pokemonapps.presentation.viewmodel.LoginViewModel
import com.rbs.pokemonapps.presentation.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>()
    private var username = ""
    private var password = ""

    @Inject
    lateinit var storeManager: DataStoreManager

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLogin()
        subscribeUI()
        subscribeObserver()
    }

    private fun subscribeUI() {
        getUsername()
        getPassword()
        login()
        register()
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
        binding.btnLogin.isEnabled = viewModel.validate(username, password)
    }

    private fun login() {
        binding.btnLogin.setOnClickListener { viewModel.login(username, password) }
    }

    private fun register() {
        binding.tvRegister.setOnClickListener { RegisterActivity.launch(this) }
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.viewState.collectLatest {
                if (it) goToHome() else showError()
            }
        }
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            storeManager.isLoggedIn.collectLatest { isLoggedIn ->
                if (isLoggedIn) goToHome() else setContentView(binding.root)
            }
        }
    }

    private fun goToHome() {
        MainActivity.launch(this)
        finish()
    }

    private fun showError() {
        Toast.makeText(this, "Error login", Toast.LENGTH_SHORT).show()
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