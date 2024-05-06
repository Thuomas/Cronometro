import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cronometro.ui.theme.CronometroTheme
import kotlinx.coroutines.delay

@Composable
fun CronometroView(modifier: Modifier = Modifier) {

    var prendido by remember {
        mutableStateOf(false)
    }
    var tiempo by remember {
        mutableStateOf<Long>(0)
    }
    LaunchedEffect(prendido) {
        while (prendido) {
            delay(1)
            tiempo++;
        }
    }

    var vueltas = remember {
        mutableListOf<Long>()
    }

    var ultimaVuelta by remember {
        mutableStateOf<Long>(0)
    }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = DateUtils.formatElapsedTime(tiempo),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),

                style = MaterialTheme.typography.titleLarge
            )
            if (prendido) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            var vuelta = tiempo - ultimaVuelta
                            ultimaVuelta = tiempo
                            vueltas.add(vuelta)
                        },
                        modifier = Modifier
                            .height(100.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(text = "Lap")
                    }

                    Button(
                        onClick = { prendido = false }, modifier = Modifier
                            .height(100.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Stop")
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            tiempo = 0
                            vueltas.clear()
                            ultimaVuelta = 0
                        }, modifier = Modifier
                            .height(100.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Text(text = "ReStart")
                    }

                    Button(
                        onClick = { prendido = true }, modifier = Modifier
                            .height(100.dp)
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(text = "Start")
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            var contador = 1
            items(items = vueltas) {
                Row {
                    Text(text = "Vuelta " + contador++ + ": ")
                    Text(DateUtils.formatElapsedTime(it))
                }

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun CronometroPreview() {
    CronometroTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CronometroView()
        }
    }
}


