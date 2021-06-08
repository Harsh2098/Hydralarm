package com.hmproductions.hydralarm.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hmproductions.hydralarm.R
import com.hmproductions.hydralarm.data.HydralarmViewModel
import com.hmproductions.hydralarm.data.Interval
import com.hmproductions.hydralarm.ui.theme.HydralarmTheme

@Composable
fun MainScreen(viewModel: HydralarmViewModel = viewModel()) {

    HydralarmTheme {
        val intervals: List<Interval> by viewModel.intervals.observeAsState(listOf())

        Column {
            Spacer(modifier = Modifier.height(36.dp))
            IntervalOptions(intervals, viewModel::onIntervalClick)
        }
    }
}

@Composable
fun IntervalOptions(
    intervals: List<Interval>, onIntervalClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        intervals.forEach {
            IntervalCard(it, onIntervalClick)
        }
    }
}

@Composable
fun IntervalCard(interval: Interval, onIntervalClick: (Int) -> Unit) {
    Card(
        backgroundColor = if (interval.selected) Color.Green else Color.White,
        border = BorderStroke(1.dp, HydralarmTheme.colors.cardBorderColor),
        modifier = Modifier.clip(MaterialTheme.shapes.medium),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { onIntervalClick(interval.minute) }
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = interval.minute.toString(), style = MaterialTheme.typography.body1,
                color = HydralarmTheme.colors.neutralFontColor
            )
            Text(
                text = stringResource(id = R.string.short_minutes),
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}
