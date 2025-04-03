package com.example.guess.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import com.example.guess.utils.toBitmap
import io.getstream.sketchbook.rememberSketchbookController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCompose(viewModel: GameViewModel) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    GameBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            ChatWindow(viewModel)
        }
    ) {
        GameDrawing(viewModel = viewModel)
    }
}

@Composable
fun GameDrawing(viewModel: GameViewModel) {
    val isHost by viewModel.isHost.collectAsState()
    if (isHost) {
        GameDrawingHost(viewModel)
    } else {
        GameDrawingNormal(viewModel)
    }
}

@Composable
fun GameDrawingHost(viewModel: GameViewModel) {
    val randomWords by viewModel.randomWords.collectAsState()
    val selectedWord by viewModel.selectedWord.collectAsState()
    if (randomWords != null && selectedWord == null) {
        WordSelectionDialog(randomWords!!) { word ->
            viewModel.setSelectedWord(word)
        }
    } else {
        val sketchbookController = rememberSketchbookController()
        sketchbookController.setPaintStrokeWidth(10f)
        sketchbookController.setPaintColor(Color.Black)
        SketchookCompose(sketchbookController) { viewModel.broadcastBitmap(it) }
    }
}

@Composable
fun GameDrawingNormal(viewModel: GameViewModel) {
    val drawingImage = viewModel.newDrawingImage.value
    Box(modifier = Modifier.fillMaxSize()) {
        drawingImage?.toBitmap()?.asImageBitmap()?.let {
            Image(bitmap = it, contentDescription = "")
        }
    }
}