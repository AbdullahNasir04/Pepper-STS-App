package com.example.apiintigration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.conversation.Chat
import com.aldebaran.qi.sdk.builder.ChatBuilder
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder
import com.aldebaran.qi.sdk.builder.TopicBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.example.apiintigration.ui.theme.APIIntigrationTheme


class MainActivity : RobotActivity(), RobotLifecycleCallbacks {
    private lateinit var qiContext: QiContext
    private lateinit var chat: Chat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QiSDK.register(this, this)
        setContent {
            APIIntigrationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PepperSpeechApp()
                }
            }
        }
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        this.qiContext = qiContext
        val topic = TopicBuilder.with(qiContext)
            .withResource(R.raw.dialog)
            .build()
        val qiChatbot = QiChatbotBuilder.with(qiContext)
            .withTopic(topic)
            .build()
        chat = ChatBuilder.with(qiContext)
            .withChatbot(qiChatbot)
            .build()
        chat.async().run()
    }

    override fun onRobotFocusLost() {
        if (::chat.isInitialized) {
            chat.removeAllOnStartedListeners()
        }
    }

    override fun onRobotFocusRefused(reason: String) {
        // Handle focus refusal
    }
}
@Composable
fun PepperSpeechApp() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text("Questions that you can ask pepper", fontSize = 30.sp, color = Color.Gray) }
        item { Text("hello, hi, hey", fontSize = 25.sp, color = Color.Black) }
        item { Text("how are you, how are you doing", fontSize = 25.sp, color = Color.Black) }
        item { Text("what's your name, who are you", fontSize = 25.sp, color = Color.Black) }
        item { Text("nice to meet you, pleased to meet you", fontSize = 25.sp, color = Color.Black) }
        item { Text("how old are you, what's your age", fontSize = 25.sp, color = Color.Black) }
        item { Text("what can you do, what are your capabilities", fontSize = 25.sp, color = Color.Black) }
        item { Text("where are you from, who made you", fontSize = 25.sp, color = Color.Black) }
        item { Text("do you like humans, what do you think of people", fontSize = 25.sp, color = Color.Black) }
        item { Text("are you smart, how intelligent are you", fontSize = 25.sp, color = Color.Black) }
        item { Text("do you have feelings, can you feel emotions", fontSize = 25.sp, color = Color.Black) }
        item { Text("what's the weather like, how's the weather", fontSize = 25.sp, color = Color.Black) }
        item { Text("tell me a joke, know any jokes", fontSize = 25.sp, color = Color.Black) }
        item { Text("goodbye, bye, see you later", fontSize = 25.sp, color = Color.Black) }
    }
}
