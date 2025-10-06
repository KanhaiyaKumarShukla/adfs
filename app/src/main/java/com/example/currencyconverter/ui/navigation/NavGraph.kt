package com.example.currencyconverter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.currencyconverter.ui.screen.deposit.DepositScreen
import com.example.currencyconverter.ui.screen.home.HomeScreen
import com.example.currencyconverter.ui.screen.home.HomeViewModel
import com.example.currencyconverter.ui.screen.onboarding.OnboardingScreen
import com.example.currencyconverter.ui.screen.splash.SplashScreen
import com.example.currencyconverter.ui.screen.transaction.TransactionDetailScreen
import com.example.currencyconverter.ui.screen.withdraw.WithdrawScreen
import com.example.currencyconverter.util.Constants.BASE_URL
import com.example.currencyconverter.util.Constants.DEPOSIT_SCREEN as NAV_DEPOSIT
import com.example.currencyconverter.util.Constants.HOME_SCREEN as NAV_HOME
import com.example.currencyconverter.util.Constants.ONBOARDING_SCREEN as NAV_ONBOARDING
import com.example.currencyconverter.util.Constants.SPLASH_SCREEN as NAV_SPLASH
import com.example.currencyconverter.util.Constants.WITHDRAW_SCREEN as NAV_WITHDRAW

// Define transaction details route
private const val NAV_TRANSACTION_DETAILS = "transaction_details"

@Composable
fun NavGraph(
    startDestination: String = NAV_SPLASH
) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(NAV_SPLASH) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(NAV_ONBOARDING) {
                        popUpTo(NAV_SPLASH) { inclusive = true }
                    }
                }
            )
        }
        
        // Onboarding Screen
        composable(NAV_ONBOARDING) {
            OnboardingScreen(
                onGetStarted = {
                    navController.navigate(NAV_HOME) {
                        popUpTo(NAV_ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        
        // Home Screen
        composable(NAV_HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            
            LaunchedEffect(Unit) {
                viewModel.loadInitialData()
            }
            
            HomeScreen(
                onDepositClick = { navController.navigate(NAV_DEPOSIT) },
                onWithdrawClick = { navController.navigate(NAV_WITHDRAW) },
                onTransactionClick = { transactionId ->
                    navController.navigate("$NAV_TRANSACTION_DETAILS/$transactionId")
                },
                viewModel = viewModel
            )
        }
        
        // Deposit Screen
        composable(NAV_DEPOSIT) {
            DepositScreen(
                onBackClick = { navController.popBackStack() },
                onDepositSuccess = { navController.popBackStack(NAV_HOME, false) }
            )
        }
        
        // Withdraw Screen
        composable(NAV_WITHDRAW) {
            WithdrawScreen(
                onBackClick = { navController.popBackStack() },
                onWithdrawSuccess = { navController.popBackStack(NAV_HOME, false) }
            )
        }
        
        // Transaction Detail Screen
        composable(
            route = "$NAV_TRANSACTION_DETAILS/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId") ?: return@composable
            
            TransactionDetailScreen(
                transactionId = transactionId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}