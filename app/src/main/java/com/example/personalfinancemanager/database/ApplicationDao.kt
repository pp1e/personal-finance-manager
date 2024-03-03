package com.example.personalfinancemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinancemanager.database.entities.FinanceOperationDbEntity
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.database.entities.OperationTypeDbEntity
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple

@Dao
interface ApplicationDao {
    @Query("SELECT finance_operation.id, finance_operation.amount, finance_operation.datetime, " +
            "finance_operation.message, operation_type.name as operation_type, " +
            "operation_category.name as operation_category FROM finance_operation " +
            "INNER JOIN operation_category ON " +
                "operation_category.id = finance_operation.operation_category_id " +
            "INNER JOIN operation_type ON operation_type.id = finance_operation.operation_type_id;")
    fun getOperationsData(): List<FinanceOperationTuple>

    @Insert(entity = FinanceOperationDbEntity::class)
    fun insertFinanceOperation(financeOperation: FinanceOperationDbEntity)

    @Insert(entity = OperationCategoryDbEntity::class)
    fun insertOperationCategory(operationCategory: OperationCategoryDbEntity)

    @Insert(entity = OperationTypeDbEntity::class)
    fun insertOperationType(operationType: OperationTypeDbEntity)
}