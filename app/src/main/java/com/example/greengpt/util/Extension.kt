package com.example.greengpt.util

import android.animation.ValueAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView

fun TextView.animateCharacterByCharacter(text: String, delay: Long = 25L) {

    if (text.isEmpty()) return

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

fun Context.copyToClipboard(text: CharSequence){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label",text)
    clipboard.setPrimaryClip(clip)
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun Intent.shareTextPlain(text : String){
    this.action = Intent.ACTION_SEND
    this.putExtra(Intent.EXTRA_TEXT,text)
    this.type = "text/plain"
}