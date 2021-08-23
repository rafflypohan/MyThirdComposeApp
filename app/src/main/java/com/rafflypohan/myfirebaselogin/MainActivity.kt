package com.rafflypohan.myfirebaselogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.rafflypohan.myfirebaselogin.MainActivity.Companion.EMAIL_ID
import com.rafflypohan.myfirebaselogin.MainActivity.Companion.USER_ID
import com.rafflypohan.myfirebaselogin.login.LoginActivity
import com.rafflypohan.myfirebaselogin.ui.theme.MyFirebaseLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra(USER_ID)
        val emailId = intent.getStringExtra(EMAIL_ID)
        setContent {
            MyFirebaseLoginTheme {
                MainScreen(userId = userId!!, emailId = emailId!!)
            }
        }
    }

    companion object {
        const val USER_ID = "user_id"
        const val EMAIL_ID = "email_id"
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, userId: String, emailId: String) {
    val context = LocalContext.current
    val activity = context as Activity
    Column(
        modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You're logged in successfully")
        Spacer(modifier = modifier.height(8.dp))
        Text(text = "Email: $emailId")
        Spacer(modifier = modifier.height(8.dp))
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                context.startActivity(Intent(context, LoginActivity::class.java))
                activity.finish()
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
            Text(text = "Logout")
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MyFirebaseLoginTheme {
        MainScreen(userId = "1", emailId = "1")
    }
}

