package com.damar.submitinter2.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.damar.submitinter2.R
import com.damar.submitinter2.customview.AnimationUtil
import com.damar.submitinter2.customview.FormValidator
import com.damar.submitinter2.databinding.ActivityRegisterBinding
import com.damar.submitinter2.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val regisViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

        regisViewModel.infoError.observe(this) { error ->
            showLoading(false)
            buttonOn(true)

            when (error) {
                true -> {
                    val errorMessage = regisViewModel.errorMessage.value
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
                false -> {
                    val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    Snackbar.make(binding.root, "Registrasi berhasil", Snackbar.LENGTH_SHORT).show()
                    startActivity(loginIntent)
                    finish()
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            val signUpIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(signUpIntent)
        }

        binding.btnRegister.setOnClickListener {
            if (!FormValidator.isValidRegister(binding)) {
                return@setOnClickListener
            }

            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            when {
                name.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, R.string.error_field_empty, Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(this, R.string.error_minimum_password, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    regisViewModel.createUser(name, email, password)
                    showLoading(true)
                    buttonOn(false)
                }
            }
        }

        AnimationUtil.playRegisAnimation(binding)
    }

    private fun buttonOn(state: Boolean) {
        binding.btnRegister.isEnabled = state
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarRegister.visibility = if (state) View.VISIBLE else View.GONE
    }
}

