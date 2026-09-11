# AK Finance AI v1.0.0 — GitHub Build Ready

این نسخه برای ساخت APK با GitHub Actions آماده شده است و برای Build محلی به Android Studio وابسته نیست.

## معماری MVP
SMS → SmsReceiver → LocalAnalyzer → SQLite → Dashboard

ویژگی‌ها:
- دریافت SMS جدید
- خواندن Sender قبل از متن
- تشخیص بانک از Sender ID و نام سرشماره
- نرمال‌سازی اعداد فارسی/عربی
- استخراج اولیه مبلغ
- ذخیره محلی SMS و تراکنش
- داشبورد ساده
- ساختار آماده برای اتصال مدل ML محلی
- GitHub Actions برای تولید APK

## Build آنلاین
1. کل محتویات این Repository را روی GitHub قرار دهید.
2. به Actions بروید.
3. Workflow با نام `Build Android APK` اجرا می‌شود.
4. APK از بخش Artifacts قابل دریافت است.

این Workflow عمداً از Gradle Wrapper استفاده نمی‌کند؛ GitHub Actions خودش Gradle 8.6 را نصب می‌کند. بنابراین `gradle-wrapper.jar` روی سیستم شما مانع Build نیست.

## Build محلی اختیاری
JDK 17 و Gradle 8.6:
`gradle assembleDebug`

## نکته ML
در این MVP هیچ مدل ساختگی به‌عنوان مدل ML قرار داده نشده است. `LocalAnalyzer` محل اتصال مدل واقعی TFLite/LiteRT است. داده‌های اصلاح‌شده کاربر باید بعداً برای ساخت dataset و مدل استفاده شوند.
