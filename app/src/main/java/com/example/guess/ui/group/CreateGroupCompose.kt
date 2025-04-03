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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guess.R
import com.example.guess.ui.main.MainViewModel
import com.example.guess.ui.components.TextInputField

@Composable
fun CreateGroupCompose(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
    ) {
        var displayName by remember { mutableStateOf("") }
        var limitUser by remember { mutableStateOf(5) }
        var limitTime by remember { mutableStateOf(60) }

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
            text = stringResource(R.string.limit_user),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        TextInputField(
            value = limitUser.toString(),
            onValueChange = {
                limitUser = it.toInt()
            },
            placeHolder = "Enter the user limit"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.limit_time),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        TextInputField(
            value = limitTime.toString(),
            onValueChange = {
                limitTime = it.toInt()
            },
            placeHolder = "Enter the time limit"
        )

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedButton(
            onClick = {
                viewModel.createGameGroup(displayName = displayName, limitUser = limitUser, limitTime = limitTime)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(10.dp)
                ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "Create Room", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun PreviewCreateGroupCompose() {
    CreateGroupCompose(viewModel = viewModel())
}