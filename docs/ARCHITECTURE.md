# Architecture

SMS_RECEIVED
→ SmsReceiver
→ Sender ID + SMS text
→ LocalAnalyzer
→ TransactionStore (SQLite)
→ Dashboard

The sender is processed before message content because some banks expose a meaningful sender name/ID.

The MVP deliberately avoids a hard-coded bank parser as the primary architecture. The current analyzer is a safe fallback and an integration boundary for the real local ML model.
