package com.example.matchessimulatorapp.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Match (
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("local")
    val place: Place,
    @SerializedName("mandante")
    val homeTeam: Team,
    @SerializedName("visitante")
    val awayTeam: Team
) : Parcelable