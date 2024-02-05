package com.tonyycruz.credit.application.system.utils

import kotlin.random.Random

class Fake {
    private fun randomString(size: Int): String {
        val charRange = 'a'..'z'
        return (0..size).map { charRange.random() }.joinToString("")
    }

    fun firstName(size: Int = 5): String = randomString(size).titleCase()

    fun lastName(size: Int = 6): String = randomString(size).titleCase()

    fun email(size: Int = 8): String = "${randomString(size)}@email.com"

    fun zipCode(): String = "${Random.nextInt(10000, 99999)}-${Random.nextInt(100, 999)}"

    fun street(): String = "${randomString(6).titleCase()} ${randomString(8).titleCase()}"

    fun password(): String = "${randomString(10).titleCase()}${Random.nextInt(8)}*"

    private fun String.titleCase() = this.replaceFirstChar(Char::titlecase)
}