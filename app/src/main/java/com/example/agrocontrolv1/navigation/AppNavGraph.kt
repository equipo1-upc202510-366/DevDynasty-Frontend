package com.example.agrocontrolv1.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.agrocontrolv1.presentation.home.HomeScreen
import com.example.agrocontrolv1.agricultural_process.presentation.fumigation_list.FumigationListScreen
import com.example.agrocontrolv1.agricultural_process.presentation.irrigation_edit.IrrigationViewScreen
import com.example.agrocontrolv1.agricultural_process.presentation.irrigation_list.IrrigationListScreen
import com.example.agrocontrolv1.common.Constants
import com.example.agrocontrolv1.data.local.AppDatabase
import com.example.agrocontrolv1.data.remote.ProductService
import com.example.agrocontrolv1.data.repository.ProductRepository
import com.example.agrocontrolv1.finances.presentation.FinancesListScreen
import com.example.agrocontrolv1.harvest.presentation.HarvestListScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListViewModel
import com.example.agrocontrolv1.workers.presentation.add_worker.AddWorkerScreen
import com.example.agrocontrolv1.workers.presentation.worker_list.WorkerListScreen
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
            IrrigationListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { entry ->
                    navController.navigate("irrigation_edit/${entry.date}/${entry.hours}")
                },
                navigateToNew = { navController.navigate(NavRoutes.IRRIGATION_NEW) }
            )
        }
        composable(NavRoutes.WORKERS) {
            WorkerListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToAdd = { navController.navigate(NavRoutes.ADD_WORKER) }
            )
        }
        composable(NavRoutes.ADD_WORKER) {
            AddWorkerScreen(navigateBack = { navController.popBackStack() })
        }

        composable(NavRoutes.IRRIGATION_NEW) {
            IrrigationViewScreen(
                navigateBack = { navController.popBackStack() },
                isEditing = false
            )
        }
        composable(NavRoutes.HARVEST) {
            HarvestListScreen(
                navigateBack = { navController.popBackStack() },
                onAddHarvest = {  }
            )
        }
        composable(NavRoutes.FINANCES) {
            FinancesListScreen(navigateBack = { navController.popBackStack() })
        }
        composable(
            route = "irrigation_edit/{date}/{hours}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("hours") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val hours = backStackEntry.arguments?.getInt("hours") ?: 0

            IrrigationViewScreen(
                navigateBack = { navController.popBackStack() },
                isEditing = true,
                initialDate = date,
                initialHours = hours
            )
        }
    }
}