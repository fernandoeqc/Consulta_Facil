package com.example.consulta_facil

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Prescricao(
    val id: String? = null,
    val dias: String? = null,
    val nome: String? = null,
    val validade: String? = null,
) : Parcelable
