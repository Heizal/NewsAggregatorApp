# Conceptual Guide

This guide is designed to explain how the app works behind the scenes. Its technical details are simplified so that anyone can understand them, even if you're not a developer.

---

## What the App Does

The app pulls the latest news from the internet, displays it in a beautiful and easy-to-read layout, and lets users:

- Browse news by category (like Tech, World, Health, etc.)
- Search for specific articles
- Bookmark articles for later
- See which articles they've already read

---

## How It Works

Think of the app like a smart **news delivery team**:

- A **Fetcher** grabs the latest headlines from trusted sources
- A **Sorter** puts them into neat piles like "General", "Business", or "Health"
- A **Saver** remembers what you bookmarked, searched for or read
- A **Presenter** shows you everything in a clean and format

All of these parts work together to make your news experience smooth and personal.

---

## The Big Picture: Conceptual Architecture

Below is a simplified diagram showing how the app is organized conceptually.

![Conceptual Architecture](/System_Architecture_Diagram.png)

---

## What Are These Pieces?

### **User Interface** (`components/`, `screens/`)
- Contains everything you see and interact with.
- Each screen (e.g. `HomeScreen.kt`, `SavedNewsScreen.kt`) lives in the `screens/` package.
- UI elements like cards, lists, and top bars live in the `components/` package.

### **ViewModel** (`viewmodel/`)
- Connects the user interface to the logic.
- Example: `HomeViewModel.kt` handles loading articles, refreshing data, and reacting to user inputs.
- Stores screen state (e.g. which category is selected).

### **Repository** (`repository/`)
- Acts as the central hub for fetching and saving data.
- Example: `NewsRepository.kt` talks to both the API and the local database.
- Abstracts away where the data comes from.

### **Navigation** (`navigation/`)
- Manages movement between screens.
- Uses `NavHostController` to go from "Home" to "Saved Articles", etc.
- Defined in `AppNavigation.kt`.

### **Data Layer**
- **API** (`api/`): Contains code that talks to the internet. Example: `NewsApiService.kt` fetches articles.
- **Database** (`database/`): Uses Room to store saved and recently read articles. See `AppDatabase.kt` and `BookmarkDao.kt`.
- **Utils** (`util/`, `helpers/`): Helpers like date formatting (`DateFormatter.kt`) and constants live here.

---

## Flow in Action

1. You open the app and land on the **Home Screen**.
2. The app uses `NewsApiService.kt` to fetch the latest headlines.
3. `HomeViewModel.kt` processes the response and tells the UI what to show.
4. If you save an article, `NewsRepository.kt` stores it using `BookmarkDao.kt`.
5. Navigation between screens is handled by `AppNavigation.kt`.

---

Need help with any part of this guide? Don’t hesitate to reach out — this doc is for **you**. 💬
