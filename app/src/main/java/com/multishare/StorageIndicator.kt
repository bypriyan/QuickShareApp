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
import androidx.compose.ui.res.colorResource
import com.multishare.R


@Composable
fun StorageUsageScreen() {
    val statFs = StatFs(Environment.getExternalStorageDirectory().absolutePath)
    val totalBytes = statFs.blockCountLong * statFs.blockSizeLong
    val freeBytes = statFs.availableBlocksLong * statFs.blockSizeLong
    val usedBytes = totalBytes - freeBytes
    val totalSpace = totalBytes.toDouble() / (1024 * 1024 * 1024)
    val usedSpace = usedBytes.toDouble() / (1024 * 1024 * 1024)
    val freeSpace = freeBytes.toDouble() / (1024 * 1024 * 1024)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Optional: Add padding around the box
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .background(color = colorResource(id = R.color.cBlue), shape = RoundedCornerShape(16.dp))
                .padding(16.dp) // Inner padding for the content
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Background CircularProgressIndicator for remaining space
                    CircularProgressIndicator(
                        progress = 1f, // Show full circle
                        color = Color.White, // Gray color for remaining space
                        strokeWidth = 8.dp,
                        modifier = Modifier.size(100.dp)
                    )
                    // Foreground CircularProgressIndicator for used space
                    CircularProgressIndicator(
                        progress = usedSpace.toFloat() / totalSpace.toFloat(),
                        color = Color.Blue, // Blue color for used space
                        strokeWidth = 8.dp,
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "${(usedSpace / totalSpace * 100).toInt()}%",
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // LinearProgressIndicator for used space
                LinearProgressIndicator(
                    progress = usedSpace.toFloat() / totalSpace.toFloat(),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(horizontal = 1.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Row for displaying space information
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Total space", color = Color.White)
                        Text(text = "%.2f GB".format(totalSpace), color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Space used", color = Color.White)
                        Text(text = "%.2f GB".format(usedSpace), color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Free space", color = Color.White)
                        Text(text = "%.2f GB".format(freeSpace), color = Color.White)
                    }
                }
            }
        }
    }
}
