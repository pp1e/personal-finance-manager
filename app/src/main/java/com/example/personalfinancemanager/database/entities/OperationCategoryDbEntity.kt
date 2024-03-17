package com.example.personalfinancemanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "operation_category",
    indices = [Index("id")]
)
data class OperationCategoryDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "frequency", defaultValue = "0") val frequency: Long = 0,
    @ColumnInfo(name = "name") val name: String
)
