package com.example.personalfinancemanager.database

const val REFILL_ID = 1L
const val WITHDRAWAL_ID = 2L

enum class OperationTypes(val id: Long) {
    REFILL(1),
    WITHDRAWAL_ID(2)
}
