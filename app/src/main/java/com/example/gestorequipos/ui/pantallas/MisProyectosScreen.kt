package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.datos.DatosEjemplo
import com.example.gestorequipos.modelo.Proyecto
import com.example.gestorequipos.modelo.TipoPlan
import com.example.gestorequipos.modelo.Usuario
import com.example.gestorequipos.ui.BarraSuperior
import com.example.gestorequipos.ui.CampoDeTexto
import com.example.gestorequipos.ui.DialogoSimple
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun MisProyectosScreen(
    usuario: Usuario,
    proyectos: List<Proyecto>,
    onAbrirProyecto: (Proyecto) -> Unit,
    onHaztePremium: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    var mostrarDialogoCrear by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BarraSuperior(
                titulo = "Mis proyectos",
                acciones = {
                    TextButton(onClick = onCerrarSesion) { Text("Cerrar sesión") }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { mostrarDialogoCrear = true },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Crear proyecto") }
            )
        }
    ) { espacioInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno),
            contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Hola, ${usuario.nombre}", style = MaterialTheme.typography.headlineSmall)
            }
            items(proyectos) { proyecto ->
                TarjetaProyecto(proyecto, onClick = { onAbrirProyecto(proyecto) })
            }
            if (usuario.plan == TipoPlan.GRATIS) {
                item { RecuadroAnuncio() }
                item {
                    Button(onClick = onHaztePremium, modifier = Modifier.fillMaxWidth()) {
                        Text("Hazte Premium")
                    }
                }
            }
        }
    }

    if (mostrarDialogoCrear) {
        DialogoSimple(
            titulo = "Crear proyecto",
            textoConfirmar = "Crear",
            onCerrar = { mostrarDialogoCrear = false }
        ) {
            CampoDeTexto("Nombre del proyecto")
        }
    }
}

@Composable
private fun TarjetaProyecto(proyecto: Proyecto, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(proyecto.nombre, style = MaterialTheme.typography.titleMedium)
            Text("${proyecto.miembros.size} miembros", style = MaterialTheme.typography.bodySmall)
            Text("Meta: ${proyecto.ciclos.last().meta}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun RecuadroAnuncio() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text("Anuncio", color = Color.DarkGray)
    }
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaMisProyectosScreen() {
    GestorEquiposTheme {
        MisProyectosScreen(
            usuario = DatosEjemplo.usuarioActual,
            proyectos = DatosEjemplo.proyectos,
            onAbrirProyecto = {},
            onHaztePremium = {},
            onCerrarSesion = {}
        )
    }
}
