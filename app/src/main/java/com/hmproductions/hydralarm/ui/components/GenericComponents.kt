package com.hmproductions.hydralarm.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hmproductions.hydralarm.R

@Composable
fun HydralarmTopBar() {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary) {
        Text(text = stringResource(id = R.string.app_name), color = Color.White, modifier = Modifier.padding(8.dp))
    }
}