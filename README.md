# TechieOS Flashlight

A privacy-conscious Android flashlight application built with Kotlin and Jetpack Compose.

TechieOS Flashlight provides a simple manual torch control together with ambient-light monitoring and an optional automatic dark-mode feature.

## Features

* Manual flashlight ON/OFF control
* Automatic flashlight activation when ambient light becomes sufficiently low
* Real-time ambient light level displayed in lux
* Adaptive interface for different Android screen sizes
* Modern Jetpack Compose UI
* CameraX-based flashlight control
* Ambient light sensor integration
* No unnecessary account or sign-in requirement

## How It Works

### Manual Mode

Use the main control to turn the device flashlight ON or OFF.

### Auto-Dark Mode

When enabled, the application monitors the device's ambient light sensor.

When the measured light level falls below the configured threshold, the application can automatically activate the flashlight.

### Lux Display

The application displays the current ambient light level reported by the device's light sensor.

The availability and accuracy of ambient-light measurements depend on the hardware provided by the Android device.

## Requirements

* Android 10 or later
* A device with a camera flash / torch for flashlight functionality
* Android device with an ambient light sensor for automatic dark-mode functionality

Some devices may not support all features.

## Technology

* Kotlin
* Jetpack Compose
* Material 3
* CameraX
* Android Sensor Framework
* Kotlin Coroutines
* AndroidX
* Navigation 3

## Project Structure

```text
app/
├── src/
│   ├── androidTest/
│   ├── main/
│   └── test/
├── build.gradle.kts
└── ...

gradle/
build.gradle.kts
gradle.properties
settings.gradle.kts
```

## Privacy

TechieOS Flashlight is designed with a privacy-conscious approach.

The application does not require an account to operate.

The camera permission is used for flashlight/torch functionality. The application also uses the device's ambient light sensor when automatic dark-mode functionality is enabled.

Review the Android permissions declared by the application before installation and use.

## Open Source

This project is developed and maintained by TechieOS.

The source code is available for developers to study, modify and improve according to the project's license.

## Status

**Development / Open Source**

The project is actively maintained as part of the TechieOS software portfolio.

## Contributing

Bug reports, improvements and pull requests are welcome.

Please describe the problem clearly and provide reproducible information when reporting an issue.

## License

See the `LICENSE` file in this repository for the applicable licensing terms.

## TechieOS

TechieOS is a Bengaluru-based technology company building practical, accessible and privacy-conscious software, IoT systems, automation solutions and developer tools.

**Website:** https://techieos.com

**GitHub:** https://github.com/TechieOS
