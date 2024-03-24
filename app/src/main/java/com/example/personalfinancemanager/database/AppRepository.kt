package com.example.personalfinancemanager.database

import com.badoo.reaktive.coroutinesinterop.asObservable
import com.badoo.reaktive.observable.Observable
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import com.example.personalfinancemanager.database.entities.FinanceOperationDbEntity
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.database.entities.OperationTypeDbEntity
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class AppRepository(private val appDao: AppDao) {

    suspend fun insertOperationType(operationType: OperationTypeDbEntity) {
        withContext(Dispatchers.IO) {
            appDao.insertOperationType(operationType)
        }
    }

    suspend fun insertOperationCategory(operationCategory: OperationCategoryDbEntity) {
        withContext(Dispatchers.IO) {
            appDao.insertOperationCategory(operationCategory)
        }
    }

    suspend fun insertFinanceOperation(
        category: String,
        type: OperationType,
        message: String,
        amount: Float
    ) {
        withContext(Dispatchers.IO) {
            val categoryDbEntity = appDao.getOperationCategoryByName(category).value
            val categoryId: Long
            if (categoryDbEntity == null) {
                categoryId = appDao.insertOperationCategory(
                    OperationCategoryDbEntity(
                        name = category
                    )
                )
            } else {
                categoryId = categoryDbEntity.id
            }

            appDao.insertFinanceOperation(
                FinanceOperationDbEntity(
                    datetime = LocalDateTime.now().toString(),
                    operationCategoryId = categoryId,
                    operationTypeId = type.id,
                    message = message,
                    amount = amount
                )
            )
        }
    }

    fun getOperationsData(): Observable<List<FinanceOperationTuple>> =
        appDao
            .getOperationsData()
            .asObservable()

    fun getOperationCategories(limit: Long? = null): Observable<List<OperationCategoryDbEntity>> =
        if (limit == null) {
            appDao
                .getFrequencyCategories()
                .asObservable()
        } else {
            appDao
                .getFrequencyCategoriesWithLimit(limit)
                .asObservable()
        }

    fun getActualBalance(): Observable<BalanceDbEntity> =
        appDao
            .getActualBalance()
            .asObservable()
}
