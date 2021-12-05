import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import exceptions.ExpressionParseException
import java.util.*

@Composable
@Preview
fun App() {
    var result by remember { mutableStateOf(0.0) };
    var input by remember { mutableStateOf("") }
    var isResultError by remember { mutableStateOf(false) }
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = 1.5f,
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = input,
                onValueChange = { newValue ->
                    input = newValue

                },
                isError = isResultError,
                modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                singleLine = true
            )
            Text(
                text = result.toString(),
                fontSize = 24.sp,
                modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
            )
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        try {
                            result = CalculatorInterface.Calculate(input);
                            isResultError = false
                        } catch (e: ExpressionParseException) {
                            isResultError = true
                            System.err.println("Wrong symbol found")
                        } catch (e: EmptyStackException) {
                            isResultError = true
                            System.err.println("Syntax error")
                        } catch (e: Exception) {
                            isResultError = true
                            e.printStackTrace()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White)
                ) {
                    Text("=")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input = ""
                    }
                ) {
                    Text("C")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input = input.dropLast(1)
                    }
                ) {
                    Text("<=")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += '/'
                    }
                ) {
                    Text("/")
                }
            }
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                for (i in 7..9) {
                    Button(
                        modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                        onClick = {
                            input += i.toString()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(i.toString())
                    }
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += '*'
                    }
                ) {
                    Text("*")
                }
            }
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                for (i in 4..6) {
                    Button(
                        modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                        onClick = {
                            input += i.toString()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(i.toString())
                    }
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += '-'
                    }
                ) {
                    Text("-")
                }
            }
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                for (i in 1..3) {
                    Button(
                        modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                        onClick = {
                            input += i.toString()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(i.toString())
                    }
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += '+'
                    }
                ) {
                    Text("+")
                }
            }
            Row(modifier = Modifier.fillMaxSize().weight(1f)) {
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += "0"
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                ) {
                    Text("0")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = { input += '(' }
                ) {
                    Text("(")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = { input += ')' }
                ) {
                    Text(")")
                }
                Button(
                    modifier = Modifier.fillMaxSize().weight(1f).padding(6.dp),
                    onClick = {
                        input += ','
                    }
                ) {
                    Text(",")
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
