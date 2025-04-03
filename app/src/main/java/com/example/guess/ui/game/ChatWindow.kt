package com.example.guess.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guess.R
import com.example.guess.ui.components.AppTextField

@Composable
fun ChatWindow(
    viewModel: GameViewModel
) {
    val isHost by viewModel.isHost.collectAsState()
    val gameChatList by viewModel.gameChatMessages.collectAsState()
    var guess by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(gameChatList) { item ->
                Text(text = "${item.user}: ${item.message}", fontSize = 18.sp)
            }
        }

        if (!isHost) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    modifier = Modifier.weight(2f),
                    label = stringResource(id = R.string.enter_guess),
                    value = guess,
                    onValueChange = { guess = it },
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button (
                    modifier = Modifier
                        .size(52.dp)
                        .weight(1f),
                    onClick = {
                        viewModel.sendGuessToChannel(guess)
                        guess = ""
                    },
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChatWindow() {
    ChatWindow(viewModel())
}
