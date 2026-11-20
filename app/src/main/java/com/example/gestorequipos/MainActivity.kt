package com.example.gestorequipos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gestorequipos.ui.AppNavegacion
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorEquiposTheme {
                AppNavegacion()
            }
        }
    }
}
