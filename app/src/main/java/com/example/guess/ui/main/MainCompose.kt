package com.example.guess.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guess.ui.group.CreateGroup
import com.example.guess.ui.group.JoinGroup
import com.example.guess.ui.home.Home
import com.example.guess.ui.navigation.NAV_CREATE_GROUP
import com.example.guess.ui.navigation.NAV_HOME
import com.example.guess.ui.navigation.NAV_JOIN_GROUP

@Composable
fun MainCompose(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAV_HOME){
        composable(NAV_HOME){ Home(navController) }
        composable(NAV_CREATE_GROUP){ CreateGroup(hiltViewModel()) }
        composable(NAV_JOIN_GROUP){ JoinGroup(hiltViewModel()) }
    }

}