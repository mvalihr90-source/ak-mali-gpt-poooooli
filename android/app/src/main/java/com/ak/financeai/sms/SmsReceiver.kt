package com.ak.financeai.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.ak.financeai.data.TransactionRecord
import com.ak.financeai.data.TransactionStore
import com.ak.financeai.ml.LocalAnalyzer

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (messages.isEmpty()) return

        val sender = messages.first().originatingAddress.orEmpty()
        val body = messages.joinToString("") { it.messageBody.orEmpty() }
        if (body.isBlank()) return

        // Sender ID is intentionally available to the local model before SMS text.
        val result = LocalAnalyzer().analyze(sender, body)

        TransactionStore(context.applicationContext).insert(
            TransactionRecord(
                sender = sender,
                bank = result.bank,
                amount = result.amount,
                body = body,
                createdAt = System.currentTimeMillis()
            )
        )
    }
}
