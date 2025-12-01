package com.example.proyectologin005d.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.proyectologin005d.R
import com.example.proyectologin005d.data.SessionManager
import com.example.proyectologin005d.data.UserRepository
import com.example.proyectologin005d.ui.login.LoginColors
import com.example.proyectologin005d.ui.login.LoginDefaults
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit,
    colors: LoginColors? = null
) {
    val palette = colors ?: LoginDefaults.fromTheme()

    val context = LocalContext.current
    val repo = remember { UserRepository(context) }
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passError by remember { mutableStateOf<String?>(null) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showPass by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Surface(Modifier.fillMaxSize(), color = palette.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val logoPainter =
                runCatching { painterResource(id = R.drawable.logo_huerto) }.getOrNull()
                    ?: runCatching { painterResource(id = R.mipmap.ic_launcher_round) }.getOrNull()
                    ?: runCatching { painterResource(id = R.mipmap.ic_launcher) }.getOrNull()

            if (logoPainter != null) {
                Image(
                    painter = logoPainter,
                    contentDescription = "Logo Huerto Hogar",
                    modifier = Modifier
                        .size(96.dp)
                        .padding(bottom = 12.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                "Bienvenido a Huerto Hogar",
                style = MaterialTheme.typography.headlineMedium,
                color = palette.title
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                    generalError = null
                },
                label = { Text("Correo", color = palette.fieldLabel) },
                isError = emailError != null,
                supportingText = {
                    if (emailError != null)
                        Text(emailError!!, color = palette.error)
                },
                singleLine = true,
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = palette.fieldContainer,
                    unfocusedContainerColor = palette.fieldContainer,
                    focusedTextColor = palette.fieldText,
                    unfocusedTextColor = palette.fieldText,
                    focusedBorderColor = palette.fieldBorder,
                    unfocusedBorderColor = palette.fieldBorder,
                    cursorColor = palette.fieldText
                )
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = pass,
                onValueChange = {
                    pass = it
                    passError = null
                    generalError = null
                },
                label = { Text("Contraseña", color = palette.fieldLabel) },
                isError = passError != null,
                supportingText = {
                    if (passError != null)
                        Text(passError!!, color = palette.error)
                },
                singleLine = true,
                visualTransformation = if (showPass)
                    androidx.compose.ui.text.input.VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPass = !showPass }) {
                        Text(if (showPass) "Ocultar" else "Ver")
                    }
                },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = palette.fieldContainer,
                    unfocusedContainerColor = palette.fieldContainer,
                    focusedTextColor = palette.fieldText,
                    unfocusedTextColor = palette.fieldText,
                    focusedBorderColor = palette.fieldBorder,
                    unfocusedBorderColor = palette.fieldBorder,
                    cursorColor = palette.fieldText
                )
            )

            if (generalError != null) {
                Spacer(Modifier.height(8.dp))
                Text(generalError!!, color = palette.error)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val emailOk =
                        android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
                    val passOk = pass.length >= 8
                    emailError = if (!emailOk) "Correo inválido" else null
                    passError = if (!passOk) "Mínimo 8 caracteres" else null

                    if (emailOk && passOk) {
                        isLoading = true
                        scope.launch {
                            val correo = email.trim()
                            val desdeRegistro = repo.login(correo, pass)

                            val predefinidos =
                                (correo == "cliente@huerto.cl" && pass == "Huerto2025!") ||
                                        (correo == "admin@huerto.cl" && pass == "Admin2025!")

                            val ok = desdeRegistro || predefinidos

                            isLoading = false
                            if (ok) {
                                // Guardamos correo de usuario logueado
                                sessionManager.setEmail(correo)
                                onLoginSuccess()
                            } else {
                                generalError = "Credenciales inválidas"
                            }
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.buttonContainer,
                    contentColor = palette.buttonContent
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        Modifier.size(20.dp),
                        color = palette.buttonContent,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Ingresar")
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = { onGoToRegister() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Crear cuenta")
            }
        }
    }
}
