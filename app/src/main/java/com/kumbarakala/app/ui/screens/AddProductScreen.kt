package com.kumbarakala.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kumbarakala.app.ui.components.ErrorDialog
import com.kumbarakala.app.ui.components.LoadingOverlay
import com.kumbarakala.app.ui.theme.DeepBrown
import com.kumbarakala.app.ui.theme.SoftBeige
import com.kumbarakala.app.ui.theme.Terracotta
import com.kumbarakala.app.ui.theme.WarmCream
import com.kumbarakala.app.ui.viewmodel.AddProductViewModel

private val categoryOptions = listOf("Pots", "Lamps", "Utensils", "Decor")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var name by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { viewModel.setPhoto(it) } }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) { viewModel.reset(); onBack() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Product", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Terracotta, titleContentColor = WarmCream,
                    navigationIconContentColor = WarmCream
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Photo Picker with enhanced styling
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .shadow(6.dp, RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(SoftBeige)
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.photoUri != null) {
                        AsyncImage(
                            model = uiState.photoUri, contentDescription = "Product photo",
                            contentScale = ContentScale.Crop, modifier = Modifier.matchParentSize()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, "Add Photo",
                                modifier = Modifier.size(52.dp), tint = Terracotta)
                            Spacer(Modifier.height(10.dp))
                            Text("Tap to add photo", color = Terracotta,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium)
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text("Product Name") }, singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Terracotta,
                        focusedLabelColor = Terracotta,
                        cursorColor = Terracotta,
                        unfocusedBorderColor = Terracotta.copy(alpha = 0.25f),
                        unfocusedContainerColor = SoftBeige.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))

                // Category Dropdown
                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value = selectedCategory, onValueChange = {},
                        readOnly = true, label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Terracotta,
                            focusedLabelColor = Terracotta,
                            unfocusedBorderColor = Terracotta.copy(alpha = 0.25f),
                            unfocusedContainerColor = SoftBeige.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categoryOptions.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = { selectedCategory = cat; expanded = false }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = description, onValueChange = { description = it },
                    label = { Text("Description") }, minLines = 3, maxLines = 5,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Terracotta,
                        focusedLabelColor = Terracotta,
                        cursorColor = Terracotta,
                        unfocusedBorderColor = Terracotta.copy(alpha = 0.25f),
                        unfocusedContainerColor = SoftBeige.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = price, onValueChange = { price = it },
                    label = { Text("Price (e.g. Rs. 200-300)") }, singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Terracotta,
                        focusedLabelColor = Terracotta,
                        cursorColor = Terracotta,
                        unfocusedBorderColor = Terracotta.copy(alpha = 0.25f),
                        unfocusedContainerColor = SoftBeige.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(28.dp))

                Button(
                    onClick = { viewModel.saveProduct(name, selectedCategory, description, price) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .shadow(8.dp, RoundedCornerShape(14.dp)),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                ) {
                    Text(
                        "Save Product",
                        color = WarmCream,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(32.dp))
            }
            LoadingOverlay(isLoading = uiState.isLoading)
            uiState.error?.let { ErrorDialog(it) { viewModel.clearError() } }
        }
    }
}
