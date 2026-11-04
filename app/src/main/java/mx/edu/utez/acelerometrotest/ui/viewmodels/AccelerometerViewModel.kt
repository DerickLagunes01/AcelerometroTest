package mx.edu.utez.acelerometrotest.ui.viewmodels

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.utez.acelerometrotest.R
import mx.edu.utez.acelerometrotest.data.AccelerometerData

class AccelerometerViewModel: ViewModel(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private var mediaPlayer: MediaPlayer? = null

    private val _sensorData = MutableStateFlow(AccelerometerData())
    val sensorData: StateFlow<AccelerometerData> = _sensorData.asStateFlow()

    fun startListening(context: Context){
        if(sensorManager == null){
            sensorManager =
                context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        accelerometer?.let {
            sensorManager?.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening(){
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            _sensorData.value = AccelerometerData(
                x = event.values[0],
                y = event.values[1],
                z = event.values[2]
            )
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //Nada
    }

    fun playSound(context: Context) {
        // Detener y liberar si ya se estaba reproduciendo algo
        stopSound()

        // R.raw.tu_sonido es el archivo de audio en res/raw/
        mediaPlayer = MediaPlayer.create(context, R.raw.latigo).apply {
            setOnCompletionListener {
                releasePlayer()
            }
            start()
        }
    }

    fun stopSound() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
        releasePlayer()
    }

}