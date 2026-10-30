# Kinnect — i190479

Kotlin and XML Views assignment by Tabarak Sikander. Package: `com.tabaraksikander.i190479`.

Repository: https://github.com/tabarak-0479/i190479-Kinnect

## Implementation

The 23 PDF screens have separate Activities and XML layouts. Open this folder in Android Studio. Use the existing Gradle wrapper and installed Android SDK 37. Run the `app` configuration.

Navigation uses explicit `Intent` calls, standard click listeners, and `finish()` for Back. Login/signup start a new Home task; logout starts a new Login task using `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK`. Main tabs open ordinary Activities, so Back returns to the previous screen.

The illustrated image assets are local Android vector drawables. They are original landscape approximations of the reference's photo placeholders; no screenshot is used as a whole-screen background. Initials remain text avatars. Layouts use LinearLayout, FrameLayout, ScrollView, HorizontalScrollView, ImageView, ImageButton, TextView, EditText, RadioGroup, RadioButton, Spinner and CheckBox.

## Concepts to explain in the live demo

These supporting techniques go beyond the minimum Activities/XML/Intent baseline and should be confirmed against the class syllabus:

- `ViewCompat` / `WindowInsetsCompat` in `Ui.kt`: adds padding for system bars and the keyboard because modern Android draws apps edge to edge. It does not change screen navigation.
- `SharedPreferences`: local demo profile edits, selected reactions, draft photos, published text and story state. It is a small key/value store; it is not a database or server.
- `AlertDialog`: simple local menus and explanatory dialogs for secondary controls without separate PDF screens.
- Adding a `TextView` to a `LinearLayout`: displays newly entered comments and chat messages locally. All original screen content remains XML.
- Kotlin mutable sets: tracks selected illustration cells in the photo picker.
- Espresso and ActivityScenario: required workflow tests and extra screenshot checks. These are test-only dependencies.

No Compose, fragments, Navigation Component, ViewModel, dependency injection, networking or backend was added.

## Demo behavior and limits

This assignment is a local UI and navigation implementation. Login uses the prefilled demonstration account. Camera shows an illustrated preview and its shutter opens Story editor; it does not record camera input. Voice call reproduces the reference UI with local mute/speaker/end controls and does not place a call. Chat/comments are local demonstration entries. Marketplace cards open local details; they do not contact sellers. Secondary controls whose destinations are not supplied in the PDF show local dialogs.

Photo picker opens the bundled illustrated collection, supports multiple selections and returns to the preceding screen. Story editor shares to Your story. Profile edits save locally. Some screen text, exact icon outlines, badge placement and illustration geometry differ from the reference; do not describe the result as pixel-perfect without a final side-by-side review.

## Verification

Build: `gradlew.bat assembleDebug`

Static checks: `gradlew.bat lintDebug`

Workflow tests: `gradlew.bat connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.tabaraksikander.i190479.NavigationTest`

`NavigationTest` covers Home → Comments → Back and Login → Menu → Logout → Back. `ScreenRenderingTest` additionally opens all 23 screens and saves app-only screenshots, including four landscape checks. Build/lint success does not by itself prove visual fidelity. Waits inside the test code let Activity transitions and rotation settle before assertions/screenshots; they are not app delays.

## Submission

The requested ZIP contains `Source Code` and `Layout and Kotlin Files`. Source excludes generated build folders, machine paths and Git metadata. The second folder contains XML/Kotlin files only, preserving their relative paths. Keep the GitHub repository available for the live demonstration.

The assignment also requests the ChatGPT conversation link and prompts. The user has stated that direct AI implementation is now authorized. Preserve the instructor's updated permission and provide a shareable link to the conversation used; the original brief's AI policy otherwise differs from this authorization. See `AI_ASSISTANCE.md`.
