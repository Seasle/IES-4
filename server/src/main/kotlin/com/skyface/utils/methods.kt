package com.skyface.utils

import com.skyface.database.Database
import kotlin.random.Random

private const val hours = 60 * 60 * 1000

fun populateWaterJournal() {
    val currentTime = System.currentTimeMillis()

    val total = Database.getWaterValueSum(1)
    val factor = waterFormula(total!!.toDouble())
    var hotWater = Database.getWaterLastValue(1, "hot_water_consumption")!!
    var coldWater = Database.getWaterLastValue(1, "cold_water_consumption")!!

    for (i in 0 until 10) {
        hotWater = round(hotWater + Random.nextDouble(0.2, 1.0) * factor, 2.0)
        coldWater = round(coldWater + Random.nextDouble(0.2, 1.6) * factor, 2.0)

        Database.insertWaterJournal(1, currentTime + hours * i, hotWater, coldWater)
    }
}

fun populateVMJ() {
    val currentTime = System.currentTimeMillis()

    for (i in 0 until 10) {
        Database.insertVMJournal(1, currentTime + hours * i, Random.nextInt(0, 2))
    }
}

fun populateSecurityJournal() {
    val currentTime = System.currentTimeMillis()

    for (i in 0 until 10) {
        Database.insertSecurityJournal(1, currentTime + hours * i, Random.nextInt(0, 2))
    }
}

fun populatePowerJournal() {
    val currentTime = System.currentTimeMillis()
    val total = Database.getPowerValueSum(1)
    val factor = powerFormula(total!!.toDouble())
    var initialPower = 1200.0

    for (i in 0 until 10) {
        initialPower = round(initialPower + Random.nextDouble(0.2, 5.0) * factor, 2.0)

        Database.insertPowerJournal(1, currentTime + hours * i, initialPower)
    }
}

fun populateHASJournal() {
    val currentTime = System.currentTimeMillis()

    for (i in 0 until 10) {
        Database.insertHAJournal(
            1,
            currentTime + hours * i,
            Random.nextInt(0, 2),
            Random.nextInt(0, 2),
            Random.nextInt(0, 2),
            Random.nextInt(0, 2),
            Random.nextInt(0, 2),
            Random.nextInt(0, 2)
        )
    }
}

fun populateEquipmentJournal() {
    val currentTime = System.currentTimeMillis()

    for (i in 0 until 10) {
        Database.insertEquipmentJournal(
            1,
            currentTime + hours * i,
            Random.nextInt(0, 2),
            Random.nextInt(0, 2),
            Random.nextInt(0, 2)
        )
    }
}

fun populateClimateJournal() {
    val currentTime = System.currentTimeMillis()
    var initialTemp = 22.5
    var initialHumidity = 63.0

    for (i in 0 until 10) {
        initialTemp = clamp(15.0, 31.0, round(initialTemp + Random.nextDouble(-0.5, 0.5), 2.0))
        initialHumidity = clamp(0.0, 100.0, round(initialHumidity + Random.nextDouble(-3.5, 3.5), 2.0))

        Database.insertClimateJournal(1, currentTime + hours * i, initialTemp, initialHumidity)
    }
}