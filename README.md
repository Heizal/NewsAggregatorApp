# NewsAggregatorApp

**NewsAggregatorApp** is an Android application that aggregates news articles from various sources, providing users with a centralized hub to stay updated on current events.

## Features

*   **News Article Display:** Fetches and displays news articles in a user-friendly format.
*   **Multiple News Sources:** Aggregates news from different sources, offering a wide range of perspectives.
*   **Search and Filtering:** Users can search for specific articles or filter news based on categories.
*  **Bookmarking:** Users can bookmark articles for later reading.
* **Recent Articles:** Displays a list of recently read articles.

## Tech Stack

*   **Kotlin:** The primary programming language for the Android application.

## Setup and Installation

Please find the setup instructions in the [INSTALLATION.md](docs/INSTALLATION.md) file

## Conceptual Guide

To understand the architectural decisions, design patterns, and high-level components of this app, refer to the [CONCEPTUAL_GUIDE.md](docs/CONCEPTUAL_GUIDE.md).

## Clean Code Practices

This project follows clean code principles. Check out [CLEAN_CODE.md](docs/CLEAN_CODE.md) to see how I applied them across the codebase.

## Project Structure

The project is organized into several modules, each serving a specific purpose. Here's a brief overview of the main modules:

📁 app  
┣ 📁 src  
┃ ┣ 📁 main  
┃ ┃ ┣ 📁 java  
┃ ┃ ┃ ┗ 📁 com.example.newsaggregatorapp  
┃ ┃ ┃ ┣ 📁 api  
┃ ┃ ┃ ┣ 📁 components  
┃ ┃ ┃ ┣ 📁 database  
┃ ┃ ┃ ┣ 📁 helpers  
┃ ┃ ┃ ┣ 📁 models  
┃ ┃ ┃ ┣ 📁 navigation  
┃ ┃ ┃ ┣ 📁 repository  
┃ ┃ ┃ ┣ 📁 screens  
┃ ┃ ┃ ┣ 📁 ui.theme  
┃ ┃ ┃ ┣ 📁 util  
┃ ┃ ┃ ┗ 📁 viewmodel  
┃ ┃ ┗ 📄 MainActivity.kt  
┃ ┣ 📁 res  
┃ ┃ ┣ 📄 AndroidManifest.xml  
┃ ┃ ┗ 📄 placeholder-playstore.png  
┣ 📁 androidTest  
┗ 📁 test [unitTest]

📁 docs  
┣ 📄 CLEAN_CODE.md  
┣ 📄 CONCEPTUAL_GUIDE.md  
┗ 📄 INSTALLATION.md

📄 README.md  
📄 build.gradle.kts  
📄 settings.gradle.kts  
📄 proguard-rules.pro

## Testing
The project includes unit tests to ensure the functionality of various components. The tests are located in the `test [unitTest]` directory and can be run using Android Studio or the command line.

## Contributing
Contributions are welcome! To contribute:

Fork the repository.
* Create a new branch `(git checkout -b feature-branch)`.
* Commit your changes `(git commit -m 'Add some feature')`.
* Push to the branch `(git push origin feature-branch)`.
* Open a pull request.

Feel free to reach out with any questions or feedback!
