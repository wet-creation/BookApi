# 📚 Book Explorer App

A simple two-screen Android app to explore book categories and bestsellers from the [New York Times Books API](https://developer.nytimes.com/docs/books-product/1/overview). Built with Kotlin, Jetpack Compose, Room, Firebase Auth, and Clean Architecture.

---

## 🚀 Features

- 🔐 Google Sign-In via Firebase
- 🏁 Splash screen with session validation
- 📚 Category list screen (from NYT API)
- 📖 Book list per category with details
- 🛒 Buy link opens in in-app browser
- 📦 Offline caching using Room
- 🧭 Jetpack Compose navigation
- 🌐 Full localization support (no hardcoded strings)

---

## 🧱 Project Structure

```
bookapi/
├── core/                  
│   ├── database/         
│   ├── di/               
│   ├── navigation/        
│   ├── network/           
│   ├── ui/                
│   ├── utils/           
│   └── Config.kt          
│
├── di/
│   └── MainModule.kt      
│
├── features/             
│   ├── auth/             
│   │   ├── data/         
│   │   ├── di/            
│   │   └── presentation/ 
│   │
│   └── books/            
│       ├── data/          
│       ├── di/            
│       ├── domain/       
│       └── presentation/  
│
├── BookApiApp.kt         
├── MainActivity.kt       
└── MainViewModel.kt       
```

---

## 🛠️ Setup Instructions

> ⚠️ Before building, make sure you have the following keys and files.

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/book-explorer.git
   cd book-explorer
   ```

2. **Add your `google-services.json` file into the `app/` directory**  
   You can get it from [Firebase Console](https://console.firebase.google.com) — make sure Google Sign-In is enabled.

3. **Create or edit `local.properties` and add:**

   ```properties
   sdk.dir=your_sdk_directory
   BOOK_API_TOKEN=your_nyt_api_key
   WEB_CLIENT_ID=your_firebase_web_client_id
   ```

   - 🔑 `BOOK_API_TOKEN` — Get it from the [NYT Developer Portal](https://developer.nytimes.com/get-started):
     1. Sign up or log in
     2. Create an application
     3. Enable "Books API"
     4. Copy the API Key

   - 🔑 `WEB_CLIENT_ID` — Get it from your Firebase Console:
     1. Go to **Project Settings > General**
     2. Under **Your apps**, select the Android app
     3. Find the **Web client ID** under **OAuth 2.0 client IDs**

4. **Sync the project and run it on a device/emulator.**

---

## 🔑 Required Accounts

- [Firebase Console](https://console.firebase.google.com) — for Google Auth
- [NY Times Developer](https://developer.nytimes.com/get-started) — for API access

---

