package com.example.plantcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.plantcare.ui.theme.PlantCareTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val googleAuthClient = GoogleAuthClient(applicationContext)

        setContent {
            PlantCareTheme {

                var isSignIn by rememberSaveable {
                    mutableStateOf(googleAuthClient.isSingedIn())
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFCCFFCC)),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plant_icon),
                            contentDescription = "Plant Icon",
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "PlantCare",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "The best app for your plants",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(32.dp))

                        /*Button(
                            onClick = { *//* TODO: Navigate to next screen *//* },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = "Get Started",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }*/
                        Spacer(modifier = Modifier.height(16.dp))

                        if (isSignIn) {

                            OutlinedButton(onClick = {
                                lifecycleScope.launch {
                                    googleAuthClient.signOut()
                                    isSignIn = false
                                }
                            }) {
                                Text(
                                    text = "Sign Out",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(
                                        horizontal = 24.dp, vertical = 4.dp
                                    )
                                )
                            }

                        } else {

                            OutlinedButton(onClick = {
                                lifecycleScope.launch {
                                    isSignIn = googleAuthClient.signIn()
                                }
                            }) {
                                Text(
                                    text = "Sign In With Google",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(
                                        horizontal = 24.dp, vertical = 4.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}