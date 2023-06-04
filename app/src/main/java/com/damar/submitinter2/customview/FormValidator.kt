package com.damar.submitinter2.customview

import com.damar.submitinter2.databinding.ActivityLoginBinding
import com.damar.submitinter2.databinding.ActivityRegisterBinding

class FormValidator {
    companion object {
        fun isValidLogin(binding: ActivityLoginBinding): Boolean {
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

        fun isValidRegister(binding: ActivityRegisterBinding): Boolean {
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
}
