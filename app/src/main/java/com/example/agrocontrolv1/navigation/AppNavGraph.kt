package com.example.agrocontrolv1.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.room.Room
import com.example.agrocontrolv1.presentation.home.HomeScreen
import com.example.agrocontrolv1.agricultural_process.presentation.fumigation_list.FumigationListScreen
import com.example.agrocontrolv1.agricultural_process.presentation.irrigation_list.IrrigationListScreen
import com.example.agrocontrolv1.common.Constants
import com.example.agrocontrolv1.data.local.AppDatabase
import com.example.agrocontrolv1.data.remote.ProductService
import com.example.agrocontrolv1.data.repository.ProductRepository
import com.example.agrocontrolv1.presentation.product_list.ProductListScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.HOME) {
        composable(NavRoutes.HOME) {
            HomeScreen(navigateTo = { route -> navController.navigate(route) })
        }
        composable(NavRoutes.FUMIGATION) {
            FumigationListScreen()
        }
        composable(NavRoutes.PRODUCTS) {
            val context = LocalContext.current

            // Retrofit
            val service = remember {
                Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ProductService::class.java)
            }

            // Room
            val dao = remember {
                Room
                    .databaseBuilder(context, AppDatabase::class.java, "products-db")
                    .build()
                    .getProductDao()
            }

            // ViewModel
            val repository = remember { ProductRepository(service, dao) }
            val viewModel = remember { ProductListViewModel(repository) }

            // Mostrar pantalla
            ProductListScreen(viewModel = viewModel)
        }
        composable(NavRoutes.IRRIGATION) {
            IrrigationListScreen()
        }
    }
}