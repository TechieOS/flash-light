# Project Plan

Flashlight app with Manual ON/OFF and Auto-dark mode.

## Project Brief

# Project Brief: Flashlight MVP

This document outlines the features and technical requirements for a modern, adaptive Android flashlight application with intelligent automation.

## Features
*   **Manual ON/OFF Toggle**: A central, high-visibility control for immediate activation and deactivation of the flashlight.
*   **Auto-Dark Mode**: Intelligent monitoring of ambient light levels to automatically activate the flashlight when the environment becomes dark.
*   **Real-time Lux Display**: Visual feedback of current light intensity using the device's light sensor data.
*   **Adaptive UI**: A responsive interface that dynamically adjusts its layout for optimal usability on phones, foldables, and tablets.

## High-Level Technical Stack
*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose
*   **Navigation**: **Jetpack Navigation 3** (utilizing state-driven navigation patterns)
*   **Adaptive Layouts**: **Compose Material Adaptive** library (ensuring seamless transitions between different window size classes)
*   **Flashlight Control**: CameraX (Camera-control APIs for reliable torch management)
*   **Sensor Integration**: **SensorManager** (for real-time ambient light sensor monitoring)
*   **Concurrency**: Kotlin Coroutines (for non-blocking sensor data collection and state updates)

---
> [!NOTE]
> This MVP focuses on a "clean and functional" approach, prioritizing reliable hardware interaction and a modern adaptive UI without unnecessary local persistence.

## Implementation Steps

### Task_1_Setup_Infrastructure: Add dependencies for CameraX, SensorManager, Navigation 3, and Material Adaptive. Implement the FlashlightManager to control the torch and SensorProvider to monitor ambient light.
- **Status:** COMPLETED
- **Updates:** Implemented CameraX integration for flashlight control, SensorManager integration for light level monitoring, and added necessary dependencies for Navigation 3 and Material Adaptive. Project builds successfully.
- **Acceptance Criteria:**
  - Project builds with CameraX, SensorManager, Navigation 3, and Material Adaptive dependencies
  - FlashlightManager correctly toggles the device torch
  - SensorProvider emits real-time lux values from the light sensor

### Task_2_ViewModel_And_State_Logic: Create the FlashlightViewModel to manage manual toggle state, auto-dark mode activation, and sensor data integration using Kotlin Coroutines and Flows.
- **Status:** COMPLETED
- **Updates:** Implemented FlashlightViewModel with reactive state management. Integrated auto-dark mode logic (10 lux threshold) and manual toggle controls. Verified logic with unit tests.
- **Acceptance Criteria:**
  - ViewModel maintains state for manual toggle and auto-dark mode
  - Auto-dark mode logic correctly triggers torch based on lux threshold
  - Lux values are streamed to the UI reactively

### Task_3_Adaptive_UI_Development: Develop the main dashboard with Jetpack Compose, featuring a high-visibility manual toggle and a real-time lux display. Integrate Navigation 3 and ensure the UI is adaptive across phone and tablet layouts.
- **Status:** COMPLETED
- **Updates:** Developed adaptive UI using Jetpack Compose and Material 3 Adaptive. Integrated Navigation 3 for app routing. Bound manual toggle, auto-mode switch, and lux display to the ViewModel. App now supports both phone and tablet layouts.
- **Acceptance Criteria:**
  - UI is responsive to different window size classes using Material Adaptive
  - Manual toggle and lux display are functional
  - Navigation 3 is implemented for app structure

### Task_4_Run_And_Verify: Finalize the application and perform a full verification. Instruct the critic_agent to verify application stability (no crashes), confirm alignment with user requirements, and report critical UI issues.
- **Status:** COMPLETED
- **Updates:** Final verification completed. App is stable on both Phone and Tablet. Manual toggle and Auto-dark mode are fully functional. No crashes or critical UI issues found.
- **Acceptance Criteria:**
  - Build pass
  - App does not crash
  - All existing tests pass
  - Manual and Auto-dark mode features function as expected
  - UI aligns with the project brief and requirements
- **Duration:** N/A

