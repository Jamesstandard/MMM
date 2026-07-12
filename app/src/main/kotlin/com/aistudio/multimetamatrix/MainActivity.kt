package com.aistudio.multimetamatrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aistudio.multimetamatrix.ui.theme.MultiMetaMatrixTheme

import androidx.room.Room
import com.aistudio.multimetamatrix.data.AppDatabase

class MainActivity : ComponentActivity() {
    lateinit var database: AppDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mmm-database"
        ).fallbackToDestructiveMigration().build()
        
        enableEdgeToEdge()
        setContent {
            MultiMetaMatrixTheme {
                AppScaffold(database)
            }
        }
    }
}
