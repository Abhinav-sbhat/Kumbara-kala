package com.kumbarakala.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kumbarakala.app.ui.components.ErrorDialog
import com.kumbarakala.app.ui.components.StatsRow
import com.kumbarakala.app.ui.theme.*
import com.kumbarakala.app.ui.viewmodel.ProfileViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val artisan = uiState.artisan

    var name by remember(artisan) { mutableStateOf(artisan?.name ?: "") }
    var phone by remember(artisan) { mutableStateOf(artisan?.phone ?: "") }
    var village by remember(artisan) { mutableStateOf(artisan?.village ?: "") }
    var bio by remember(artisan) { mutableStateOf(artisan?.bio ?: "") }
    var experience by remember(artisan) { mutableStateOf(artisan?.experience ?: "") }
    var specialty by remember(artisan) { mutableStateOf(artisan?.specialty ?: "") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { viewModel.uploadPhoto(it) } }

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) viewModel.clearSuccessFlag()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "profile")
    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ringPulse"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Custom gradient header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GradientTerracottaStart,
                            GradientTerracottaEnd
                        )
                    )
                )
                .statusBarsPadding()
                .padding(bottom = 48.dp)
        ) {
            // Logout button
            IconButton(
                onClick = { viewModel.logout(); onLogout() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, "Logout", tint = WarmCream)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = WarmCream
                )
                Spacer(Modifier.height(20.dp))

                // Profile Photo with animated gold ring
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .border(
                                width = 3.dp,
                                brush = Brush.sweepGradient(
                                    listOf(
                                        Gold.copy(alpha = ringAlpha),
                                        GoldLight,
                                        Gold.copy(alpha = ringAlpha)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(SoftBeige)
                            .clickable { imagePickerLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (artisan?.profilePhoto?.isNotEmpty() == true &&
                            File(artisan.profilePhoto).exists()) {
                            AsyncImage(
                                model = File(artisan.profilePhoto), contentDescription = "Profile",
                                contentScale = ContentScale.Crop, modifier = Modifier.matchParentSize()
                            )
                        } else {
                            Icon(Icons.Default.Person, "Profile", Modifier.size(52.dp), tint = Terracotta)
                        }
                    }
                    Surface(
                        shape = CircleShape, color = Gold,
                        modifier = Modifier
                            .size(36.dp)
                            .shadow(4.dp, CircleShape)
                    ) {
                        Icon(Icons.Default.CameraAlt, "Change photo",
                            Modifier.padding(8.dp), tint = WarmCream)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Artisan name & village
                Text(
                    text = artisan?.name ?: "Artisan",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = WarmCream
                )
                if (artisan?.village?.isNotEmpty() == true) {
                    Text(
                        text = "📍 ${artisan.village}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmCream.copy(alpha = 0.8f)
                    )
                }
                if (artisan?.specialty?.isNotEmpty() == true) {
                    Spacer(Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = WarmCream.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "✨ ${artisan.specialty}",
                            style = MaterialTheme.typography.labelMedium,
                            color = WarmCream,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Content scrollable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-32).dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Stats Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                color = WarmCream
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📊 My Statistics",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = DeepBrown
                    )
                    Spacer(Modifier.height(12.dp))
                    uiState.stats?.let { StatsRow(stats = it) }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Contact Section
            SectionCard(title = "Contact Details", icon = Icons.Default.Phone) {
                ProfileField(
                    value = name, onValueChange = { name = it },
                    label = "Name", icon = Icons.Default.Person
                )
                Spacer(Modifier.height(10.dp))
                ProfileField(
                    value = phone, onValueChange = { phone = it },
                    label = "Phone", icon = Icons.Default.Phone
                )
                Spacer(Modifier.height(10.dp))
                ProfileField(
                    value = village, onValueChange = { village = it },
                    label = "Village", icon = Icons.Default.Place
                )
            }

            Spacer(Modifier.height(16.dp))

            // My Story Section
            SectionCard(title = "My Story", icon = Icons.Default.Edit) {
                ProfileField(
                    value = bio, onValueChange = { bio = it },
                    label = "Bio / Heritage Story", minLines = 3, maxLines = 5
                )
                Spacer(Modifier.height(10.dp))
                ProfileField(
                    value = experience, onValueChange = { experience = it },
                    label = "Years of Experience", icon = Icons.Default.WorkHistory
                )
                Spacer(Modifier.height(10.dp))
                ProfileField(
                    value = specialty, onValueChange = { specialty = it },
                    label = "Specialty", icon = Icons.Default.Star
                )
            }

            Spacer(Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = { viewModel.updateProfile(name, phone, village, bio, experience, specialty) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .shadow(8.dp, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
            ) {
                Text("Save Changes", color = WarmCream,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)
            }

            if (uiState.saveSuccess) {
                Spacer(Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SuccessGreen.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Profile saved!", color = SuccessGreen,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }

    uiState.error?.let { ErrorDialog(it) { viewModel.clearError() } }
}

@Composable
private fun SectionCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        color = WarmCream
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = Terracotta, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DeepBrown
                )
            }
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun ProfileField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector? = null,
    minLines: Int = 1,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = icon?.let { { Icon(it, null, tint = Terracotta.copy(alpha = 0.7f)) } },
        singleLine = minLines == 1,
        minLines = minLines,
        maxLines = maxLines,
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
}
