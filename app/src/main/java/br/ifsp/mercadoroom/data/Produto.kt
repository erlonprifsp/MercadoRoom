package br.ifsp.mercadoroom.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Produto (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome:String,
    val categoria:String,
    val marca:String?
): Serializable