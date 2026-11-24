package com.ca.bolsvy.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ca.bolsvy.R
import com.ca.bolsvy.ui.theme.BolsvyTheme


private const val WHATSAPP_URL = "https://wa.me/5544759732"
private const val INSTAGRAM_URL = "https://www.instagram.com/bolsvy?bqrl=Mm0xNHhjZ2dwcnNt"
private const val FACEBOOK_URL = "https://facebook.com/tu_pagina" //aún no hay pagina en facebook

private fun abrirEnlace(context: Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    } catch (e: ActivityNotFoundException) {
        // No hay una app o navegador que pueda abrir el enlace
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoPantalla(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_contacto),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo_bolsvy),
                contentDescription = stringResource(R.string.desc_logo),
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = stringResource(R.string.cont_pregunta),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.cont_texto),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))

            BotonContacto(R.string.cont_whatsapp, Color(0xFFA8D8B0)) {
                abrirEnlace(context, WHATSAPP_URL)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BotonContacto(R.string.cont_instagram, Color(0xFFD18AB3)) {
                abrirEnlace(context, INSTAGRAM_URL)
            }
            Spacer(modifier = Modifier.height(12.dp))
            BotonContacto(R.string.cont_facebook, Color(0xFF9DB8E0)) {
                abrirEnlace(context, FACEBOOK_URL)
            }
        }
    }
}

@Composable
private fun BotonContacto(texto: Int, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(stringResource(texto))
    }
}

@Preview(showBackground = true)
@Composable
fun ContactoPantallaPreview() {
    BolsvyTheme {
        ContactoPantalla(rememberNavController())
    }
}