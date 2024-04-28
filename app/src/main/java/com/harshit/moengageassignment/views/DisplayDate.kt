package com.harshit.moengageassignment.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
/**
 * Composable function to display the current date.
 */
@Composable
fun DisplayDate() {
    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("EEEE, MMMM dd")
    }
    val currentDate = LocalDate.now()
    val formattedDate = currentDate.format(dateFormatter)
    // Display the formatted date using the Text composable
    Text(
        text = formattedDate,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 0.dp)
    )
}

