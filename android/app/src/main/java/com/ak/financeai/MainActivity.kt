package com.ak.financeai

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ak.financeai.data.TransactionStore

class MainActivity : ComponentActivity() {
    private val requestCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        val summary = findViewById<TextView>(R.id.summary)
        val button = findViewById<Button>(R.id.permissionButton)

        fun refresh() {
            val read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
            val receive = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
            status.text = if (read && receive) "وضعیت: دسترسی پیامک فعال است" else "وضعیت: نیازمند دسترسی پیامک"
            summary.text = "تراکنش‌های ثبت‌شده: ${TransactionStore(this).count()}"
        }

        button.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS),
                requestCode
            )
        }
        refresh()
    }
}
