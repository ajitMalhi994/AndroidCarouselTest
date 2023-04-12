package com.example.androidexam.utils

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.viewpager2.widget.ViewPager2
import java.net.URL
import kotlin.concurrent.thread

fun ViewPager2.afterPageScrolled(afterPageChanged: (Int) -> Unit){
    this.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            afterPageChanged.invoke(position)
        }
    })
}

fun randomString() : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..6)
        .map { allowedChars.random() }
        .joinToString("")
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        var timer: CountDownTimer? = null
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(100, 100) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    afterTextChanged.invoke(editable.toString())
                }
            }.start()
        }
    })
}


fun ImageView.loadImage(imgUrl: String?) {

    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build().toString()


        val uiHandler = Handler(Looper.getMainLooper())
        thread(start = true) {
            val bitmap = downloadBitmap(imgUri)
            uiHandler.post {
                this.setImageBitmap(bitmap)
            }
        }
    }
}

fun downloadBitmap(imageUrl: String): Bitmap? {
    return try {
        val conn = URL(imageUrl).openConnection()
        conn.connect()
        val inputStream = conn.getInputStream()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        bitmap
    } catch (e: Exception) {
        Log.e(ContentValues.TAG, "Exception $e")
        null
    }
}