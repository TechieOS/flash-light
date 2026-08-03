# Task 3: Adaptive UI Development

Implement the main dashboard with Jetpack Compose, featuring a high-visibility manual toggle, a real-time lux display, and Navigation 3 integration. The UI will be adaptive across phone and tablet layouts.

## Proposed Changes

### [Navigation]
- Create `Routes.kt` defining the `MainDashboard` route using Kotlin Serialization.

### [UI Components]
- Create `DashboardScreen.kt` implementing the main UI.
- Use `androidx.compose.material3.adaptive` APIs to handle window size classes.
- Implement a "Big Button" for manual torch control.
- Implement a lux level display.
- Implement an "Auto-Dark Mode" toggle.

### [Activity Integration]
- Update `MainActivity.kt` to use `NavDisplay` from Navigation 3.
- Wire up `FlashlightViewModel` to the `DashboardScreen`.

## Verification Plan
### Automated Tests
- Build the project to ensure no compilation errors: `./gradlew :app:assembleDebug`

### Manual Verification
- Verify the UI adapts correctly to different screen sizes (Phone vs Tablet) in Previews.
- Verify the manual toggle and auto-mode switch work as expected.
