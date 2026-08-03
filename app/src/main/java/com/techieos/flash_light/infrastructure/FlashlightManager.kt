package com.techieos.flash_light.infrastructure

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.Log

open class FlashlightManager(private val context: Context?) {
    private val cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as? CameraManager
    private var cameraId: String? = null

    init {
        try {
            cameraId = cameraManager?.cameraIdList?.firstOrNull { id ->
                val characteristics = cameraManager?.getCameraCharacteristics(id)
                characteristics?.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }
            Log.d("FlashlightManager", "Camera with flash found: $cameraId")
        } catch (e: Exception) {
            Log.e("FlashlightManager", "Error initializing FlashlightManager", e)
        }
    }

    open fun toggleTorch(enabled: Boolean) {
        cameraId?.let {
            try {
                cameraManager?.setTorchMode(it, enabled)
                Log.d("FlashlightManager", "Torch toggled: $enabled")
            } catch (e: Exception) {
                Log.e("FlashlightManager", "Error toggling torch", e)
            }
        } ?: Log.e("FlashlightManager", "No camera with flash available")
    }
}
