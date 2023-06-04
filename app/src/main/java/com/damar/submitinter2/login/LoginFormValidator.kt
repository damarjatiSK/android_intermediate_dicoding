package com.damar.submitinter2.login

import com.damar.submitinter2.databinding.ActivityLoginBinding

class LoginFormValidator(private val binding: ActivityLoginBinding) {
    fun isValid(): Boolean {
        var isValid = true

        if (binding.edLoginEmail.text.toString().isEmpty()) {
            binding.edLoginEmail.error = "Email tidak boleh kosong"
            isValid = false
        }

        if (binding.edLoginPassword.text.toString().isEmpty()) {
            binding.edLoginPassword.error = "Password tidak boleh kosong"
            isValid = false
        }
        if (binding.edLoginPassword.text.toString().length < 8) {
            binding.edLoginPassword.error = "Karakter password harus 8 atau lebih"
            isValid = false
        }
        return isValid
    }
}