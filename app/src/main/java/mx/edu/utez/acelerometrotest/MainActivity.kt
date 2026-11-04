package mx.edu.utez.acelerometrotest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.edu.utez.acelerometrotest.ui.screens.AccelerometerScreen
import mx.edu.utez.acelerometrotest.ui.theme.AcelerometroTestTheme
import mx.edu.utez.acelerometrotest.ui.viewmodels.AccelerometerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = AccelerometerViewModel()
        setContent {
            AcelerometroTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AccelerometerScreen(
                        viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
