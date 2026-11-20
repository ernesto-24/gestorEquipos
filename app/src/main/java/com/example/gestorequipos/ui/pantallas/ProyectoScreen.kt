package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.datos.DatosEjemplo
import com.example.gestorequipos.modelo.Ciclo
import com.example.gestorequipos.modelo.Proyecto
import com.example.gestorequipos.modelo.Tarea
import com.example.gestorequipos.modelo.Usuario
import com.example.gestorequipos.ui.BarraSuperior
import com.example.gestorequipos.ui.CampoDeTexto
import com.example.gestorequipos.ui.ChipEstadoTarea
import com.example.gestorequipos.ui.DialogoSimple
import com.example.gestorequipos.ui.MenuDeMiembros
import com.example.gestorequipos.ui.formatoFecha
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun ProyectoScreen(
    proyecto: Proyecto,
    onVolver: () -> Unit,
    onAbrirTarea: (Tarea) -> Unit,
    onCerrarCiclo: () -> Unit
) {
    val cicloActual = proyecto.ciclos.last()
    var mostrarDialogoMiembro by remember { mutableStateOf(false) }
    var mostrarDialogoTarea by remember { mutableStateOf(false) }
    var mostrarDialogoCiclo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { BarraSuperior(titulo = proyecto.nombre, onVolver = onVolver) }
    ) { espacioInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SeccionMiembros(proyecto.miembros, onAgregarMiembro = { mostrarDialogoMiembro = true })
            }
            item { InterruptoresDelProyecto(proyecto) }
            item { TarjetaCicloActual(cicloActual) }
            item { Text("Tareas del ciclo", style = MaterialTheme.typography.titleMedium) }
            items(cicloActual.tareas) { tarea ->
                FilaTarea(tarea, onClick = { onAbrirTarea(tarea) })
            }
            item {
                BotonesDelCiclo(
                    onNuevaTarea = { mostrarDialogoTarea = true },
                    onCerrarCiclo = onCerrarCiclo,
                    onAbrirNuevoCiclo = { mostrarDialogoCiclo = true }
                )
            }
        }
    }

    if (mostrarDialogoMiembro) {
        DialogoAgregarMiembro(onCerrar = { mostrarDialogoMiembro = false })
    }
    if (mostrarDialogoTarea) {
        DialogoNuevaTarea(onCerrar = { mostrarDialogoTarea = false })
    }
    if (mostrarDialogoCiclo) {
        DialogoNuevoCiclo(miembros = proyecto.miembros, onCerrar = { mostrarDialogoCiclo = false })
    }
}

@Composable
private fun SeccionMiembros(miembros: List<Usuario>, onAgregarMiembro: () -> Unit) {
    Column {
        Text("Miembros", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            miembros.forEach { miembro ->
                AssistChip(
                    onClick = {},
                    label = { Text(miembro.nombre) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        }
        TextButton(onClick = onAgregarMiembro) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("Agregar miembro")
        }
    }
}

@Composable
private fun InterruptoresDelProyecto(proyecto: Proyecto) {
    var estimacionActiva by remember { mutableStateOf(proyecto.plantilla != null) }
    var recompensasActivas by remember { mutableStateOf(proyecto.recompensasActivas) }

    Column {
        FilaInterruptor("Estimación de pesos", estimacionActiva, onCambiar = { estimacionActiva = it })
        FilaInterruptor("Recompensas", recompensasActivas, onCambiar = { recompensasActivas = it })
    }
}

@Composable
private fun FilaInterruptor(texto: String, activado: Boolean, onCambiar: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(texto, modifier = Modifier.weight(1f))
        Switch(checked = activado, onCheckedChange = onCambiar)
    }
}

@Composable
private fun TarjetaCicloActual(ciclo: Ciclo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Ciclo actual", style = MaterialTheme.typography.titleMedium)
            Text("Meta: ${ciclo.meta}")
            Text("Del ${ciclo.inicio.format(formatoFecha)} al ${ciclo.fin.format(formatoFecha)}")
            Text("Coordinador: ${ciclo.coordinador.nombre}")
        }
    }
}

@Composable
private fun FilaTarea(tarea: Tarea, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(tarea.titulo, style = MaterialTheme.typography.titleSmall)
                Text(tarea.responsable?.nombre ?: "Sin asignar", style = MaterialTheme.typography.bodySmall)
                if (tarea.peso != null) {
                    Text("Peso: ${tarea.peso.etiqueta}", style = MaterialTheme.typography.bodySmall)
                }
            }
            ChipEstadoTarea(tarea.estado)
        }
    }
}

@Composable
private fun BotonesDelCiclo(
    onNuevaTarea: () -> Unit,
    onCerrarCiclo: () -> Unit,
    onAbrirNuevoCiclo: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedButton(onClick = onNuevaTarea, modifier = Modifier.fillMaxWidth()) {
            Text("Nueva tarea")
        }
        Button(onClick = onCerrarCiclo, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar ciclo")
        }
        OutlinedButton(onClick = onAbrirNuevoCiclo, modifier = Modifier.fillMaxWidth()) {
            Text("Abrir nuevo ciclo")
        }
    }
}

@Composable
private fun DialogoAgregarMiembro(onCerrar: () -> Unit) {
    DialogoSimple(titulo = "Agregar miembro", textoConfirmar = "Agregar", onCerrar = onCerrar) {
        CampoDeTexto("Correo del miembro")
    }
}

@Composable
private fun DialogoNuevaTarea(onCerrar: () -> Unit) {
    DialogoSimple(titulo = "Nueva tarea", textoConfirmar = "Crear", onCerrar = onCerrar) {
        CampoDeTexto("Título")
        CampoDeTexto("Fecha límite (dd/mm/aaaa)")
    }
}

@Composable
private fun DialogoNuevoCiclo(miembros: List<Usuario>, onCerrar: () -> Unit) {
    var coordinadorElegido by remember { mutableStateOf<Usuario?>(null) }

    DialogoSimple(titulo = "Abrir nuevo ciclo", textoConfirmar = "Abrir", onCerrar = onCerrar) {
        CampoDeTexto("Meta")
        CampoDeTexto("Fecha de inicio (dd/mm/aaaa)")
        CampoDeTexto("Fecha de fin (dd/mm/aaaa)")
        Text("Coordinador")
        MenuDeMiembros(
            textoBoton = coordinadorElegido?.nombre ?: "Elegir coordinador",
            miembros = miembros,
            onElegir = { coordinadorElegido = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaProyectoScreen() {
    GestorEquiposTheme {
        ProyectoScreen(
            proyecto = DatosEjemplo.proyectoReciclaje,
            onVolver = {},
            onAbrirTarea = {},
            onCerrarCiclo = {}
        )
    }
}
