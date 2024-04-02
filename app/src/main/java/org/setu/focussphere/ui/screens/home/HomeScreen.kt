package org.setu.focussphere.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.setu.focussphere.util.UiEvent

@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    modifier: Modifier = Modifier
) {

    val message = remember {
        arrayOf(
            "Focus breeds success. Embrace it.",
            "Today, I choose productivity. I am unstoppable.",
            "Progress over perfection. Every step counts.",
            "I am in control. I prioritize with purpose.",
            "With clarity comes achievement. I stay focused.",
            "Believe. Achieve. Repeat.",
            "My focus shapes my future. I choose wisely.",
            "Commitment fuels progress. I am committed.",
            "In every moment, I move closer to my goals. I persist.",
        ).random()
    }
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        style = MaterialTheme.typography.bodyLarge.copy(
            textAlign = TextAlign.Center
        ),
        text = "Welcome to FocusSphere"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = message,
            color = Color.DarkGray,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight(500),
                lineHeight = 50.sp,
                textAlign = TextAlign.Center
            )
        )
    }

}