package com.example.personalfinancemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import com.example.personalfinancemanager.database.entities.FinanceOperationDbEntity
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.database.entities.OperationTypeDbEntity
import java.time.LocalDateTime
import java.util.concurrent.Executors

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
    abstract fun getAppDao(): AppDao

    companion object {
        private const val DATABASE_NAME = "database.db"

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = DATABASE_NAME
            )
                .addCallback(prepopulateCallback)
                .build()
        }

        private val prepopulateCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // pre-populate data
                Executors.newSingleThreadExecutor().execute {
                    instance?.let {
                        val appDao = it.getAppDao()

                        appDao.insertOperationType(
                            OperationTypeDbEntity(
                                id = REFILL_ID,
                                name = "refill"
                            )
                        )
                        appDao.insertOperationType(
                            OperationTypeDbEntity(
                                id = WITHDRAWAL_ID,
                                name = "withdrawal"
                            )
                        )

                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Payment for public transport"
                            )
                        )
                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Payment at the supermarket"
                            )
                        )
                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Payment in restaurants"
                            )
                        )
                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Purchase of medicines"
                            )
                        )
                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Payment for the fuel"
                            )
                        )
                        appDao.insertOperationCategory(
                            OperationCategoryDbEntity(
                                name = "Payment for housing and communal services"
                            )
                        )

                        appDao.insertBalance(
                            BalanceDbEntity(
                                datetime = LocalDateTime.now().toString(),
                                amount = 0f
                            )
                        )
                    }
                }
            }
        }
    }
}
