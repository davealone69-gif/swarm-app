package com.swarm.swarmapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var counter by remember { mutableIntStateOf(0) }
    var selectedFeature by remember { mutableStateOf<Feature?>(null) }

    val features = listOf(
        Feature("Dashboard", Icons.Default.Dashboard, "View key metrics and summaries"),
        Feature("Profile", Icons.Default.Person, "Manage your account settings"),
        Feature("Notifications", Icons.Default.Notifications, "Stay up to date with alerts"),
        Feature("Settings", Icons.Default.Settings, "Customize your experience"),
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("SwarmApp", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero counter
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Interactions", color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
                    Text("$counter", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        OutlinedButton(onClick = { if (counter > 0) counter-- }) { Text("−") }
                        Button(onClick = { counter++ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) { Text("+") }
                    }
                }
            }

            Text("Features", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

            features.chunked(2).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    row.forEach { feature ->
                        FeatureCard(feature = feature, modifier = Modifier.weight(1f)) { selectedFeature = feature }
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
    }

    selectedFeature?.let { f ->
        AlertDialog(
            onDismissRequest = { selectedFeature = null },
            icon = { Icon(f.icon, contentDescription = null) },
            title = { Text(f.title) },
            text = { Text(f.description) },
            confirmButton = { Button(onClick = { selectedFeature = null }) { Text("Got it") } }
        )
    }
}

data class Feature(val title: String, val icon: ImageVector, val description: String)

@Composable
fun FeatureCard(feature: Feature, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(feature.icon, contentDescription = feature.title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
            Text(feature.title, fontWeight = FontWeight.Medium, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}
