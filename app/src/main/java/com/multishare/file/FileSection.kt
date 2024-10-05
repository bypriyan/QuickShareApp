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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween // Space out the content
    ) {
        // Main content area
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
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(15.dp))
                    .height(boxHeight) // Set a fixed height
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.send),
                        contentDescription = "send img",
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                    Text("SEND", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                ) {
                    Image(
                        painter = painterResource(R.drawable.down),
                        contentDescription = "receive img",
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                    Text("RECEIVE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            // Third Box with weight
            Box(
                modifier = Modifier
                    .weight(1f) // Take up equal space
                    .padding(0.dp, 0.dp, 8.dp, 0.dp)
                    .background(color = colorResource(R.color.cBlue), shape = RoundedCornerShape(15.dp))
                    .height(boxHeight) // Set a fixed height
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.folder),
                        contentDescription = "folder img",
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // Space between image and text
                    Text("All Files", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }

   Spacer(modifier = Modifier.height(166.dp))
        cc()
    }
}

@Composable
fun cc() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(1.dp)
            .background(color = colorResource(R.color.cBlue))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("", fontSize = 22.sp, color = Color.White, modifier = Modifier.height(20.dp))
            Text("Ad", fontSize = 22.sp, color = Color.White, modifier = Modifier.height(20.dp))
            Text("", fontSize = 22.sp, color = Color.White, modifier = Modifier.height(20.dp))
            Text("", fontSize = 22.sp, color = Color.White, modifier = Modifier.height(20.dp))
        }
    }
}
