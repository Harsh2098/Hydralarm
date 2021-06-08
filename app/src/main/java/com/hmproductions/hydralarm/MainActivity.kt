package com.hmproductions.hydralarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.hmproductions.hydralarm.data.HydralarmViewModel
import com.hmproductions.hydralarm.data.HydralarmViewModelFactory
import com.hmproductions.hydralarm.data.dataStore
import com.hmproductions.hydralarm.ui.components.MainScreen

class MainActivity : ComponentActivity() {

    private lateinit var model: HydralarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = ViewModelProvider(this, HydralarmViewModelFactory(dataStore)).get(HydralarmViewModel::class.java)

        setContent {
            MainScreen()
        }
    }
}
