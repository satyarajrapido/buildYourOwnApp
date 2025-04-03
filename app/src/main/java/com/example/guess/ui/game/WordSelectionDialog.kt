package com.example.guess.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.guess.R

@Composable
fun WordSelectionDialog(
    words: List<String>,
    wordSelected: (selection: String) -> Unit
){
    Dialog(onDismissRequest = {}) {
        WordSelectionDialogCompose(words = words, wordSelected = wordSelected)
    }
}

@Composable
private fun WordSelectionDialogCompose(
    words: List<String>,
    wordSelected: (selection: String) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(text = stringResource(id = R.string.select_a_word))
            Column(modifier = Modifier.padding(top = 18.dp)) {
                words.forEachIndexed {index, it ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                wordSelected.invoke(it)
                            }
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    selectedIndex = index
                                    wordSelected.invoke(it)
                                },
                            text = it,
                            )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewWordSelectionDialogCompose(){
    WordSelectionDialogCompose(listOf("Goku", "Vegeta", "Broly")) {}
}