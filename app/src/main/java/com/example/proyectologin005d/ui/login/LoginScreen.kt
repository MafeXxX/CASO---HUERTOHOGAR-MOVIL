// app/src/main/java/com/example/proyectologin005d/ui/login/LoginScreen.kt
package com.example.proyectologin005d.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.SessionManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = viewModel(),
    // Usa tu paleta: fija de marca o tomada del MaterialTheme
    colors: LoginColors = LoginDefaults.brand()
    // Si prefieres el theme del sistema, usa: colors = LoginDefaults.fromTheme()
) {
    val ui = viewModel.uiState
    var passwordVisible by remember { mutableStateOf(false) }

    // Necesitamos contexto y scope para guardar el email (suspend)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize(), color = colors.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_huerto),
                contentDescription = "Logo Huerto Hogar",
                modifier = Modifier.size(96.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                "Bienvenido a Huerto Hogar",
                style = MaterialTheme.typography.headlineMedium,
                color = colors.title
            )

            Spacer(Modifier.height(24.dp))

            // Correo
            OutlinedTextField(
                value = ui.username,
                onValueChange = { viewModel.onUsernameChange(it) },
                label = { Text("Correo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !ui.isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colors.fieldText,
                    unfocusedTextColor = colors.fieldText,
                    focusedLabelColor = colors.fieldLabel,
                    unfocusedLabelColor = colors.fieldLabel,
                    focusedBorderColor = colors.fieldBorder,
                    unfocusedBorderColor = colors.fieldBorder,
                    cursorColor = colors.fieldText,
                    focusedContainerColor = colors.fieldContainer,
                    unfocusedContainerColor = colors.fieldContainer,
                    errorBorderColor = colors.error,
                    errorLabelColor = colors.error
                )
            )

            Spacer(Modifier.height(12.dp))

            // Contraseña
            OutlinedTextField(
                value = ui.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(if (passwordVisible) "Ocultar" else "Ver")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !ui.isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colors.fieldText,
                    unfocusedTextColor = colors.fieldText,
                    focusedLabelColor = colors.fieldLabel,
                    unfocusedLabelColor = colors.fieldLabel,
                    focusedBorderColor = colors.fieldBorder,
                    unfocusedBorderColor = colors.fieldBorder,
                    cursorColor = colors.fieldText,
                    focusedContainerColor = colors.fieldContainer,
                    unfocusedContainerColor = colors.fieldContainer,
                    errorBorderColor = colors.error,
                    errorLabelColor = colors.error
                )
            )

            if (!ui.error.isNullOrBlank()) {
                Spacer(Modifier.height(10.dp))
                Text(ui.error ?: "", color = colors.error, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    // Usamos la versión de submit que entrega el username (correo).
                    viewModel.submit { email ->
                        scope.launch {
                            // Guarda el correo en DataStore para que Profile lo lea
                            SessionManager(context).setEmail(email)
                            // Delega la navegación al contenedor (MainActivity/NavHost)
                            onLoginSuccess()
                        }
                    }
                },
                enabled = !ui.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.buttonContainer,
                    contentColor = colors.buttonContent,
                    disabledContainerColor = colors.buttonContainer.copy(alpha = 0.4f),
                    disabledContentColor = colors.buttonContent.copy(alpha = 0.8f)
                )
            ) {
                Text(if (ui.isLoading) "Ingresando..." else "Ingresar")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
