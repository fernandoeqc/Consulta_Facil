package com.example.consulta_facil

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vacina(
    val id: String? = null,
    val nomeMedico: String? = null,
    val specialty: String? = null,
    val data: String? = null,
    val endereco: String? = null,
    val hour: String? = null
) : Parcelable
