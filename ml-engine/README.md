# Local ML

`LocalAnalyzer` is the MVP inference boundary.

Production path:
1. Collect labeled SMS locally.
2. Include sender ID as an input feature.
3. Train a compact multilingual/Persian model.
4. Export TFLite/LiteRT.
5. Place the model under `android/app/src/main/assets/models/`.
6. Replace `LocalAnalyzer` internals with on-device inference.

No fake binary model is included.
