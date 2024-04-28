package com.harshit.moengageassignment.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harshit.moengageassignment.R

/**
 * Composable function to display the heading with sort icons.
 *
 * @param sortNewsList A function to sort the news list based on ascending or descending order.
 */
@Composable
fun DisplayHeading(sortNewsList: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.heading),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Row containing sort icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            // Ascending sort icon
            Icon(
                painter = painterResource(id = R.drawable.ascending),
                contentDescription = stringResource(id = R.string.ascending),
                modifier = Modifier
                    .size(45.dp)
                    .padding(4.dp)
                    .clickable {
                        sortNewsList(true)
                    }
            )
            // Descending sort icon
            Icon(
                painter = painterResource(id = R.drawable.descending),
                contentDescription = stringResource(id = R.string.descending),
                modifier = Modifier
                    .size(45.dp)
                    .padding(4.dp)
                    .clickable {
                        sortNewsList(false)
                    }
            )
        }
    }
}
