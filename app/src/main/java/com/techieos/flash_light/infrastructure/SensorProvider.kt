package com.techieos.flash_light.infrastructure

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

open class SensorProvider(context: Context?) {
    private val sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
    private val lightSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)

    open fun getLightLevel(): Flow<Float> = callbackFlow {
        if (lightSensor == null) {
            Log.w("SensorProvider", "Light sensor not available")
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if (it.sensor.type == Sensor.TYPE_LIGHT) {
                        trySend(it.values[0])
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        Log.d("SensorProvider", "Registering light sensor listener")
        sensorManager?.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_UI)

        awaitClose {
            Log.d("SensorProvider", "Unregistering light sensor listener")
            sensorManager?.unregisterListener(listener)
        }
    }
}
