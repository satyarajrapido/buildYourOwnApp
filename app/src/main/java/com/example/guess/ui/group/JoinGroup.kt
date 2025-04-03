package com.example.guess.ui.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guess.R
import com.example.guess.data.GameConnectionState
import com.example.guess.ui.main.MainViewModel
import com.example.guess.ui.components.TextInputField
import com.example.guess.ui.game.GameActivity

@Composable
fun JoinGroup(viewModel: MainViewModel) {
    val context = LocalContext.current
    val gameConnection by viewModel.gameConnectionState.collectAsState()

    if(gameConnection is GameConnectionState.Success){
        GameActivity.startGame(context, (gameConnection as GameConnectionState.Success).channel.cid)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
    ) {
        var displayName by remember { mutableStateOf("") }
        var groupCode by remember { mutableStateOf("") }

        Text(
            text = stringResource(R.string.display_name),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        TextInputField(
            value = displayName,
            onValueChange = {
                displayName = it
            },
            placeHolder = stringResource(R.string.display_name)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.group_code),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        TextInputField(
            value = groupCode,
            onValueChange = {
                groupCode = it
            },
            placeHolder = stringResource(id = R.string.group_code)
        )

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedButton(
            onClick = {
                viewModel.joinGameGroup(displayName = displayName, groupId = groupCode)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color.Green,
                    shape = RoundedCornerShape(10.dp)
                ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(text = stringResource(R.string.join_group), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun PreviewJoinGroup() {
    JoinGroup(viewModel())
}