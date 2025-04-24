# Installation Guide

Welcome to the News Aggregator App! Follow these steps to set up the project on your local machine and get it running smoothly.

## 📋 Prerequisites

Before you begin, ensure you have the following tools installed:

- **Android Studio (Arctic Fox or later)**
- **Java 17+**
- **Gradle (comes bundled with Android Studio)**
- **Git**

## 📦 Clone the Repository

```bash
git clone https://github.com/yourusername/news-aggregator-app.git
cd news-aggregator-app
```

## ⚙️ Open the Project
1. Open Android Studio.
2. Select "Open an existing Android Studio project".
3. Navigate to the cloned repository and select it.
4. Wait for Android Studio to sync the project. This may take a few minutes as it downloads dependencies.
5. Once the sync is complete, you should see the project structure in the left panel.

## Configure Environment Variables
1. Create a folder named `assets` in the main directory of the project. In it create a `config.properties` file. This file will store your API  keys. 
2. Add your API keys and other environment variables in the `.env` file. For example:
   ```env
   API_KEY=your_api_key_here
   BASE_URL=the_base_url_here
   ```
3. Make sure to add the `config.propertes` file to your `.gitignore` to prevent it from being pushed to the repository.

## Run the App
1. Connect an Android device or start an emulator.
2. In Android Studio, click on the "Run" button (green triangle) or press `Shift + F10`.
3. The app should build and launch on your device/emulator.

## Troubleshooting
- If you encounter any issues during the build process, try the following:
  - **Invalidate Caches/Restart**: Go to `File > Invalidate Caches / Restart`.
  - **Clean Project**: Go to `Build > Clean Project`.
  - **Rebuild Project**: Go to `Build > Rebuild Project`.
  - **Check Dependencies**: Ensure all dependencies are correctly defined in the `build.gradle` files.

