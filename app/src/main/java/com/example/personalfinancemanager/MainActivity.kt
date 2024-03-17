package com.example.personalfinancemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.personalfinancemanager.database.AppDatabase
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.routing.RootContent
import com.example.personalfinancemanager.routing.RootRouter
import com.example.personalfinancemanager.ui.theme.PersonalFinanceManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy {
            AppDatabase.getInstance(this)
        }
        val appRepository = AppRepository(database.getAppDao())

        val rootRouter = RootRouter(
            componentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory(),
            database = appRepository
        )
        setContent {
            PersonalFinanceManagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootContent(router = rootRouter)
                }
            }
        }
    }
}
