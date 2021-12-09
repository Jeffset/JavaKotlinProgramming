import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import exceptions.ExpressionParseException
import java.util.*

@Composable
@Preview
fun App() {
    var result by remember { mutableStateOf(0.0) }
    var input by remember { mutableStateOf("") }
    var isResultError by remember { mutableStateOf(false) }
    var variables by remember { mutableStateOf(HashMap<String, Double>()) }
    var expressionInfo by remember { mutableStateOf("") }
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = 1.5f,
            fontScale = 1.5f
        )
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().weight(1f)) {
                TextField(
                    value = input,
                    onValueChange = { newValue ->
                        input = newValue

                    },
                    isError = isResultError,
                    modifier = Modifier.fillMaxSize().weight(2f).padding(6.dp),
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
                                val root = ParserImpl().parseExpression(input)
                                expressionInfo = "Tree: " + root.accept(DebugRepresentationExpressionVisitor()) + '\n' +
                                        "Tree depth: " + root.accept(TreeDepthVisitor())
                                variables = root.accept(VariableQueryVisitor()) as HashMap<String, Double>
                                if (variables.isEmpty()) {
                                    result = root.accept(ComputeExpressionVisitor(variables)) as Double
                                    isResultError = false
                                }
                            } catch (e: ExpressionParseException) {
                                isResultError = true
                                expressionInfo = "Wrong symbol found"
                            } catch (e: EmptyStackException) {
                                isResultError = true
                                expressionInfo = "Syntax error"
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
                        Text("<")
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
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
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
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
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
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Blue,
                                contentColor = Color.White
                            )
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    expressionInfo, modifier = Modifier.weight(1f).padding(6.dp)
                )
                Button(
                    modifier = Modifier.weight(1f).padding(6.dp).fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red, contentColor = Color.White),
                    onClick = {
                        try {
                            val root = ParserImpl().parseExpression(input)
                            expressionInfo = "Tree: " + root.accept(DebugRepresentationExpressionVisitor()) + '\n' +
                                    "Tree depth: " + root.accept(TreeDepthVisitor())
                            result = root.accept(ComputeExpressionVisitor(variables)) as Double
                            isResultError = false
                        } catch (e: Exception) {
                            expressionInfo = "Syntax error"
                        }
                    }
                ) {
                    Text("Apply")
                }
                for ((name, value) in variables) {
                    Row(modifier = Modifier.weight(1f)) {
                        Box(modifier = Modifier.weight(1f).fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(name, textAlign = TextAlign.Center)
                        }
                        var var_input by remember { mutableStateOf("") }
                        var isVarInputError by remember { mutableStateOf(false) }
                        TextField(
                            value = var_input,
                            onValueChange = { newValue ->
                                var_input = newValue
                                var number = 0.0
                                try {
                                    number = newValue.toDouble()
                                    isVarInputError = false
                                } catch (e: NumberFormatException) {
                                    isVarInputError = true
                                }
                                variables[name] = number
                            },
                            modifier = Modifier.weight(1f).padding(6.dp).fillMaxSize(),
                            singleLine = true,
                            isError = isVarInputError
                        )
                    }

                }
            }
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 700.dp, height = 700.dp)
    ) {
        App()
    }
}
