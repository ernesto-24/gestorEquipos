package com.example.gestorequipos.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.gestorequipos.datos.DatosEjemplo
import com.example.gestorequipos.ui.pantallas.AccesoScreen
import com.example.gestorequipos.ui.pantallas.CierreCicloScreen
import com.example.gestorequipos.ui.pantallas.MisProyectosScreen
import com.example.gestorequipos.ui.pantallas.PremiumScreen
import com.example.gestorequipos.ui.pantallas.ProyectoScreen
import com.example.gestorequipos.ui.pantallas.TareaScreen

enum class Pantalla {
    ACCESO,
    MIS_PROYECTOS,
    PROYECTO,
    TAREA,
    CIERRE_CICLO,
    PREMIUM
}

@Composable
fun AppNavegacion() {
    var pantallaActual by remember { mutableStateOf(Pantalla.ACCESO) }
    var proyectoSeleccionado by remember { mutableStateOf(DatosEjemplo.proyectoReciclaje) }
    var tareaSeleccionada by remember { mutableStateOf(DatosEjemplo.tareaDisenarPantallas) }

    when (pantallaActual) {
        Pantalla.ACCESO -> AccesoScreen(
            onEntrar = { pantallaActual = Pantalla.MIS_PROYECTOS }
        )

        Pantalla.MIS_PROYECTOS -> MisProyectosScreen(
            usuario = DatosEjemplo.usuarioActual,
            proyectos = DatosEjemplo.proyectos,
            onAbrirProyecto = { proyecto ->
                proyectoSeleccionado = proyecto
                pantallaActual = Pantalla.PROYECTO
            },
            onHaztePremium = { pantallaActual = Pantalla.PREMIUM },
            onCerrarSesion = { pantallaActual = Pantalla.ACCESO }
        )

        Pantalla.PROYECTO -> ProyectoScreen(
            proyecto = proyectoSeleccionado,
            onVolver = { pantallaActual = Pantalla.MIS_PROYECTOS },
            onAbrirTarea = { tarea ->
                tareaSeleccionada = tarea
                pantallaActual = Pantalla.TAREA
            },
            onCerrarCiclo = { pantallaActual = Pantalla.CIERRE_CICLO }
        )

        Pantalla.TAREA -> TareaScreen(
            tarea = tareaSeleccionada,
            proyecto = proyectoSeleccionado,
            onVolver = { pantallaActual = Pantalla.PROYECTO }
        )

        Pantalla.CIERRE_CICLO -> CierreCicloScreen(
            proyecto = proyectoSeleccionado,
            informe = DatosEjemplo.informesPorProyecto[proyectoSeleccionado] ?: emptyList(),
            onVolverAlProyecto = { pantallaActual = Pantalla.PROYECTO }
        )

        Pantalla.PREMIUM -> PremiumScreen(
            onMejorarAPremium = { pantallaActual = Pantalla.MIS_PROYECTOS },
            onVolver = { pantallaActual = Pantalla.MIS_PROYECTOS }
        )
    }
}
