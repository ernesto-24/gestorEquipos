package com.example.gestorequipos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.modelo.EstadoTarea
import com.example.gestorequipos.modelo.Usuario
import java.time.format.DateTimeFormatter

val formatoFecha: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
val formatoFechaHora: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

val ColorVerde = Color(0xFF2E7D32)
val ColorRojo = Color(0xFFC62828)
val ColorNaranja = Color(0xFFEF6C00)
val ColorAzul = Color(0xFF1565C0)
val ColorGris = Color(0xFF757575)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(
    titulo: String,
    onVolver: (() -> Unit)? = null,
    acciones: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(titulo) },
        navigationIcon = {
            if (onVolver != null) {
                IconButton(onClick = onVolver) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        },
        actions = acciones
    )
}

@Composable
fun Etiqueta(texto: String, color: Color) {
    Surface(color = color, shape = RoundedCornerShape(50)) {
        Text(
            text = texto,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ChipEstadoTarea(estado: EstadoTarea) {
    when (estado) {
        EstadoTarea.PENDIENTE -> Etiqueta("Pendiente", ColorGris)
        EstadoTarea.EN_PROGRESO -> Etiqueta("En progreso", ColorAzul)
        EstadoTarea.EN_REVISION -> Etiqueta("En revisión", ColorNaranja)
        EstadoTarea.HECHA -> Etiqueta("Hecha", ColorVerde)
    }
}

@Composable
fun CampoDeTexto(etiqueta: String) {
    var texto by remember { mutableStateOf("") }
    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        label = { Text(etiqueta) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DialogoSimple(
    titulo: String,
    textoConfirmar: String,
    onCerrar: () -> Unit,
    contenido: @Composable ColumnScope.() -> Unit
) {
    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text(titulo) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                contenido()
            }
        },
        confirmButton = {
            TextButton(onClick = onCerrar) { Text(textoConfirmar) }
        },
        dismissButton = {
            TextButton(onClick = onCerrar) { Text("Cancelar") }
        }
    )
}

@Composable
fun MenuDeMiembros(
    textoBoton: String,
    miembros: List<Usuario>,
    onElegir: (Usuario) -> Unit
) {
    var menuAbierto by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { menuAbierto = true }) {
            Text(textoBoton)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = menuAbierto, onDismissRequest = { menuAbierto = false }) {
            miembros.forEach { miembro ->
                DropdownMenuItem(
                    text = { Text(miembro.nombre) },
                    onClick = {
                        onElegir(miembro)
                        menuAbierto = false
                    }
                )
            }
        }
    }
}
