package com.multishare.file

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multishare.R

@Composable
fun FileSection() {
    val boxHeight = getResponsiveDimension()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        // First Box with weight
        Box(
            modifier = Modifier
                .weight(1f) // Take up equal space
                .padding(10.dp,0.dp,0.dp,0.dp)
                .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(15.dp))
                .height(boxHeight) // Set a fixed height
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                // Center elements in the row
            ) {
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "send img",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                Text("SEND", color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
            }
        }

        // Second Box with weight
        Box(
            modifier = Modifier
                .weight(1f) // Take up equal space
                .padding(8.dp)
                .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(15.dp))
                .height(boxHeight) // Set a fixed height
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                // Center elements in the row
            )    {
                Image(
                    painter = painterResource(R.drawable.down),
                    contentDescription = "send img",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                Text("RECIEVE", color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
            }
        }
        Box(
            modifier = Modifier
                .weight(1f) // Take up equal space
                .padding(0.dp,0.dp,8.dp,0.dp)
                .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(15.dp))
                .height(boxHeight) // Set a fixed height
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                // Center elements in the row
            ) {
                Image(
                    painter = painterResource(R.drawable.folder),
                    contentDescription = "send img",
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                Text("All Files", color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 15.sp)
            }
        }
    }
    Spacer( modifier = Modifier.height(10.dp))
    Box(
        modifier = Modifier.fillMaxWidth().padding(20.dp).background(
            color = colorResource(R.color.cBlue),
            shape = RoundedCornerShape(5.dp)
        )
    ) {
        Column (modifier = Modifier.fillMaxWidth().fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Top) {
            Text("Advertising", fontSize = 22.sp, color = Color.White)
            Text("", fontSize = 22.sp, color = Color.White)
            Text("", fontSize = 22.sp, color = Color.White)
            Text("", fontSize = 22.sp, color = Color.White)
            Text("", fontSize = 22.sp, color = Color.White)
            Text("", fontSize = 22.sp, color = Color.White)

        }
    }
}