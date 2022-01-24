package com.sanket.androidtestingrepo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sanket.androidtestingrepo.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        initLoginLD()
        initUsernameLD()
        initPasswordLD()
    }

    private fun initPasswordLD() {
        viewModel.getPasswordLD().observe(this, {
            if (it is Resource.Error) {
                binding.tilPassword.error = getString(R.string.password_short_error)
                hideProgress()
            }
        })
    }

    private fun initUsernameLD() {
        viewModel.getUsernameLD().observe(this, {
            if (it is Resource.Error) {
                binding.tilUsername.error = getString(R.string.username_short_error)
                hideProgress()
            }
        })
    }

    private fun initLoginLD() {
        viewModel.getLoginLD().observe(this, {
            when (it) {
                is Resource.Loading -> showProgress()
                is Resource.Error -> {
                    hideProgress()
                    it.uiText?.let { uiText ->
                        toast(uiText.getText(this))
                    }
//                    EspressoIdlingResource.decrement()
                }
                is Resource.Success -> {
                    hideProgress()
                    openActivity<MainActivity>()
                }
            }
        })
    }

    private fun hideProgress() {
        binding.llProgressBar.llProgress.hide()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgress() {
        binding.llProgressBar.llProgress.show()
    }

    private fun initViews() {
        binding.apply {
            btnLogin.setOnClickListener {
//            EspressoIdlingResource.increment()
                viewModel.login(
                    etUsername.text.toString().trim(),
                    etPassword.text.toString().trim()
                )
            }
            llProgressBar.llProgress.setOnClickListener { /* no-op */ }
            etUsername.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) tilUsername.helperText =
                    viewModel.getUsernameValidationErrorMessage(etUsername.text.toString().trim())
                        ?.getText(this@LoginActivity)
            }
            etPassword.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) tilPassword.helperText =
                    viewModel.getPasswordValidationErrorMessage(etPassword.text.toString().trim())
                        ?.getText(this@LoginActivity)
            }
        }
    }
}