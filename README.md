# Kinnect — i190479

Kotlin/XML Views assignment by Tabarak Sikander. Package: `com.tabaraksikander.i190479`.
Repository: https://github.com/tabarak-0479/i190479-Kinnect

Open this folder in Android Studio and run `app`. Use the checked-in Gradle wrapper, Android SDK 37 and Studio's Java runtime. `local.properties` is excluded because SDK paths vary by computer.

## Implementation

Each of the 23 PDF screens has a separate Activity and `activity_*.xml` layout. Ordinary widgets include LinearLayout, FrameLayout, ScrollView, HorizontalScrollView, TextView, ImageView, ImageButton, EditText, RadioGroup, Spinner and CheckBox. Navigation uses explicit Intents, click listeners and `finish()`. Login/signup and logout use `NEW_TASK | CLEAR_TASK` to prevent returning to authenticated screens after logout.

The primary color is `#0B5F63`. Reusable shapes/vectors provide borders, avatar rings, rounded cards, reactions, call halos and icons. Bundled vectors bypass the broken Studio icon catalog. No screen is a flattened screenshot. Original landscape illustrations follow the reference's color families.

Local behavior includes all profile fields and avatar/cover editing; image-selection return and recreation; posts/attachments; editable/shared stories and replies; person-specific profiles/chats with persistent messages; search/category filters; requests; notification actions; menu settings; marketplace filters, listings and seller conversations. Secondary actions without a PDF destination use simple dialogs.

This is a local UI/navigation demonstration. Authentication, recovery, camera, calling and marketplace have no connected accounts, camera capture, audio transport, transactions or backend. The shutter uses an illustrated scene; the call screen does not place a call. Those external services are not required by the supplied rubric.

## Supporting concepts beyond the baseline

The class syllabus was not supplied. Compare these techniques with it; each is explained here as requested:

| Concept | Purpose and explanation |
|---|---|
| WindowInsetsCompat / WindowInsetsControllerCompat | Read system-bar/keyboard space, add padding and choose legible system-bar icons on current Android. Compatibility handling in Ui.kt. |
| SharedPreferences | Save small local key/value values: profile fields, image IDs, reactions, requests and messages. `edit().put...().apply()` writes them; `get...()` reads them. No database/server. |
| Intent extras / Activity results | Pass a person's name or selected image IDs. The picker returns RESULT_OK; onActivityResult reads it. The older API is intentionally used for a simple class-style flow despite deprecation warnings. |
| Bundle saved state | Restore picker/editor state and timer when an Activity is recreated. Preferences instead persist data beyond that screen. |
| TextWatcher | Receive edits and filter rows or update strength/send controls. |
| AlertDialog | Basic local choice/input popups where no separate reference screen exists. |
| Arrays, lists, LinkedHashSet | Map image IDs/people and track selected pictures without duplicates while retaining their order. |
| Adding TextView/ImageView rows to LinearLayout | Show newly submitted messages, comments, attachments or listings. Original screen structure stays in XML. |
| Chronometer | Display elapsed time in the local call UI. |
| ClipboardManager | Copy post text or a profile name for local sharing. |
| TouchDelegate / font sizing / resource configurations | Expand small badge hit areas, keep initials inside avatars, stack forms for large text, and use a landscape call layout. |
| Espresso / ActivityScenario / configuration contexts / Canvas | Test-only workflow, recreation and visual QA tools. Alternate-size XML rendering is distinct from physical-tablet testing. |

No Compose, fragments, Navigation Component, ViewModel, dependency injection, networking or server. Ui.kt is a small shared helper.

## Verification

Build/static checks: `gradlew.bat assembleDebug assembleDebugAndroidTest lintDebug`.

NavigationTest contains the two required Espresso tests: Home → Comments → Back and Login → Menu → Logout → Back, with an authenticated-task assertion. FeatureDataTest and NavigationCoverageTest add ten regression workflows. Test-only waits settle transitions; the app has no artificial interaction delays.

ScreenRenderingTest captures 22 stable phone screens and six landscape views. Splash is verified through timed navigation and XML configuration renders, avoiding a transient screenshot of another app. ConfigurationLayoutTest renders all 23 layouts at 600×960, 960×600, and 390×844 at font scales 1.3 and 2.0, using the same AppCompat widget types as Activities. It asserts pinned-control bounds. Data-changing test classes back up/restore demo preferences.

See Validation and Rubric-audit in the handoff for executed results. Automated checks do not certify pixel equality, TalkBack usability or an instructor's grade.

## Submission

The ZIP has exactly two folders: Source Code and Layout and Kotlin Files. Source excludes generated files, machine settings and Git metadata; the second contains only Kotlin/layout XML with relative paths preserved. GitHub preserves the real progressive history.

The user explicitly authorized direct AI implementation. AI_ASSISTANCE.md records permission and principal instructions. The student must supply a shareable conversation/full transcript, retain updated permission, confirm the accepted deadline and perform the live demonstration. DEMO_GUIDE.md provides rehearsal and edit/debug examples.
