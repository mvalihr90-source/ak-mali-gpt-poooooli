package com.ak.financeai.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TransactionRecord(
    val sender: String,
    val bank: String?,
    val amount: Long?,
    val body: String,
    val createdAt: Long
)

class TransactionStore(context: Context) :
    SQLiteOpenHelper(context, "ak_finance.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sender TEXT NOT NULL," +
                "bank TEXT," +
                "amount INTEGER," +
                "body TEXT NOT NULL," +
                "created_at INTEGER NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit

    fun insert(record: TransactionRecord) {
        val values = android.content.ContentValues().apply {
            put("sender", record.sender)
            put("bank", record.bank)
            if (record.amount != null) put("amount", record.amount) else putNull("amount")
            put("body", record.body)
            put("created_at", record.createdAt)
        }
        writableDatabase.insert("transactions", null, values)
    }

    fun count(): Int {
        readableDatabase.rawQuery("SELECT COUNT(*) FROM transactions", null).use {
            return if (it.moveToFirst()) it.getInt(0) else 0
        }
    }
}
