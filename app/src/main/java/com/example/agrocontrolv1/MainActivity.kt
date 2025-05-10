package com.example.agrocontrolv1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.agrocontrolv1.common.Constants
import com.example.agrocontrolv1.data.local.AppDatabase
import com.example.agrocontrolv1.data.remote.ProductService
import com.example.agrocontrolv1.data.repository.ProductRepository
import com.example.agrocontrolv1.presentation.product_list.ProductListScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListViewModel
import com.example.agrocontrolv1.ui.theme.AgroControlv1Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "products-db")
            .build()
            .getProductDao()

        val viewModel = ProductListViewModel(ProductRepository(service, dao))

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgroControlv1Theme {
                ProductListScreen( viewModel)
            }
        }
    }
}

