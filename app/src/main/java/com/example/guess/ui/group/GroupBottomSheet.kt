package com.example.guess.ui.group

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun GroupBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    expand: Boolean,
    sheetContent: @Composable () -> Unit,
    mainContent: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            sheetContent()
        },
        sheetPeekHeight = 12.dp
    ) {
        mainContent()
    }

    if(expand && bottomSheetScaffoldState.bottomSheetState.hasExpandedState){
        LaunchedEffect(bottomSheetScaffoldState.bottomSheetState){
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
}