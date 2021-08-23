package com.rafflypohan.myfirebaselogin.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rafflypohan.myfirebaselogin.MainActivity
import com.rafflypohan.myfirebaselogin.login.LoginActivity
import com.rafflypohan.myfirebaselogin.ui.theme.MyFirebaseLoginTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirebaseLoginTheme {
                RegisterScreen()
            }
        }
    }
}

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(all = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = modifier.height(40.dp))
        Text(
            text = "Register",
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = modifier.height(80.dp))

        val emailState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text(text = "Email") },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        )

//        ErrorText(text = if (usernameState.value.isEmpty()) "Username cannot be empty" else "")

        Spacer(modifier = modifier.height(2.dp))

        val passwordState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text(text = "Password") },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = modifier.height(2.dp))

        val confirmPasswordState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = confirmPasswordState.value,
            onValueChange = { confirmPasswordState.value = it },
            label = { Text(text = "Confirm Password") },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = !confirmPassword(confirmPasswordState.value, passwordState.value)
        )

        Spacer(modifier = modifier.height(12.dp))

        Button(
            onClick = {
                when{
                    emailState.value.isEmpty() -> Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    passwordState.value.isEmpty() -> Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                    else -> {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailState.value, passwordState.value).addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                Toast.makeText(context, "You are successfully registered", Toast.LENGTH_SHORT).show()

                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ),
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Register", fontSize = 18.sp)
        }
    }
}

fun confirmPassword(confirmPassword: String, password: String): Boolean {
    return confirmPassword == password
}

@Composable
fun ErrorText(text: String){
    Text(text = text, fontSize = 12.sp, color = MaterialTheme.colors.error)
}


@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    MyFirebaseLoginTheme {
        RegisterScreen()
    }
}