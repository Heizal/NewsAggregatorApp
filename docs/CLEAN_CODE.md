# Clean Code & Analysis Strategy

This document outlines the **clean code principles**, **refactoring process**, and **code analysis tools** applied in this project.

## Code Analysis Tools

| Tool         | Purpose                              |
|--------------|---------------------------------------|
| **Lint**     | Detects code smells, unused declarations, and layout issues.
| **Unit Tests + Truth** | Ensure behavior is correct before and after refactors.

## Clean Code Principles Followed

| Principle | Description | Code Reference |
|----------|-------------|----------------|
| **Single Responsibility Principle (SRP)** | Each class/component does one job. | `HomeViewModel`, `SearchViewModel`, `RecentlyReadViewModel` all follow SRP. |
| **Intent-Revealing Names** | Variables and functions are named clearly. | `addToRecentlyRead()`, `searchNews()`, `recentSearches` in ViewModels. |
| **No Duplicate Code** | Common code like `NewsItem` is reused across screens. | `NewsItem.kt` is reused in `HomeScreen`, `SavedNewsScreen`, etc. |
| **Minimal Commenting, Maximum Clarity** | Code is self-explanatory, minimal comments. | `SearchViewModel.kt`, `MainScaffold.kt` — readable logic without excess comments. |
| **Use of Interfaces / DI-friendly Design** | Dependencies can be mocked/tested. | `SearchViewModel` accepts injected `NewsApiService` & `RecentSearchRepository`. |
| **Composition Over Inheritance** | Screens and components are composed rather than extended. | `MainScaffold`, `NewsItem`, and `LargeNewsItem` are composed in `HomeScreen`. |


---

## Refactoring Workflow

Refactoring was done **iteratively**, following the safe and test-driven approach from *Fowler's Refactoring* and *Clean Code*:

### Step 1: Identify Code Smells
- Used **Lint** to find probable bugs (e.g. `object` in `Navigation.kt`).
- Manual review to spot duplicated code (e.g. `NewsItem` logic repeated across screens)

### Step 2: Write Tests where applicable
- Verified behavior with `SearchViewModelTest`, `RecentlyReadViewModelTest`, `HomeViewModelTest`.

### Step 3: Extract Components
- Moved UI logic like `NewsItem`, `LargeNewsItem`, `CategoryTabs`, `SearchBar` into reusable composables.

### Step 4: Simplify
- Replaced unnecessary code with minimal clean logic.
- Ensured state flows clearly through `collectAsState()` and proper parameterization.

### Step 5: Verify with Tests
- Ensured no regression with existing ViewModel tests.
- Verified mocks like `RecentSearchRepository` or `NewsApiService` using `MockK`.

### Step 6: Document
- Clean code strategies & decisions documented here.


## Future Opportunities for Clean Code
- Add **interfaces** for all repositories to improve testability & modularity.
- Add **ktlint/detekt** to enforce stylistic rules on CI.
- Reduce coupling by moving away from `AndroidViewModel` where possible.

## 👥 Contributors
- **Patricia Heizal Nagginda** – Refactored major screens, implemented ViewModels, rewrote testable logic.