package com.example.ssutask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookDAOEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title: String?,
    var description: String?
)
