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
import com.example.agrocontrolv1.agricultural_process.presentation.fumigation_edit.FumigationViewScreen
import com.example.agrocontrolv1.agricultural_process.presentation.fumigation_list.FumigationListScreen
import com.example.agrocontrolv1.agricultural_process.presentation.irrigation_edit.IrrigationViewScreen
import com.example.agrocontrolv1.agricultural_process.presentation.irrigation_list.IrrigationListScreen
import com.example.agrocontrolv1.common.Constants
import com.example.agrocontrolv1.data.local.AppDatabase
import com.example.agrocontrolv1.data.remote.ProductService
import com.example.agrocontrolv1.data.repository.ProductRepository
import com.example.agrocontrolv1.finances.presentation.FinancesListScreen
import com.example.agrocontrolv1.harvest.presentation.HarvestListScreen
import com.example.agrocontrolv1.presentation.home.HomeScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListScreen
import com.example.agrocontrolv1.presentation.product_list.ProductListViewModel
import com.example.agrocontrolv1.workers.presentation.add_worker.AddWorkerScreen
import com.example.agrocontrolv1.workers.presentation.worker_list.WorkerListScreen
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoutes.HOME) {

        // Pantalla principal
        composable(NavRoutes.HOME) {
            HomeScreen(navigateTo = { route -> navController.navigate(route) })
        }

        // Lista de riegos
        composable(NavRoutes.IRRIGATION) {
            IrrigationListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { entry ->
                    navController.navigate("irrigation_edit/${entry.date}/${entry.hours}")
                },
                navigateToNew = {
                    navController.navigate(NavRoutes.IRRIGATION_NEW)
                }
            )
        }

        // Nuevo riego
        composable(NavRoutes.IRRIGATION_NEW) {
            IrrigationViewScreen(
                navigateBack = { navController.popBackStack() },
                isEditing = false
            )
        }

        // Editar riego
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

        // Lista de fumigaciones
        composable(NavRoutes.FUMIGATION) {
            FumigationListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEdit = { entry ->
                    navController.navigate("fumigation_edit/${entry.date}/${entry.hours}")
                },
                navigateToNew = {
                    navController.navigate(NavRoutes.FUMIGATION_NEW)
                }
            )
        }

        // Nueva fumigación
        composable(NavRoutes.FUMIGATION_NEW) {
            FumigationViewScreen(
                navigateBack = { navController.popBackStack() },
                isEditing = false
            )
        }

        // Editar fumigación
        composable(
            route = "fumigation_edit/{date}/{hours}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("hours") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val hours = backStackEntry.arguments?.getInt("hours") ?: 0

            FumigationViewScreen(
                navigateBack = { navController.popBackStack() },
                isEditing = true,
                initialDate = date,
                initialHours = hours
            )
        }

        // Lista de productos
        composable(NavRoutes.PRODUCTS) {
            val context = LocalContext.current

            val service = remember {
                Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ProductService::class.java)
            }

            val dao = remember {
                Room.databaseBuilder(context, AppDatabase::class.java, "products-db")
                    .build()
                    .getProductDao()
            }

            val repository = remember { ProductRepository(service, dao) }
            val viewModel = remember { ProductListViewModel(repository) }

            ProductListScreen(viewModel = viewModel)
        }

        // Lista de trabajadores
        composable(NavRoutes.WORKERS) {
            WorkerListScreen(
                navigateBack = { navController.popBackStack() },
                navigateToAdd = { navController.navigate(NavRoutes.ADD_WORKER) }
            )
        }

        // Agregar trabajador
        composable(NavRoutes.ADD_WORKER) {
            AddWorkerScreen(navigateBack = { navController.popBackStack() })
        }

        // Lista de cosechas
        composable(NavRoutes.HARVEST) {
            HarvestListScreen(
                navigateBack = { navController.popBackStack() },
                onAddHarvest = { /* Implementar si es necesario */ }
            )
        }

        // Finanzas
        composable(NavRoutes.FINANCES) {
            FinancesListScreen(navigateBack = { navController.popBackStack() })
        }
    }
}
