package com.example.gestorequipos.modelo

import java.time.LocalDate
import java.time.LocalDateTime

enum class TipoPlan(val maxProyectos: Int?, val conAnuncios: Boolean) {
    GRATIS(maxProyectos = 3, conAnuncios = true),
    PREMIUM(maxProyectos = null, conAnuncios = false)
}

data class Usuario(
    val id: String,
    val nombre: String,
    val correo: String,
    val plan: TipoPlan
)

data class NivelPeso(
    val etiqueta: String,
    val valor: Int
)

data class PlantillaPesos(
    val nombre: String,
    val niveles: List<NivelPeso>
)

enum class TipoAdjunto { PDF, ENLACE, NOTA }

data class Adjunto(
    val tipo: TipoAdjunto,
    val contenido: String
)

data class Entrega(
    val autor: Usuario,
    val mensaje: String,
    val fecha: LocalDateTime,
    val adjuntos: List<Adjunto>,
    val revisor: Usuario?,
    val aprobada: Boolean?
)

enum class EstadoTarea { PENDIENTE, EN_PROGRESO, EN_REVISION, HECHA }

data class Tarea(
    val titulo: String,
    val fechaLimite: LocalDate,
    val responsable: Usuario?,
    val peso: NivelPeso?,
    val cantidadVotos: Int,
    val entregas: List<Entrega>,
    val estado: EstadoTarea,
    val fueATiempo: Boolean
)

data class Ciclo(
    val meta: String,
    val inicio: LocalDate,
    val fin: LocalDate,
    val coordinador: Usuario,
    val tareas: List<Tarea>,
    val cerrado: Boolean,
    val metaCumplida: Boolean
)

data class Proyecto(
    val nombre: String,
    val creador: Usuario,
    val miembros: List<Usuario>,
    val plantilla: PlantillaPesos?,
    val recompensasActivas: Boolean,
    val ciclos: List<Ciclo>
)

data class InformeContribucion(
    val usuario: Usuario,
    val tareasHechas: Int,
    val porcentajeATiempo: Double,
    val puntos: Int?,
    val racha: Int?
)
