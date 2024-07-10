package com.beyzaparlak.trendsapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.beyzaparlak.trendsapp.configs.LoginRequest
import com.beyzaparlak.trendsapp.configs.RetrofitClient
import com.beyzaparlak.trendsapp.models.User
import com.beyzaparlak.trendsapp.ui.theme.TrendsAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrendsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// composable
@Composable
fun LoginContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
        Text(
            text = "Welcome Back!",
            fontSize = 35.sp,
            color = Color(0xFF004D40)
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            textStyle = TextStyle(color = Color(0xFF004D40)),
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(25.dp))
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            textStyle = TextStyle(color = Color(0xFF004D40)),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
            login(context, username.value.text, password.value.text)
        },
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004D40))
        ) {
            Text("Login")
        }
    }
}

// Tokenı saklama islemi
fun saveToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("jwt_token", token)
    editor.apply()
}
// sharedPreferences ile kullanıcı bilgilerini kaydediyorum
fun saveUserInfo(context: Context, user: User) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("user_id", user.id)
    editor.putString("firstName", user.firstName)
    editor.putString("lastName", user.lastName)
    editor.putString("gender", user.gender)
    editor.putString("username", user.username)
    editor.putString("password", user.password)
    editor.apply()
}

// userId bilgisini almak için api istegi
fun fetchUserInfo(context: Context, userId: Int) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitClient.instance.getUser(userId)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    saveUserInfo(context, user)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error fetching user info: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

// kullanıcı id kaydı
fun saveUserId(context: Context, userId: Int) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("user_id", userId)
    editor.apply()
}

// login islemlerinin kontrolu
fun login(context: Context, username: String, password: String) {
    val loginRequest = LoginRequest(username, password)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitClient.instance.login(loginRequest)
            if (response.isSuccessful) {
                val token = response.body()?.token
                val userId = response.body()?.id
                withContext(Dispatchers.Main) {
                    if (token != null && userId != null) {
                        saveToken(context, token)
                        saveUserId(context, userId)
                        fetchUserInfo(context, userId)
                    }
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, DetailsActivity::class.java)
                    context.startActivity(intent)
                    if (context is Activity) {
                        context.finish()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}



