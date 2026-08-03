package com.techieos.flash_light.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation routes for the Flashlight app.
 */
@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Dashboard : Route
}
