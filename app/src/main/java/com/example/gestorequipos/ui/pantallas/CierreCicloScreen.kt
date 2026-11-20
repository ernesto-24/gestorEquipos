package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.datos.DatosEjemplo
import com.example.gestorequipos.modelo.Ciclo
import com.example.gestorequipos.modelo.Entrega
import com.example.gestorequipos.modelo.InformeContribucion
import com.example.gestorequipos.modelo.Proyecto
import com.example.gestorequipos.modelo.Tarea
import com.example.gestorequipos.ui.BarraSuperior
import com.example.gestorequipos.ui.ColorRojo
import com.example.gestorequipos.ui.ColorVerde
import com.example.gestorequipos.ui.Etiqueta
import com.example.gestorequipos.ui.formatoFechaHora
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun CierreCicloScreen(
    proyecto: Proyecto,
    informe: List<InformeContribucion>,
    onVolverAlProyecto: () -> Unit
) {
    val ciclo = proyecto.ciclos.last()
    val entregasOrdenadas = ciclo.tareas
        .flatMap { tarea -> tarea.entregas.map { entrega -> tarea to entrega } }
        .sortedBy { (_, entrega) -> entrega.fecha }

    Scaffold(
        topBar = { BarraSuperior(titulo = "Cierre del ciclo") }
    ) { espacioInterno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { TarjetaMetaDelCiclo(ciclo) }
            item { Text("Línea de tiempo", style = MaterialTheme.typography.titleMedium) }
            itemsIndexed(entregasOrdenadas) { indice, (tarea, entrega) ->
                ElementoLineaDeTiempo(
                    tarea = tarea,
                    entrega = entrega,
                    esElUltimo = indice == entregasOrdenadas.lastIndex
                )
            }
            item { Text("Informe de contribución", style = MaterialTheme.typography.titleMedium) }
            items(informe) { contribucion ->
                TarjetaContribucion(contribucion, mostrarRecompensas = proyecto.recompensasActivas)
            }
            item {
                Button(onClick = onVolverAlProyecto, modifier = Modifier.fillMaxWidth()) {
                    Text("Volver al proyecto")
                }
            }
        }
    }
}

@Composable
private fun TarjetaMetaDelCiclo(ciclo: Ciclo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Meta del ciclo", style = MaterialTheme.typography.labelLarge)
            Text(ciclo.meta, style = MaterialTheme.typography.titleMedium)
            if (ciclo.metaCumplida) {
                Etiqueta("Meta cumplida", ColorVerde)
            } else {
                Etiqueta("Meta no cumplida", ColorRojo)
            }
        }
    }
}

@Composable
private fun ElementoLineaDeTiempo(tarea: Tarea, entrega: Entrega, esElUltimo: Boolean) {
    val llegoATiempo = entrega.fecha.toLocalDate() <= tarea.fechaLimite

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        MarcadorLineaDeTiempo(mostrarLinea = !esElUltimo)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(entrega.fecha.format(formatoFechaHora), style = MaterialTheme.typography.labelMedium)
            Text(entrega.autor.nombre, style = MaterialTheme.typography.titleSmall)
            Text(tarea.titulo, style = MaterialTheme.typography.bodyMedium)
            if (llegoATiempo) {
                Etiqueta("A tiempo", ColorVerde)
            } else {
                Etiqueta("Tarde", ColorRojo)
            }
        }
    }
}

@Composable
private fun MarcadorLineaDeTiempo(mostrarLinea: Boolean) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )
        if (mostrarLinea) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.outline)
            )
        }
    }
}

@Composable
private fun TarjetaContribucion(contribucion: InformeContribucion, mostrarRecompensas: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(contribucion.usuario.nombre, style = MaterialTheme.typography.titleMedium)
            Text("Tareas hechas: ${contribucion.tareasHechas}")
            Text("A tiempo: ${contribucion.porcentajeATiempo.toInt()}%")
            if (mostrarRecompensas) {
                Text("Puntos: ${contribucion.puntos ?: 0}")
                Text("Racha: ${contribucion.racha ?: 0}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaCierreCicloScreen() {
    GestorEquiposTheme {
        CierreCicloScreen(
            proyecto = DatosEjemplo.proyectoReciclaje,
            informe = DatosEjemplo.informeReciclaje,
            onVolverAlProyecto = {}
        )
    }
}
