package com.upiiz.jscb_examen_2.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String
)


