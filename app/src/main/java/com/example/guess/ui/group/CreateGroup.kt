package com.example.guess.ui.group

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.guess.data.GameConnectionState
import com.example.guess.ui.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroup(mainViewModel: MainViewModel) {
    val gameStatus by mainViewModel.gameConnectionState.collectAsState()
    val channel by mainViewModel.connectedChannel.collectAsState(initial = null)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    GroupBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        expand = gameStatus is GameConnectionState.Success,
        sheetContent = { GroupBottomSheetContent(channel) },
        mainContent = { CreateGroupCompose(viewModel = mainViewModel) }
    )
}