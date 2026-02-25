package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {

    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "🍋 Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->

        // Gradient Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                    )
                )
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp
            ) {

                when (currentStep) {

                    1 -> LemonContent(
                        R.string.lemon_select,
                        R.drawable.lemon_tree,
                        R.string.lemon_tree_content_description
                    ) {
                        currentStep = 2
                        squeezeCount = (2..4).random()
                    }

                    2 -> LemonContent(
                        R.string.lemon_squeeze,
                        R.drawable.lemon_squeeze,
                        R.string.lemon_content_description
                    ) {
                        squeezeCount--
                        if (squeezeCount == 0) currentStep = 3
                    }

                    3 -> LemonContent(
                        R.string.lemon_drink,
                        R.drawable.lemon_drink,
                        R.string.lemonade_content_description
                    ) { currentStep = 4 }

                    4 -> LemonContent(
                        R.string.lemon_empty_glass,
                        R.drawable.lemon_restart,
                        R.string.empty_glass_content_description
                    ) { currentStep = 1 }
                }
            }
        }
    }
}

@Composable
fun LemonContent(
    textId: Int,
    imageId: Int,
    contentDescId: Int,
    onClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {

            Image(
                painter = painterResource(imageId),
                contentDescription = stringResource(contentDescId),
                modifier = Modifier
                    .width(dimensionResource(R.dimen.button_image_width))
                    .height(dimensionResource(R.dimen.button_image_height))
                    .padding(dimensionResource(R.dimen.button_interior_padding))
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))

        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonPreview() {
    MaterialTheme {
        LemonadeApp()
    }
}