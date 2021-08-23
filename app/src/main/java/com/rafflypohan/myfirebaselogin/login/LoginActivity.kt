package com.rafflypohan.myfirebaselogin.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rafflypohan.myfirebaselogin.MainActivity
import com.rafflypohan.myfirebaselogin.register.RegisterActivity
import com.rafflypohan.myfirebaselogin.ui.theme.MyFirebaseLoginTheme

class LoginActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirebaseLoginTheme {
                val currentUser = auth.currentUser
                LoginScreen()
            }
        }
    }

    companion object {
        const val USER_ID = "user_id"
        const val EMAIL_ID = "email_id"
    }

}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(all = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = modifier.height(40.dp))
        Text(
            text = "Login",
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
            label = { Text(text = "Username") },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
        )


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


        Spacer(modifier = modifier.height(12.dp))

        Button(
            onClick = {
                when{
                    emailState.value.isEmpty() -> Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    passwordState.value.isEmpty() -> Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                    else -> {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailState.value, passwordState.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    Toast.makeText(context, "You are logged in successfully", Toast.LENGTH_SHORT).show()

                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    val intent = Intent(context, MainActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        putExtra(MainActivity.USER_ID, firebaseUser.uid)
                                        putExtra(MainActivity.EMAIL_ID, emailState.value)
                                    }
                                    context.startActivity(intent)
                                    val activity = context as Activity
                                    activity.finish()
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
            modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Login", fontSize = 18.sp)
        }
        
        Spacer(modifier = modifier.height(12.dp))

        Button(
            onClick = {
                      context.startActivity(Intent(context, RegisterActivity::class.java))
            },
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.onPrimary,
                contentColor = MaterialTheme.colors.primary
            ),
            modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
        ) {
            Text(text = "Register", fontSize = 18.sp)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MyFirebaseLoginTheme {
        LoginScreen()
    }
}


