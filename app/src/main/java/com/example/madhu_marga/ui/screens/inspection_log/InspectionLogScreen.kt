package com.example.madhu_marga.ui.screens.inspection_log

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.madhu_marga.ui.theme.HoneyYellow
import com.example.madhu_marga.ui.viewmodel.HiveViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspectionLogScreen(
    viewModel: HiveViewModel,
    onNavigateBack: () -> Unit
) {
    val hives by viewModel.allHives.collectAsStateWithLifecycle()
    
    var selectedHiveId by remember { mutableStateOf<Int?>(null) }
    var queenPresent by remember { mutableStateOf(true) }
    var pestsSeen by remember { mutableStateOf(false) }
    var honeyFlow by remember { mutableStateOf("Medium") }
    var activityLevel by remember { mutableStateOf("Normal") }
    var notes by remember { mutableStateOf("") }
    
    var showHivePicker by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inspection Log", fontWeight = FontWeight.Bold) },
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
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hive Selection
            Text(
                text = "Select Hive", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Box {
                val selectedHive = hives.find { it.id == selectedHiveId }
                OutlinedButton(
                    onClick = { showHivePicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text(selectedHive?.hiveId ?: "Tap to choose a hive")
                }
                DropdownMenu(expanded = showHivePicker, onDismissRequest = { showHivePicker = false }) {
                    hives.forEach { hive ->
                        DropdownMenuItem(
                            text = { Text(hive.hiveId) },
                            onClick = {
                                selectedHiveId = hive.id
                                showHivePicker = false
                            }
                        )
                    }
                }
            }

            // Date Selection
            Text(
                text = "Date", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                } ?: SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
                Text(selectedDate)
            }

            // Queen Present
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Queen Bee Present?", 
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = queenPresent,
                    onCheckedChange = { queenPresent = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = HoneyYellow)
                )
            }

            // Pests
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pests Seen? (Mites, etc.)", 
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = pestsSeen,
                    onCheckedChange = { pestsSeen = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.Red)
                )
            }

            // Honey Flow
            Text(
                text = "Honey Flow Level", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            SegmentedButtons(
                options = listOf("Low", "Medium", "High"),
                selectedOption = honeyFlow,
                onOptionSelected = { honeyFlow = it }
            )

            // Activity Level
            Text(
                text = "Activity Level", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            SegmentedButtons(
                options = listOf("Low", "Normal", "High"),
                selectedOption = activityLevel,
                onOptionSelected = { activityLevel = it }
            )

            // Notes
            Text(
                text = "Notes", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                placeholder = { Text("Any specific observations...") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedHiveId?.let { id ->
                        val date = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                        viewModel.addInspection(id, queenPresent, pestsSeen, honeyFlow, activityLevel, notes, date)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = selectedHiveId != null,
                colors = ButtonDefaults.buttonColors(containerColor = HoneyYellow),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Log", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SegmentedButtons(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            OutlinedButton(
                onClick = { onOptionSelected(option) },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isSelected) HoneyYellow.copy(alpha = 0.2f) else Color.Transparent,
                    contentColor = if (isSelected) HoneyYellow else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                border = BorderStroke(1.dp, if (isSelected) HoneyYellow else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            ) {
                Text(option, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
