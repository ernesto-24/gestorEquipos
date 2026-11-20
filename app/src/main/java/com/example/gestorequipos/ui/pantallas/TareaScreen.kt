package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.datos.DatosEjemplo
import com.example.gestorequipos.modelo.Adjunto
import com.example.gestorequipos.modelo.Entrega
import com.example.gestorequipos.modelo.NivelPeso
import com.example.gestorequipos.modelo.PlantillaPesos
import com.example.gestorequipos.modelo.Proyecto
import com.example.gestorequipos.modelo.Tarea
import com.example.gestorequipos.modelo.TipoAdjunto
import com.example.gestorequipos.modelo.Usuario
import com.example.gestorequipos.ui.BarraSuperior
import com.example.gestorequipos.ui.CampoDeTexto
import com.example.gestorequipos.ui.ChipEstadoTarea
import com.example.gestorequipos.ui.ColorGris
import com.example.gestorequipos.ui.ColorNaranja
import com.example.gestorequipos.ui.ColorVerde
import com.example.gestorequipos.ui.DialogoSimple
import com.example.gestorequipos.ui.Etiqueta
import com.example.gestorequipos.ui.MenuDeMiembros
import com.example.gestorequipos.ui.formatoFecha
import com.example.gestorequipos.ui.formatoFechaHora
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun TareaScreen(
    tarea: Tarea,
    proyecto: Proyecto,
    onVolver: () -> Unit
) {
    val plantilla = proyecto.plantilla
    var mostrarDialogoEntrega by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { BarraSuperior(titulo = "Tarea", onVolver = onVolver) }
    ) { espacioInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { TarjetaDatosTarea(tarea, proyecto.miembros) }
            if (plantilla != null) {
                item {
                    SeccionVotacion(
                        plantilla = plantilla,
                        cantidadVotos = tarea.cantidadVotos,
                        totalMiembros = proyecto.miembros.size,
                        pesoResultante = tarea.peso
                    )
                }
            }
            item { Text("Entregas", style = MaterialTheme.typography.titleMedium) }
            if (tarea.entregas.isEmpty()) {
                item { Text("Todavía no hay entregas.") }
            }
            items(tarea.entregas) { entrega ->
                TarjetaEntrega(entrega)
            }
            item {
                Button(onClick = { mostrarDialogoEntrega = true }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Nueva entrega")
                }
            }
        }
    }

    if (mostrarDialogoEntrega) {
        DialogoNuevaEntrega(onCerrar = { mostrarDialogoEntrega = false })
    }
}

@Composable
private fun TarjetaDatosTarea(tarea: Tarea, miembros: List<Usuario>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(tarea.titulo, style = MaterialTheme.typography.titleLarge)
            Text("Fecha límite: ${tarea.fechaLimite.format(formatoFecha)}")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Estado: ")
                ChipEstadoTarea(tarea.estado)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Responsable: ${tarea.responsable?.nombre ?: "Sin asignar"}",
                    modifier = Modifier.weight(1f)
                )
                MenuDeMiembros(textoBoton = "Asignar", miembros = miembros, onElegir = {})
            }
        }
    }
}

@Composable
private fun SeccionVotacion(
    plantilla: PlantillaPesos,
    cantidadVotos: Int,
    totalMiembros: Int,
    pesoResultante: NivelPeso?
) {
    var nivelElegido by remember { mutableStateOf<NivelPeso?>(null) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Votación de peso", style = MaterialTheme.typography.titleMedium)
            Text(plantilla.nombre, style = MaterialTheme.typography.bodySmall)
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                plantilla.niveles.forEach { nivel ->
                    FilterChip(
                        selected = nivel == nivelElegido,
                        onClick = { nivelElegido = nivel },
                        label = { Text("${nivel.etiqueta} (${nivel.valor})") }
                    )
                }
            }
            Text("Han votado $cantidadVotos de $totalMiembros")
            if (pesoResultante != null) {
                Text(
                    text = "Peso resultante: ${pesoResultante.etiqueta} (${pesoResultante.valor})",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TarjetaEntrega(entrega: Entrega) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = entrega.autor.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f)
                )
                EtiquetaEstadoEntrega(entrega.aprobada)
            }
            Text(entrega.fecha.format(formatoFechaHora), style = MaterialTheme.typography.bodySmall)
            Text(entrega.mensaje)
            entrega.adjuntos.forEach { adjunto ->
                FilaAdjunto(adjunto)
            }
            if (entrega.aprobada == null) {
                BotonesDeRevision()
            }
        }
    }
}

@Composable
private fun EtiquetaEstadoEntrega(aprobada: Boolean?) {
    when (aprobada) {
        null -> Etiqueta("Pendiente", ColorGris)
        true -> Etiqueta("Aprobada", ColorVerde)
        false -> Etiqueta("Cambios pedidos", ColorNaranja)
    }
}

@Composable
private fun FilaAdjunto(adjunto: Adjunto) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(iconoDeAdjunto(adjunto.tipo), contentDescription = textoDeTipoAdjunto(adjunto.tipo))
        Text(adjunto.contenido, modifier = Modifier.padding(start = 8.dp))
    }
}

private fun iconoDeAdjunto(tipo: TipoAdjunto): ImageVector {
    return when (tipo) {
        TipoAdjunto.PDF -> Icons.Default.Info
        TipoAdjunto.ENLACE -> Icons.Default.Share
        TipoAdjunto.NOTA -> Icons.Default.Edit
    }
}

private fun textoDeTipoAdjunto(tipo: TipoAdjunto): String {
    return when (tipo) {
        TipoAdjunto.PDF -> "PDF"
        TipoAdjunto.ENLACE -> "Enlace"
        TipoAdjunto.NOTA -> "Nota"
    }
}

@Composable
private fun BotonesDeRevision() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(onClick = {}) { Text("Aprobar") }
        OutlinedButton(onClick = {}) { Text("Pedir cambios") }
    }
}

@Composable
private fun DialogoNuevaEntrega(onCerrar: () -> Unit) {
    var tipoElegido by remember { mutableStateOf(TipoAdjunto.PDF) }

    DialogoSimple(titulo = "Nueva entrega", textoConfirmar = "Enviar", onCerrar = onCerrar) {
        CampoDeTexto("Mensaje")
        Text("Tipo de adjunto")
        SelectorTipoAdjunto(tipoElegido, onElegir = { tipoElegido = it })
        CampoDeTexto("Contenido del adjunto")
    }
}

@Composable
private fun SelectorTipoAdjunto(tipoElegido: TipoAdjunto, onElegir: (TipoAdjunto) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        TipoAdjunto.entries.forEach { tipo ->
            FilterChip(
                selected = tipo == tipoElegido,
                onClick = { onElegir(tipo) },
                label = { Text(textoDeTipoAdjunto(tipo)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaTareaScreen() {
    GestorEquiposTheme {
        TareaScreen(
            tarea = DatosEjemplo.tareaConfigurarRepositorio,
            proyecto = DatosEjemplo.proyectoReciclaje,
            onVolver = {}
        )
    }
}
