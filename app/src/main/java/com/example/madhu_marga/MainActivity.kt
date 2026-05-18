package com.example.madhu_marga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.madhu_marga.ui.navigation.NavGraph
import com.example.madhu_marga.ui.theme.MadhuMargaTheme
import com.example.madhu_marga.ui.viewmodel.AiViewModel
import com.example.madhu_marga.ui.viewmodel.AiViewModelFactory
import com.example.madhu_marga.ui.viewmodel.HiveViewModel
import com.example.madhu_marga.ui.viewmodel.HiveViewModelFactory

class MainActivity : ComponentActivity() {
    private val hiveViewModel: HiveViewModel by viewModels {
        HiveViewModelFactory((application as MadhuMargaApplication).repository)
    }
    
    private val aiViewModel: AiViewModel by viewModels {
        AiViewModelFactory((application as MadhuMargaApplication).aiRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MadhuMargaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        hiveViewModel = hiveViewModel,
                        aiViewModel = aiViewModel
                    )
                }
            }
        }
    }
}
