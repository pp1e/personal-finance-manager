package com.example.personalfinancemanager.database.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class FinanceOperationTuple(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "datetime") val datetime: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "operation_category") val operationCategory: String,
    @ColumnInfo(name = "operation_type") val operationType: String
)
