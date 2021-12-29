package com.sanket.androidtestingrepo

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
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
                binding.etPassword.error = getString(R.string.password_error)
                hideProgress()
            }
        })
    }

    private fun initUsernameLD() {
        viewModel.getUsernameLD().observe(this, {
            if (it is Resource.Error) {
                binding.etUsername.error = getString(R.string.username_error)
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
                }
                is Resource.Success -> {
                    hideProgress()
                    toast("Go to home page")
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
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etUsername.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
        binding.llProgressBar.llProgress.setOnClickListener { /* no-op */ }
    }
}