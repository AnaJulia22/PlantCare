package com.example.plantcare.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.plantcare.ui.states.SignUpUiState
import com.example.plantcare.ui.theme.PlantCareTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(visible = uiState.error != null) {
        uiState.error?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(8.dp)
            ) {
                val error = uiState.error ?: ""
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Box(
        modifier = modifier.fillMaxSize().background(Color(0x339DC384)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier
                .fillMaxWidth(0.9f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Sign Up",
                Modifier
                    .padding(8.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Column(
                Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = uiState.onEmailChange,
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "ícone de usuário"
                        )
                    },
                    label = {
                        Text(text = "Email")
                    }
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = uiState.onPasswordChange,
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Password,
                            contentDescription = "ícone de senha"
                        )
                    },
                    label = {
                        Text(text = "Password")
                    },
                    trailingIcon = {
                        val trailingIconModifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                uiState.onTogglePasswordVisibility()
                            }
                        when (uiState.isShowPassword) {
                            true -> {
                                Icon(
                                    Icons.Filled.Visibility,
                                    contentDescription = "ícone de visível",
                                    trailingIconModifier
                                )
                            }

                            else -> Icon(
                                Icons.Filled.VisibilityOff,
                                contentDescription = "ícone de não visível",
                                trailingIconModifier
                            )
                        }
                    },
                    visualTransformation = when (uiState.isShowPassword) {
                        false -> PasswordVisualTransformation()
                        true -> VisualTransformation.None
                    }
                )
                OutlinedTextField(
                    value = uiState.confirmPassword,
                    onValueChange = uiState.onConfirmPasswordChange,
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Password,
                            contentDescription = "ícone de senha"
                        )
                    },
                    label = {
                        Text(text = "Confirm Password")
                    },
                    trailingIcon = {
                        val trailingIconModifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                uiState.onTogglePasswordVisibility()
                            }
                        when (uiState.isShowPassword) {
                            true -> {
                                Icon(
                                    Icons.Filled.Visibility,
                                    contentDescription = "ícone de visível",
                                    trailingIconModifier
                                )
                            }

                            else -> Icon(
                                Icons.Filled.VisibilityOff,
                                contentDescription = "ícone de não visível",
                                trailingIconModifier
                            )
                        }
                    },
                    visualTransformation = when (uiState.isShowPassword) {
                        false -> PasswordVisualTransformation()
                        true -> VisualTransformation.None
                    }
                )
                Button(
                    onClick = onSignUpClick,
                    Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9DC384))
                ) {
                    Text(text = "Sign Up", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                }

                TextButton(
                    onClick = onSignInClick,
                    Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = TextDecoration.Underline,
                            color = Color.Black
                        )
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Default")
@Composable
fun SignUpScreenPreview() {
    PlantCareTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = {},
            onSignInClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With error")
@Composable
fun SignUpScreen1Preview() {
    PlantCareTheme {
        SignUpScreen(
            uiState = SignUpUiState(
                error = "Error"
            ),
            onSignUpClick = {},
            onSignInClick = {}
        )
    }
}