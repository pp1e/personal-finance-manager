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
import java.time.LocalDateTime

@Dao
interface AppDao {
    @Query(
        "SELECT finance_operation.id, finance_operation.amount, finance_operation.datetime, " +
            "finance_operation.message, finance_operation.operation_type_id, " +
            "operation_category.name as operation_category FROM finance_operation " +
            "INNER JOIN operation_category ON " +
            "operation_category.id = finance_operation.operation_category_id " +
            "ORDER BY finance_operation.datetime DESC;"
    )
    fun getOperationsData(): Flow<List<FinanceOperationTuple>>

    @Query(
        "SELECT id, name, frequency FROM operation_category  ORDER BY frequency DESC LIMIT :limit"
    )
    fun getFrequencyCategoriesWithLimit(limit: Long): Flow<List<OperationCategoryDbEntity>>

    @Query(
        "SELECT id, name, frequency FROM operation_category  ORDER BY frequency DESC"
    )
    fun getFrequencyCategories(): Flow<List<OperationCategoryDbEntity>>

    @Insert(entity = FinanceOperationDbEntity::class)
    fun insertFinanceOperation(financeOperation: FinanceOperationDbEntity)

    @Insert(entity = OperationCategoryDbEntity::class)
    fun insertOperationCategory(operationCategory: OperationCategoryDbEntity): Long

    @Insert(entity = OperationTypeDbEntity::class)
    fun insertOperationType(operationType: OperationTypeDbEntity)

    @Insert(entity = BalanceDbEntity::class)
    fun insertBalance(balanceDbEntity: BalanceDbEntity)

    @Query("UPDATE operation_category SET frequency = frequency + 1 WHERE id = :id")
    fun increaseCategoryFrequency(id: Long)

    @Query("SELECT * FROM balance ORDER BY datetime DESC LIMIT 1")
    fun getActualBalance(): Flow<BalanceDbEntity?>

    @Query("SELECT * FROM operation_category WHERE name = :name")
    fun getOperationCategoryByName(name: String): OperationCategoryDbEntity?

    @Query("INSERT INTO balance (datetime, amount) VALUES (:datetime," +
            " (SELECT amount FROM balance ORDER BY datetime DESC LIMIT 1) + :amount);")
    fun updateBalance(amount: Float, datetime: String = LocalDateTime.now().toString())

    @Query("SELECT * FROM balance")
    fun getBalanceHistory(): Flow<List<BalanceDbEntity>>
}
