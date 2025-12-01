package com.example.proyectologin005d.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectologin005d.ui.login.LoginDefaults

@Composable
fun RegisterScreen(
    onBackToLogin: () -> Unit,
    vm: RegisterViewModel = viewModel()
) {
    val state = vm.state
    val palette = LoginDefaults.fromTheme()
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = palette.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = palette.title
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { vm.onNameChange(it) },
                label = { Text("Nombre completo", color = palette.fieldLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
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
                value = state.email,
                onValueChange = { vm.onEmailChange(it) },
                label = { Text("Correo electrónico", color = palette.fieldLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
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
                value = state.password,
                onValueChange = { vm.onPasswordChange(it) },
                label = { Text("Contraseña", color = palette.fieldLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
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
                value = state.confirmPassword,
                onValueChange = { vm.onConfirmChange(it) },
                label = { Text("Confirmar contraseña", color = palette.fieldLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
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

            if (state.error != null) {
                Spacer(Modifier.height(8.dp))
                Text(state.error!!, color = palette.error)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { vm.register() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.buttonContainer,
                    contentColor = palette.buttonContent
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = palette.buttonContent,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Registrarse")
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = onBackToLogin) {
                Text("Volver al inicio de sesión", color = palette.title)
            }

            if (state.success) {
                AlertDialog(
                    onDismissRequest = onBackToLogin,
                    confirmButton = {
                        TextButton(onClick = onBackToLogin) {
                            Text("OK")
                        }
                    },
                    title = { Text("Registro exitoso") },
                    text = { Text("Ya puedes iniciar sesión con tu cuenta") }
                )
            }
        }
    }
}
