package com.multishare

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.AlertDialogDefaults.textContentColor
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import coil.size.Size
import com.multishare.R
@Composable
fun ProfileBarrier() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
        Image(
            painter = painterResource(id = R.drawable.qr_code_scanner_24),
            contentDescription = "image"
        )
    }
}

