package com.damar.submitinter2.register

import com.damar.submitinter2.databinding.ActivityRegisterBinding

class RegisterFormValidator(private val binding: ActivityRegisterBinding) {
    fun isValid(): Boolean {
        var isValid = true
        if (binding.edRegisterName.text.toString().isEmpty()) {
            binding.edRegisterName.error = "Nama tidak boleh kosong"
            isValid = false
        }
        if (binding.edRegisterEmail.text.toString().isEmpty()) {
            binding.edRegisterEmail.error = "Email tidak boleh kosong"
            isValid = false
        }
        if (binding.edRegisterPassword.text.toString().isEmpty()) {
            binding.edRegisterPassword.error = "Password tidak boleh kosong"
            isValid = false
        }
        if (binding.edRegisterPassword.text.toString().length < 8) {
            binding.edRegisterPassword.error = "Karakter password harus 8 atau lebih"
            isValid = false
        }
        return isValid
    }
}