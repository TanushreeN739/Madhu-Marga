package com.example.madhu_marga.ui.screens.harvest_tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.madhu_marga.data.local.entities.HarvestEntity
import com.example.madhu_marga.ui.theme.Amber
import com.example.madhu_marga.ui.theme.HoneyYellow
import com.example.madhu_marga.ui.viewmodel.HiveViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HarvestTrackerScreen(
    viewModel: HiveViewModel,
    onNavigateBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val harvests by viewModel.allHarvests.collectAsStateWithLifecycle()
    val hives by viewModel.allHives.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Harvest Tracker", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HoneyYellow,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = HoneyYellow,
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Harvest")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HarvestSummaryCard(harvests) }
            item { ProductionChart(harvests) }
            item {
                Text(
                    text = "Recent Harvests",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            if (harvests.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No harvests recorded yet.", 
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                }
            } else {
                items(harvests) { harvest ->
                    val hiveName = hives.find { it.id == harvest.hiveId }?.hiveId ?: "Unknown Hive"
                    HarvestItem(harvest, hiveName)
                }
            }
        }

        if (showDialog) {
            AddHarvestDialog(
                hives = hives,
                onDismiss = { showDialog = false },
                onConfirm = { hiveId, amount, date ->
                    viewModel.addHarvest(hiveId, amount, date)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun HarvestSummaryCard(harvests: List<HarvestEntity>) {
    val totalAmount = harvests.sumOf { it.amount }
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val thisYearAmount = harvests.filter { 
        val cal = Calendar.getInstance().apply { timeInMillis = it.date }
        cal.get(Calendar.YEAR) == thisYear
    }.sumOf { it.amount }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Production Overview", 
                style = MaterialTheme.typography.labelLarge, 
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = String.format(Locale.getDefault(), "%.1f kg", totalAmount),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Amber
            )
            Text(
                text = "Lifetime Total", 
                style = MaterialTheme.typography.bodySmall, 
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp), 
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = "This Year", 
                        style = MaterialTheme.typography.labelSmall, 
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "%.1f kg", thisYearAmount), 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                val target = 100.0
                val progress = (thisYearAmount / target).coerceIn(0.0, 1.0).toFloat()
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.size(48.dp),
                        color = HoneyYellow,
                        strokeWidth = 6.dp,
                        trackColor = HoneyYellow.copy(alpha = 0.1f)
                    )
                    Text(
                        text = "${(progress * 100).toInt()}%", 
                        fontSize = 10.sp, 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
fun ProductionChart(harvests: List<HarvestEntity>) {
    val yearlyData = harvests.groupBy { 
        Calendar.getInstance().apply { timeInMillis = it.date }.get(Calendar.YEAR)
    }.mapValues { entry -> entry.value.sumOf { it.amount } }
    .toSortedMap()

    if (yearlyData.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = HoneyYellow)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Yearly Production", 
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val maxVal = yearlyData.values.maxOrNull() ?: 1.0
                yearlyData.forEach { (year, amount) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val barHeight = (amount / maxVal).coerceIn(0.1, 1.0).toFloat()
                        Box(
                            modifier = Modifier
                                .width(32.dp)
                                .fillMaxHeight(barHeight)
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(HoneyYellow)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = year.toString().takeLast(2), 
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HarvestItem(harvest: HarvestEntity, hiveName: String) {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val dateString = sdf.format(Date(harvest.date))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = HoneyYellow.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Scale,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = HoneyYellow
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Hive: $hiveName", 
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = dateString, 
                    style = MaterialTheme.typography.bodySmall, 
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            Text(
                text = String.format(Locale.getDefault(), "+%.1f kg", harvest.amount),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHarvestDialog(
    hives: List<com.example.madhu_marga.data.local.entities.HiveEntity>,
    onDismiss: () -> Unit,
    onConfirm: (Int, Double, Long) -> Unit
) {
    var selectedHiveId by remember { mutableStateOf<Int?>(null) }
    var amount by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Harvest") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Select Hive", style = MaterialTheme.typography.labelLarge)
                Box {
                    val selectedHive = hives.find { it.id == selectedHiveId }
                    OutlinedButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(selectedHive?.hiveId ?: "Choose Hive")
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        hives.forEach { hive ->
                            DropdownMenuItem(
                                text = { Text(hive.hiveId) },
                                onClick = {
                                    selectedHiveId = hive.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                
                OutlinedTextField(
                    value = amount,
                    onValueChange = { if (it.isEmpty() || it.toDoubleOrNull() != null) amount = it },
                    label = { Text("Amount (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                    } ?: "Select Date"
                    Text(selectedDate)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { 
                    val amt = amount.toDoubleOrNull()
                    val date = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    if (selectedHiveId != null && amt != null) {
                        onConfirm(selectedHiveId!!, amt, date)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = HoneyYellow),
                enabled = selectedHiveId != null && amount.isNotEmpty()
            ) {
                Text("Save", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
