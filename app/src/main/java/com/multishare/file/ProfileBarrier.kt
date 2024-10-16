package com.multishare.file

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.multishare.R
@Composable
fun ProfileBarrier() {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(50.dp)
            .background(color = colorResource(R.color.cBlue))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween// Center content vertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.able_rows_24),
            contentDescription = "image"
        )

        Text(
            "TV MULTISHARE",
            fontSize = 15.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = Color.White // Optional: Set text color to improve visibility
        )
        Image(modifier = Modifier.padding(0.dp,3.dp,0.dp),
            painter = painterResource(id = R.drawable.scan),
            contentDescription = "image"
        )
    }
}

