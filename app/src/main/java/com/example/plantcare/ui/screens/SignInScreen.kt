package com.example.plantcare.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantcare.R
import com.example.plantcare.ui.states.SignInUiState
import com.example.plantcare.ui.theme.PlantCareTheme

@Composable
fun SignInScreen(
    uiState: SignInUiState,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignInWithGoogleClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    val isError = uiState.error != null
    AnimatedVisibility(visible = isError) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
        ) {
            val error = uiState.error ?: ""
            Text(
                text = error,
                Modifier
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.onError
            )
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0x339DC384)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.potted_plant),
            contentDescription = "Ícone minhas Plantas",
            Modifier
                .clip(CircleShape)
                .size(124.dp)
                .background(Color(0xFF9DC384), CircleShape)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "Plant Care", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.size(16.dp))
        val textFieldModifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
        OutlinedTextField(
            value = uiState.email,
            onValueChange = uiState.onEmailChange,
            textFieldModifier,
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
            textFieldModifier,
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    Icons.Filled.Password,
                    contentDescription = "ícone de senha"
                )
            },
            label = {
                Text("Password")
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
            onClick = onSignInClick,
            Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9DC384))
        ) {
            Text(text = "Sign In", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        }
        OutlinedButton(
            onClick = onSignInWithGoogleClick,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google logo",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Entrar com Google")
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = onSignUpClick,
            Modifier
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    PlantCareTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onSignInClick = {},
            onSignInWithGoogleClick = {},
            onSignUpClick = {}
        )
    }
}