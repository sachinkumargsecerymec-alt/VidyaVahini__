# Vidya Vahini

Vidya Vahini is a Kotlin Android Studio project for real-time crowdsourced rural student transport tracking. The app follows MVVM, uses ViewBinding, Material UI, Navigation Component, RecyclerView, Firebase services, Google Maps, Retrofit, local offline cache, and an AI ETA / assistant workflow.

## Open in Android Studio

1. Open this `VidyaVahini` folder in Android Studio.
2. Let Gradle sync.
3. Replace `app/google-services.json` with your Firebase project file.
4. Add a real Google Maps key in `app/build.gradle.kts` under `manifestPlaceholders["MAPS_API_KEY"]`.
5. Set an AI endpoint and API key in `AiRepository` if you want live Gemini/OpenAI responses. The built-in route-aware fallback keeps the emulator app functional without paid keys.

## Features

- Student login/signup with Firebase Auth and local demo fallback.
- Route list and route search.
- Google Maps live tracking screen with bus marker updates.
- Crowd ping storage through Firestore and offline local cache.
- AI ETA prediction from GPS and crowd pings.
- Firebase Cloud Messaging service for arrival, delay, and safe reach notifications.
- Emergency, breakdown, and SOS reports.
- Offline cache and automatic retry-friendly repository design.
- In-app assistant for route and timing help.

## Build

Use Android Studio, or run:

```powershell
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
C:\Users\DELL\.gradle\wrapper\dists\gradle-9.2.1-bin\2t0n5ozlw9xmuyvbp7dnzaxug\gradle-9.2.1\bin\gradle.bat assembleDebug
```
