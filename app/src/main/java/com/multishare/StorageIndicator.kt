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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import coil.size.Size
import com.multishare.FileSection
import com.multishare.ProfileBarrier
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
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
        .statusBarsPadding()) {
         ProfileBarrier()
            SearchBar { /* Handle search here */ }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp) // Optional: Add padding around the box
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(1.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            listOf(Color(0xFF005cb9),
                                Color(0xFF21CBF3)),
                            // Example gradient colors
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp) // Inner padding for the content
            ) {
                Spacer(modifier = Modifier.height(68.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                {   Spacer(modifier = Modifier.height(48.dp),)
                    Text("Your Device Storage", color = Color.White,fontSize = 15.sp,
                        modifier = Modifier.padding(15.dp,0.dp,0.dp,0.dp)) }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Box(contentAlignment = Alignment.Center) {
                        // Background CircularProgressIndicator for remaining space
                        CircularProgressIndicator(
                            progress = 1f, // Show full circle
                            color = colorResource(id=R.color.cGray), // Gray color for remaining space
                            strokeWidth = 8.dp,
                            modifier = Modifier.size(100.dp)
                        )
                        // Foreground CircularProgressIndicator for used space
                        CircularProgressIndicator(
                            progress = usedSpace.toFloat() / totalSpace.toFloat(),
                            color = Color.White, // Blue color for used space
                            strokeWidth = 8.dp,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "${(usedSpace / totalSpace * 100).toInt()}%",
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // LinearProgressIndicator for remaining space in white
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Remaining space LinearProgressIndicator
                        LinearProgressIndicator(
                            progress = .1f, // This will fill the whole width
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .padding(horizontal = 1.dp)
                        )
                        LinearProgressIndicator(
                            progress = usedSpace.toFloat() / totalSpace.toFloat(),
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .padding(horizontal = 1.dp)
                        )
                    }

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

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Space used", color = Color.White)
                            Text(text = "%.2f GB".format(usedSpace), color = Color.White)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Free space", color = Color.White)
                            Text(text = "%.2f GB".format(freeSpace), color = Color.White)
                        }
                    }
                }
            }
        }

        FileSection()
    }

}
