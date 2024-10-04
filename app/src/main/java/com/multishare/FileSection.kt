package com.multishare

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FileSection() {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First Box with weight
        Box(
            modifier = Modifier
                .weight(1f) // Take up equal space
                .padding(15.dp)
                .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(40.dp))
                .height(120.dp) // Set a fixed height
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // Center elements in the row
            ) {
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "send img",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp)) // Space between image and text
                Text("SEND", color = Color.White, fontSize = 22.sp)
            }
        }

        // Second Box with weight
        // First Box with weight
        Box(
            modifier = Modifier
                .weight(1f) // Take up equal space
                .padding(15.dp)
                .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(40.dp))
                .height(120.dp) // Set a fixed height
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center // Center elements in the row
            ) {
                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "send img",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(5.dp)) // Space between image and text
                Text(
                    "RECIEVE",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp)
                )
            }
        }
    }
    Spacer( modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier.fillMaxWidth().padding(20.dp).background(
            color = colorResource(R.color.cBlue),
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        Column (modifier = Modifier.fillMaxWidth().fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Top) {
            Text("Advertising", fontSize = 22.sp, color = Color.White)

        }
    }
}