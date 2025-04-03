package com.example.guess.ui.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guess.R
import com.example.guess.ui.game.GameActivity
import com.example.guess.utils.groupId
import io.getstream.chat.android.models.Channel

@Composable
fun GroupBottomSheetContent(
    channel: Channel?
) {
    val context = LocalContext.current
    Box(
        Modifier
            .padding(16.dp)
            .heightIn(max = 400.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.group_entrance),
                modifier = Modifier
                    .padding(vertical = 12.dp),
                fontSize = 22.sp
            )
            Text(
                text = stringResource(id = R.string.group_created_desc),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
            )
            Box(
                modifier = Modifier
                    .background(color = Color.Blue, shape = RoundedCornerShape(8.dp))
                    .wrapContentSize()
                    .padding(12.dp)
            ) {
                Text(
                    text = channel?.groupId ?: "",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    modifier = Modifier
                        .clickable {  }
                )
            }
            Text(
                text = stringResource(id = R.string.invite_people),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {  }
            )

            ElevatedButton(
                onClick = {
                    channel?.let { GameActivity.startGame(context = context, it.cid) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .background(
                        color = Color.Blue,
                        shape = RoundedCornerShape(10.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(
                    text = stringResource(id = R.string.continue_to_game),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewGroupBottomSheetContent() {
    GroupBottomSheetContent(null)
}