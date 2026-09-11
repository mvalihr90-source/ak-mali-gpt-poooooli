package com.ak.financeai

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "AK Finance AI v1.0.0"
            textSize = 24f
            setPadding(40, 80, 40, 40)
        }

        setContentView(textView)
    }
}
