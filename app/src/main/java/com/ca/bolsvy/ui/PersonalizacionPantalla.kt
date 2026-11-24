package com.ca.bolsvy.ui

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ca.bolsvy.R
import com.ca.bolsvy.data.AppScreens
import com.ca.bolsvy.ui.theme.BolsvyTheme

private data class ColorOpcion(val nombre: Int, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizacionPantalla(navController: NavController) {
    val context = LocalContext.current

    val tipos = listOf(
        R.string.tipo_tote,
        R.string.tipo_cosmetiquera,
        R.string.tipo_mochila
    )
    val colores = listOf(
        ColorOpcion(R.string.color_lila, Color(0xFFD9CCF0)),
        ColorOpcion(R.string.color_azul, Color(0xFFC5DDE8)),
        ColorOpcion(R.string.color_rosa, Color(0xFFF3C6D3)),
        ColorOpcion(R.string.color_cafe, Color(0xFF5A4636))
    )

    var tipo by rememberSaveable { mutableIntStateOf(tipos[0]) }
    var colorSeleccionado by rememberSaveable { mutableIntStateOf(0) }
    var diseno by rememberSaveable { mutableStateOf("") }
    var detalles by rememberSaveable { mutableStateOf("") }
    var menuAbierto by remember { mutableStateOf(false) }

    // Textos que se usan al enviar la solicitud
    val tipoTexto = stringResource(tipo)
    val colorTexto = stringResource(colores[colorSeleccionado].nombre)
    val tituloCompartir = stringResource(R.string.pers_compartir)
    val mensaje = stringResource(
        R.string.pers_mensaje,
        tipoTexto,
        colorTexto,
        diseno,
        detalles
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_regresar),
                            contentDescription = stringResource(R.string.desc_regresar),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.pers_titulo),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.pers_subtitulo),
                style = MaterialTheme.typography.bodyMedium
            )

            // Tipo de bolsa
            SeccionCard {
                Text(
                    text = stringResource(R.string.pers_tipo),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    Button(
                        onClick = { menuAbierto = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(
                            text = tipoTexto,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = menuAbierto,
                        onDismissRequest = { menuAbierto = false }
                    ) {
                        tipos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(stringResource(opcion)) },
                                onClick = {
                                    tipo = opcion
                                    menuAbierto = false
                                }
                            )
                        }
                    }
                }
            }

            // Color principal
            SeccionCard {
                Text(
                    text = stringResource(R.string.pers_color),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    colores.forEachIndexed { indice, opcion ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(opcion.color)
                                .border(
                                    border = if (indice == colorSeleccionado) {
                                        BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
                                    } else {
                                        BorderStroke(0.dp, Color.Transparent)
                                    },
                                    shape = CircleShape
                                )
                                .clickable { colorSeleccionado = indice }
                        )
                    }
                }
            }

            // Diseño y detalles
            SeccionCard {
                Text(
                    text = stringResource(R.string.pers_diseno),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = diseno,
                    onValueChange = { diseno = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.pers_diseno_hint)) },
                    minLines = 3
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.pers_detalles),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = detalles,
                    onValueChange = { detalles = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.pers_detalles_hint)) },
                    minLines = 3
                )
            }

            // Botón: Enviar solicitud
            Button(
                onClick = {
                    val envio = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, mensaje)
                    }
                    context.startActivity(Intent.createChooser(envio, tituloCompartir))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) {
                Text(stringResource(R.string.btn_enviar_solicitud))
            }

            // Botón: Volver al catálogo
            Button(
                onClick = {
                    navController.navigate(AppScreens.Catalogo.route) {
                        popUpTo(AppScreens.Inicio.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(stringResource(R.string.btn_volver_catalogo))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SeccionCard(contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            contenido()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalizacionPantallaPreview() {
    BolsvyTheme {
        PersonalizacionPantalla(rememberNavController())
    }
}