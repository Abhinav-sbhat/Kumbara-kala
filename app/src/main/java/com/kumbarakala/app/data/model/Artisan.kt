package com.kumbarakala.app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artisans")
data class Artisan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "email")
    val email: String = "",

    @ColumnInfo(name = "phone")
    val phone: String = "",

    @ColumnInfo(name = "village")
    val village: String = "",

    @ColumnInfo(name = "password_hash")
    val passwordHash: String = "",

    @ColumnInfo(name = "bio")
    val bio: String = "",

    @ColumnInfo(name = "profile_photo")
    val profilePhoto: String = "",

    @ColumnInfo(name = "experience")
    val experience: String = "",

    @ColumnInfo(name = "specialty")
    val specialty: String = "",

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
