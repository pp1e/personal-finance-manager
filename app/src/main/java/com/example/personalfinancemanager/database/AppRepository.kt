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

    suspend fun insertFinanceOperation(financeOperation: FinanceOperationDbEntity) {
        withContext(Dispatchers.IO) {
            appDao.insertFinanceOperation(financeOperation)
        }
    }

    fun getOperationsData(): Observable<List<FinanceOperationTuple>> =
        appDao
            .getOperationsData()
            .asObservable()

    fun getOperationCategories(limit: Long): Observable<List<OperationCategoryDbEntity>> =
        appDao
            .getFrequencyCategories(limit)
            .asObservable()

    fun getActualBalance(): Observable<BalanceDbEntity> =
        appDao
            .getActualBalance()
            .asObservable()
}
