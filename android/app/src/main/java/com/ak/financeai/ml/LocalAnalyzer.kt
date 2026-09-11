package com.ak.financeai.ml

import java.util.regex.Pattern

data class AnalysisResult(
    val bank: String?,
    val amount: Long?,
    val confidence: Float
)

class LocalAnalyzer {

    private val senderBanks = mapOf(
        "MELLAT" to "بانک ملت",
        "ملت" to "بانک ملت",
        "MELLI" to "بانک ملی",
        "ملی" to "بانک ملی",
        "SAMAN" to "بانک سامان",
        "سامان" to "بانک سامان",
        "TEJARAT" to "بانک تجارت",
        "تجارت" to "بانک تجارت"
    )

    fun analyze(sender: String, body: String): AnalysisResult {
        val bank = senderBanks.entries.firstOrNull { sender.contains(it.key, true) }?.value
        val normalized = normalizeDigits(body)
        val amount = extractAmount(normalized)
        val confidence = when {
            bank != null && amount != null -> 0.90f
            bank != null -> 0.70f
            amount != null -> 0.55f
            else -> 0.10f
        }
        return AnalysisResult(bank, amount, confidence)
    }

    private fun normalizeDigits(text: String): String {
        val fa = "۰۱۲۳۴۵۶۷۸۹"
        val ar = "٠١٢٣٤٥٦٧٨٩"
        return buildString(text.length) {
            for (c in text) {
                val f = fa.indexOf(c)
                val a = ar.indexOf(c)
                append(
                    when {
                        f >= 0 -> ('0'.code + f).toChar()
                        a >= 0 -> ('0'.code + a).toChar()
                        c == '٬' || c == ',' -> ','
                        else -> c
                    }
                )
            }
        }
    }

    private fun extractAmount(text: String): Long? {
        val p = Pattern.compile("(\\d{1,3}(?:[, ]\\d{3})+|\\d{4,})")
        val m = p.matcher(text)
        while (m.find()) {
            val value = m.group(1).replace(",", "").replace(" ", "").toLongOrNull()
            if (value != null && value > 0) return value
        }
        return null
    }
}
