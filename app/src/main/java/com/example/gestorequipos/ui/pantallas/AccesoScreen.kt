package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.ui.CampoDeTexto
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun AccesoScreen(onEntrar: () -> Unit) {
    var modoRegistro by remember { mutableStateOf(false) }

    Scaffold { espacioInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Gestor de Equipos", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            FormularioAcceso(modoRegistro)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onEntrar, modifier = Modifier.fillMaxWidth()) {
                Text(if (modoRegistro) "Registrarse" else "Iniciar sesión")
            }
            TextButton(onClick = { modoRegistro = !modoRegistro }) {
                Text(if (modoRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate")
            }
        }
    }
}

@Composable
private fun FormularioAcceso(modoRegistro: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (modoRegistro) {
            CampoDeTexto("Nombre")
        }
        CampoDeTexto("Correo")
        CampoContrasena()
    }
}

@Composable
private fun CampoContrasena() {
    var contrasena by remember { mutableStateOf("") }
    OutlinedTextField(
        value = contrasena,
        onValueChange = { contrasena = it },
        label = { Text("Contraseña") },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaAccesoScreen() {
    GestorEquiposTheme {
        AccesoScreen(onEntrar = {})
    }
}
