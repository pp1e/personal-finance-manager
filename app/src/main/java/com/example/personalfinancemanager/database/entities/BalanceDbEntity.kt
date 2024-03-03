package com.example.personalfinancemanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "balance",
    indices = [Index("id")],
    )
data class BalanceDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "datetime") val datetime: String,
    @ColumnInfo(name = "amount") val amount: Float,
)