package com.example.gestorequipos.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorequipos.modelo.TipoPlan
import com.example.gestorequipos.ui.BarraSuperior
import com.example.gestorequipos.ui.theme.GestorEquiposTheme

@Composable
fun PremiumScreen(
    onMejorarAPremium: () -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = { BarraSuperior(titulo = "Premium", onVolver = onVolver) }
    ) { espacioInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioInterno)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Compara los planes", style = MaterialTheme.typography.headlineSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                TarjetaPlan(TipoPlan.GRATIS, modifier = Modifier.weight(1f))
                TarjetaPlan(TipoPlan.PREMIUM, modifier = Modifier.weight(1f))
            }
            Button(onClick = onMejorarAPremium, modifier = Modifier.fillMaxWidth()) {
                Text("Mejorar a Premium")
            }
        }
    }
}

@Composable
private fun TarjetaPlan(plan: TipoPlan, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(plan.name, style = MaterialTheme.typography.titleLarge)
            Text("Proyectos máximos", style = MaterialTheme.typography.bodySmall)
            Text(plan.maxProyectos?.toString() ?: "Ilimitados", fontWeight = FontWeight.Bold)
            Text("Anuncios", style = MaterialTheme.typography.bodySmall)
            Text(if (plan.conAnuncios) "Sí" else "No", fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VistaPreviaPremiumScreen() {
    GestorEquiposTheme {
        PremiumScreen(onMejorarAPremium = {}, onVolver = {})
    }
}
