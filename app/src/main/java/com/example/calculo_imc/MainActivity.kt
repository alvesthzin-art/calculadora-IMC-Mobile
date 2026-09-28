package com.example.calculo_imc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculo_imc.ui.theme.CalculoIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculoIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCscreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun IMCscreen(modifier: Modifier = Modifier) {

    var altura by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var imc by remember { mutableStateOf(0.0) }
    var textoResultado by remember { mutableStateOf("") }
    var corResultado by remember { mutableStateOf(Color.Gray) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                // Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(color = colorResource(id = R.color.cor_app)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.bmi),
                        contentDescription = "Logo App",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(vertical = 16.dp)
                    )

                    Text(
                        text = "Calculadora IMC",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Formulário
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .offset(y = (-30).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F6F6)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp),
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Seus dados",
                                fontSize = 24.sp,
                                color = colorResource(id = R.color.cor_app),
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = altura,
                                onValueChange = { novoValor -> altura = novoValor },
                                modifier = Modifier.width(250.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                shape = RoundedCornerShape(12.dp),
                                placeholder = { Text(text = "Altura (cm)") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(id = R.color.cor_app),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = peso,
                                onValueChange = { novoValor -> peso = novoValor },
                                modifier = Modifier.width(250.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                shape = RoundedCornerShape(12.dp),
                                placeholder = { Text(text = "Peso (kg)") },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(id = R.color.cor_app),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    val alturaNumero = altura.toDoubleOrNull()
                                    val pesoNumero = peso.toDoubleOrNull()

                                    if (alturaNumero != null && pesoNumero != null && alturaNumero > 0) {
                                        val alturaMetros = alturaNumero / 100
                                        imc = pesoNumero / (alturaMetros * alturaMetros)

                                        when {
                                            imc < 18.5 -> {
                                                textoResultado = "Abaixo do peso"
                                                corResultado = Color.Red
                                            }
                                            imc < 25.0 -> {
                                                textoResultado = "Peso ideal"
                                                corResultado = Color(0xFF579A6E)
                                            }
                                            imc < 30.0 -> {
                                                textoResultado = "Levemente acima do peso"
                                                corResultado = Color.Red
                                            }
                                            imc < 35.0 -> {
                                                textoResultado = "Obesidade grau I"
                                                corResultado = Color.Red
                                            }
                                            imc < 40.0 -> {
                                                textoResultado = "Obesidade grau II"
                                                corResultado = Color.Red
                                            }
                                            else -> {
                                                textoResultado = "Obesidade grau III"
                                                corResultado = Color.Red
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.cor_app)
                                )
                            ) {
                                Text(
                                    text = "Calcular",
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Limpar dados
                            Button(
                                onClick = {
                                    altura = ""
                                    peso = ""
                                    imc = 0.0
                                    textoResultado = ""
                                    corResultado = Color.Gray
                                },
                                modifier = Modifier.width(250.dp),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "Limpar dados",
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Card resultado
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = corResultado
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (imc > 0) "%.2f - $textoResultado".format(imc) else "Informe seus dados",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}