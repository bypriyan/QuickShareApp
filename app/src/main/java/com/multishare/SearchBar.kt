import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.ui.res.colorResource
import com.multishare.R
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource

@Composable
fun SearchBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onSearch: (String) -> Unit
) {
    val query = remember { mutableStateOf("") }

    OutlinedTextField(
        value = query.value,
        onValueChange = { query.value = it },
        placeholder = { Text("Search...", fontSize = 14.sp) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(query.value)
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(10.dp,
                RoundedCornerShape(50.dp)), // Add shadow for elevation
        shape = RoundedCornerShape(50.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_24), // Replace with your search icon resource
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        },

                colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.white), // Background color
            unfocusedContainerColor = colorResource(id = R.color.white), // Background color
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
