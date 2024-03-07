package com.example.greengpt.util

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun TextView.animateCharacterByCharacter(text: String, delay: Long = 33L) {
    // Make sure the text is not empty
    if (text.isEmpty()) return

    // Initialize a value animator to iterate over characters
    val charAnimation = ValueAnimator.ofInt(0, text.length)

    charAnimation.apply {
        this.duration = delay * text.length.toLong()
        this.repeatCount = 0
        addUpdateListener {
            val charCount = it.animatedValue as Int
            val animatedText = text.substring(0, charCount)
            this@animateCharacterByCharacter.text = animatedText
        }
    }

    charAnimation.start()
}