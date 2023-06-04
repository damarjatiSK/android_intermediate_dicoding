package com.damar.submitinter2.response

data class RegisterRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
)