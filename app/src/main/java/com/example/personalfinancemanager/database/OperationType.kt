package com.example.personalfinancemanager.database

const val REFILL_ID = 1L
const val WITHDRAWAL_ID = 2L

enum class OperationType(val id: Long) {
    REFILL(1),
    WITHDRAWAL(2);

    companion object {
        fun byId(id: Long) =
            when (id) {
                1L -> REFILL
                2L -> WITHDRAWAL
                else -> throw IllegalArgumentException("Unknown operation type ID!")
            }
    }
}
