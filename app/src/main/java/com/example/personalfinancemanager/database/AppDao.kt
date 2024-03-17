package com.example.personalfinancemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import com.example.personalfinancemanager.database.entities.FinanceOperationDbEntity
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.database.entities.OperationTypeDbEntity
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query(
        "SELECT finance_operation.id, finance_operation.amount, finance_operation.datetime, " +
            "finance_operation.message, operation_type.name as operation_type, " +
            "operation_category.name as operation_category FROM finance_operation " +
            "INNER JOIN operation_category ON " +
            "operation_category.id = finance_operation.operation_category_id " +
            "INNER JOIN operation_type ON operation_type.id = finance_operation.operation_type_id;"
    )
    fun getOperationsData(): Flow<List<FinanceOperationTuple>>

    @Query(
        "SELECT id, name, frequency FROM operation_category  ORDER BY frequency DESC LIMIT :limit"
    )
    fun getFrequencyCategories(limit: Long): Flow<List<OperationCategoryDbEntity>>

    @Insert(entity = FinanceOperationDbEntity::class)
    fun insertFinanceOperation(financeOperation: FinanceOperationDbEntity)

    @Insert(entity = OperationCategoryDbEntity::class)
    fun insertOperationCategory(operationCategory: OperationCategoryDbEntity)

    @Insert(entity = OperationTypeDbEntity::class)
    fun insertOperationType(operationType: OperationTypeDbEntity)

    @Insert(entity = BalanceDbEntity::class)
    fun insertBalance(balanceDbEntity: BalanceDbEntity)

    @Query("UPDATE operation_category SET frequency = frequency + 1 WHERE id = :id")
    fun increaseCategoryFrequency(id: Long)

    @Query("SELECT * FROM balance ORDER BY datetime LIMIT 1")
    fun getActualBalance(): Flow<BalanceDbEntity>
}
