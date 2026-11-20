package com.example.gestorequipos.datos

import com.example.gestorequipos.modelo.Adjunto
import com.example.gestorequipos.modelo.Ciclo
import com.example.gestorequipos.modelo.Entrega
import com.example.gestorequipos.modelo.EstadoTarea
import com.example.gestorequipos.modelo.InformeContribucion
import com.example.gestorequipos.modelo.NivelPeso
import com.example.gestorequipos.modelo.PlantillaPesos
import com.example.gestorequipos.modelo.Proyecto
import com.example.gestorequipos.modelo.Tarea
import com.example.gestorequipos.modelo.TipoAdjunto
import com.example.gestorequipos.modelo.TipoPlan
import com.example.gestorequipos.modelo.Usuario
import java.time.LocalDate
import java.time.LocalDateTime

object DatosEjemplo {

    val ana = Usuario("u1", "Ana Torres", "ana.torres@correo.com", TipoPlan.GRATIS)
    val bruno = Usuario("u2", "Bruno Díaz", "bruno.diaz@correo.com", TipoPlan.PREMIUM)
    val camila = Usuario("u3", "Camila Rojas", "camila.rojas@correo.com", TipoPlan.GRATIS)
    val diego = Usuario("u4", "Diego Pérez", "diego.perez@correo.com", TipoPlan.PREMIUM)

    val usuarioActual = ana

    val tallaS = NivelPeso("S", 1)
    val tallaM = NivelPeso("M", 2)
    val tallaL = NivelPeso("L", 3)
    val tallaXL = NivelPeso("XL", 5)

    val plantillaTallas = PlantillaPesos(
        nombre = "Tallas de camiseta",
        niveles = listOf(tallaS, tallaM, tallaL, tallaXL)
    )

    val tareaDisenarPantallas = Tarea(
        titulo = "Diseñar las pantallas en Figma",
        fechaLimite = LocalDate.of(2026, 11, 13),
        responsable = camila,
        peso = tallaL,
        cantidadVotos = 4,
        entregas = listOf(
            Entrega(
                autor = camila,
                mensaje = "Les dejo el diseño final y el enlace al prototipo.",
                fecha = LocalDateTime.of(2026, 11, 12, 18, 30),
                adjuntos = listOf(
                    Adjunto(TipoAdjunto.PDF, "diseno_pantallas.pdf"),
                    Adjunto(TipoAdjunto.ENLACE, "https://figma.com/file/app-reciclaje")
                ),
                revisor = ana,
                aprobada = true
            )
        ),
        estado = EstadoTarea.HECHA,
        fueATiempo = true
    )

    val tareaHistoriasDeUsuario = Tarea(
        titulo = "Redactar las historias de usuario",
        fechaLimite = LocalDate.of(2026, 11, 14),
        responsable = ana,
        peso = tallaM,
        cantidadVotos = 4,
        entregas = listOf(
            Entrega(
                autor = ana,
                mensaje = "Primera versión de las historias de usuario.",
                fecha = LocalDateTime.of(2026, 11, 14, 10, 0),
                adjuntos = listOf(
                    Adjunto(TipoAdjunto.NOTA, "Son 8 historias, todavía sin criterios de aceptación.")
                ),
                revisor = bruno,
                aprobada = false
            ),
            Entrega(
                autor = ana,
                mensaje = "Agregué los criterios de aceptación que faltaban.",
                fecha = LocalDateTime.of(2026, 11, 15, 21, 15),
                adjuntos = listOf(
                    Adjunto(TipoAdjunto.PDF, "historias_de_usuario_v2.pdf")
                ),
                revisor = bruno,
                aprobada = true
            )
        ),
        estado = EstadoTarea.HECHA,
        fueATiempo = false
    )

    val tareaConfigurarRepositorio = Tarea(
        titulo = "Configurar el repositorio",
        fechaLimite = LocalDate.of(2026, 11, 18),
        responsable = diego,
        peso = tallaS,
        cantidadVotos = 4,
        entregas = listOf(
            Entrega(
                autor = diego,
                mensaje = "Repositorio creado con la estructura base del proyecto.",
                fecha = LocalDateTime.of(2026, 11, 17, 16, 45),
                adjuntos = listOf(
                    Adjunto(TipoAdjunto.ENLACE, "https://github.com/equipo/app-reciclaje"),
                    Adjunto(TipoAdjunto.NOTA, "La rama principal está protegida.")
                ),
                revisor = null,
                aprobada = null
            )
        ),
        estado = EstadoTarea.EN_REVISION,
        fueATiempo = true
    )

    val tareaInicioDeSesion = Tarea(
        titulo = "Implementar el inicio de sesión",
        fechaLimite = LocalDate.of(2026, 11, 20),
        responsable = bruno,
        peso = null,
        cantidadVotos = 2,
        entregas = emptyList(),
        estado = EstadoTarea.EN_PROGRESO,
        fueATiempo = false
    )

    val tareaPresentacion = Tarea(
        titulo = "Preparar la presentación final",
        fechaLimite = LocalDate.of(2026, 11, 22),
        responsable = null,
        peso = null,
        cantidadVotos = 0,
        entregas = emptyList(),
        estado = EstadoTarea.PENDIENTE,
        fueATiempo = false
    )

    val cicloPrototipo = Ciclo(
        meta = "Tener un prototipo navegable de la app",
        inicio = LocalDate.of(2026, 11, 9),
        fin = LocalDate.of(2026, 11, 22),
        coordinador = bruno,
        tareas = listOf(
            tareaDisenarPantallas,
            tareaHistoriasDeUsuario,
            tareaConfigurarRepositorio,
            tareaInicioDeSesion,
            tareaPresentacion
        ),
        cerrado = false,
        metaCumplida = false
    )

    val proyectoReciclaje = Proyecto(
        nombre = "App de reciclaje",
        creador = ana,
        miembros = listOf(ana, bruno, camila, diego),
        plantilla = plantillaTallas,
        recompensasActivas = true,
        ciclos = listOf(cicloPrototipo)
    )

    val tareaBuscarFuentes = Tarea(
        titulo = "Buscar fuentes bibliográficas",
        fechaLimite = LocalDate.of(2026, 11, 19),
        responsable = diego,
        peso = null,
        cantidadVotos = 0,
        entregas = listOf(
            Entrega(
                autor = diego,
                mensaje = "Encontré cinco libros y dos artículos útiles.",
                fecha = LocalDateTime.of(2026, 11, 18, 20, 0),
                adjuntos = listOf(
                    Adjunto(TipoAdjunto.ENLACE, "https://biblioteca.universidad.edu/lista-fuentes")
                ),
                revisor = ana,
                aprobada = true
            )
        ),
        estado = EstadoTarea.HECHA,
        fueATiempo = true
    )

    val tareaEscribirIntroduccion = Tarea(
        titulo = "Escribir la introducción",
        fechaLimite = LocalDate.of(2026, 11, 24),
        responsable = ana,
        peso = null,
        cantidadVotos = 0,
        entregas = emptyList(),
        estado = EstadoTarea.EN_PROGRESO,
        fueATiempo = false
    )

    val cicloBorrador = Ciclo(
        meta = "Terminar el borrador del ensayo",
        inicio = LocalDate.of(2026, 11, 16),
        fin = LocalDate.of(2026, 11, 27),
        coordinador = ana,
        tareas = listOf(tareaBuscarFuentes, tareaEscribirIntroduccion),
        cerrado = false,
        metaCumplida = false
    )

    val proyectoEnsayo = Proyecto(
        nombre = "Ensayo de Historia",
        creador = ana,
        miembros = listOf(ana, bruno, diego),
        plantilla = null,
        recompensasActivas = false,
        ciclos = listOf(cicloBorrador)
    )

    val proyectos = listOf(proyectoReciclaje, proyectoEnsayo)

    val informeReciclaje = listOf(
        InformeContribucion(ana, tareasHechas = 1, porcentajeATiempo = 0.0, puntos = 10, racha = 0),
        InformeContribucion(bruno, tareasHechas = 0, porcentajeATiempo = 0.0, puntos = 0, racha = 0),
        InformeContribucion(camila, tareasHechas = 1, porcentajeATiempo = 100.0, puntos = 30, racha = 2),
        InformeContribucion(diego, tareasHechas = 0, porcentajeATiempo = 0.0, puntos = 5, racha = 1)
    )

    val informeEnsayo = listOf(
        InformeContribucion(ana, tareasHechas = 0, porcentajeATiempo = 0.0, puntos = null, racha = null),
        InformeContribucion(bruno, tareasHechas = 0, porcentajeATiempo = 0.0, puntos = null, racha = null),
        InformeContribucion(diego, tareasHechas = 1, porcentajeATiempo = 100.0, puntos = null, racha = null)
    )

    val informesPorProyecto = mapOf(
        proyectoReciclaje to informeReciclaje,
        proyectoEnsayo to informeEnsayo
    )
}
