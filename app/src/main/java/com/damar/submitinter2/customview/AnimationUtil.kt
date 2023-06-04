package com.damar.submitinter2.customview

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.damar.submitinter2.databinding.ActivityLoginBinding
import com.damar.submitinter2.databinding.ActivityRegisterBinding

class AnimationUtil {
    companion object {
        fun playLoginAnimation(binding: ActivityLoginBinding) {
            val emailAnimator = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
            val passwordAnimator = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(450)
            val loginButtonAnimator = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(400)
            val textView1Animator = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(350)
            val textView2Animator = ObjectAnimator.ofFloat(binding.txtSignUp, View.ALPHA, 1f).setDuration(300)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(textView1Animator, textView2Animator)
            }
            AnimatorSet().apply {
                playSequentially(emailAnimator, passwordAnimator, loginButtonAnimator, togetherAnimator)
                start()
            }
        }

        fun playRegisAnimation(binding: ActivityRegisterBinding) {
            val nameAnimator = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(550)
            val emailAnimator = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
            val passwordAnimator = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(450)
            val registerButtonAnimator = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(400)
            val textView1Animator = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(350)
            val textView2Animator = ObjectAnimator.ofFloat(binding.txtLogin, View.ALPHA, 1f).setDuration(300)

            val togetherAnimator = AnimatorSet().apply {
                playTogether(textView1Animator, textView2Animator)
            }
            AnimatorSet().apply {
                playSequentially(nameAnimator, emailAnimator, passwordAnimator, registerButtonAnimator, togetherAnimator)
                start()
            }
        }
    }
}
