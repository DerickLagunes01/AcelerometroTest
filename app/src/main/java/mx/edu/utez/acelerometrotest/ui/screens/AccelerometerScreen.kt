package mx.edu.utez.acelerometrotest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.edu.utez.acelerometrotest.ui.viewmodels.AccelerometerViewModel
import mx.edu.utez.acelerometrotest.ui.viewmodels.SoundViewModel

@Composable
fun AccelerometerScreen(
    viewModel: AccelerometerViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sensorData by viewModel.sensorData.collectAsStateWithLifecycle()

    // Manejo automático de ciclo de vida (iniciar/detener la escucha al entrar/salir de pantalla)
    DisposableEffect(Unit) {
        viewModel.startListening(context)
        onDispose {
            viewModel.stopListening()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Datos del Acelerómetro",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        SensorValueCard(label = "Eje X", value = sensorData.x)
        Spacer(modifier = Modifier.height(12.dp))
        SensorValueCard(label = "Eje Y", value = sensorData.y)
        Spacer(modifier = Modifier.height(12.dp))
        SensorValueCard(label = "Eje Z", value = sensorData.z)

        if(sensorData.y > 5.0f){
            run { viewModel.playSound(context) }
        }
    }
}

@Composable
private fun SensorValueCard(label: String, value: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = String.format("%.2f m/s²", value),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

