package com.example.personalfinancemanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "finance_operation",
    indices = [Index("id")],
    foreignKeys = [
        ForeignKey(
            entity = OperationCategoryDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["operation_category_id"]
        ),
        ForeignKey(
            entity = OperationTypeDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["operation_type_id"]
        )
    ]
    )
data class FinanceOperationDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "datetime") val datetime: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "operation_category_id") val operationCategoryId: Long,
    @ColumnInfo(name = "operation_type_id") val operationTypeId: Long,
    )