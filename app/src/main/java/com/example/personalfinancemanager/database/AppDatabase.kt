package com.example.personalfinancemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import com.example.personalfinancemanager.database.entities.FinanceOperationDbEntity
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.database.entities.OperationTypeDbEntity

@Database(
    version = 1,
    entities = [
        FinanceOperationDbEntity::class,
        BalanceDbEntity::class,
        OperationTypeDbEntity::class,
        OperationCategoryDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getApplicationDao(): ApplicationDao

}
