package com.example.spotlightfeature

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotlightfeature.ui.theme.SpotLightFeatureTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotLightFeatureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleSpotLightScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleSpotLightScreen() {
    var targetRect by remember { mutableStateOf<Rect?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text("SampleSpotLightScreen")
            Spacer(modifier = Modifier.height(30.dp))
            Text("SampleSpotLightScreen")
            Text("SampleSpotLightScreen")
            Text("SampleSpotLightScreen")
            SampleItem(
                modifier = Modifier.onGloballyPositioned {
                    println("onGloballyPositioned")
                    targetRect = it.boundsInRoot()
                }
            )
        }
        targetRect?.let {
            println("drawRect")
            SpotLight(rect = it)
        }
    }
}

@Composable
fun SampleItem(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.twitter_logo),
                contentDescription = "Twitter Logo",
            )
            Text("The bird was dead", style = TextStyle(color = Color.Red))
        }
    }
}

@Composable
fun SpotLight(rect: Rect) {
    val context = LocalContext.current
    Canvas(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    if(rect.contains(it)) {
                        Toast.makeText(context, "tapped spot light area", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    ) {
        val spotlightPath = Path().apply {
            addRect(rect)
        }
        clipPath(
            path = spotlightPath,
            clipOp = ClipOp.Difference
        ) {
            drawRect(color = Color.Gray.copy(alpha = 0.8f))
        }
    }
}