package com.skyface.database

import com.google.common.base.CaseFormat.LOWER_CAMEL
import com.google.common.base.CaseFormat.LOWER_UNDERSCORE
import com.skyface.model.House
import com.skyface.model.Modules
import java.sql.Connection
import java.sql.DriverManager

object Database {
    private var connection: Connection?

    init {
        val path = javaClass.getResource("/raw/smart-house.db")
        connection = DriverManager.getConnection("jdbc:sqlite::resource:$path")
    }

    fun insertControlJournal(msg: String, houseId: Int) {
        insert(
            """
           INSERT INTO control_supply_journal
           (module_id, timestamp, message)
           VALUES (
                (SELECT id FROM control_supply
                WHERE house_id = $houseId
                LIMIT 1),
                ${System.currentTimeMillis()},
                '$msg'
            )
        """
        )
    }

    fun getWaterLastValue(moduleId: Int, waterType: String): Double? {
        return connection?.let { connection ->
            val query = """
                SELECT $waterType FROM water_supply_journal
                WHERE module_id = $moduleId
                ORDER BY $waterType DESC 
                LIMIT 1
                """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            resultSet.getDouble(waterType)
        }
    }

    fun getWaterValueSum(moduleId: Int): Int? {
        return connection?.let { connection ->
            val query = """
                SELECT SUM(value) as total FROM water_supply_description 
                WHERE module_id = $moduleId
                """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            resultSet.getInt("total")
        }
    }

    fun insertWaterJournal(moduleId: Int, timestamp: Long, hwc: Double, cwc: Double) {
        insert(
            """
            INSERT INTO water_supply_journal
            (module_id, timestamp, hot_water_consumption, cold_water_consumption)
            VALUES
            ($moduleId, $timestamp, $hwc, $cwc)
        """
        )
    }

    fun insertVMJournal(moduleId: Int, timestamp: Long, camera: Int) {
        insert(
            """
            INSERT INTO video_monitoring_supply_journal
            (module_id, timestamp, camera)
            VALUES
            ($moduleId, $timestamp, $camera)
        """
        )
    }

    fun insertSecurityJournal(moduleId: Int, timestamp: Long, security: Int) {
        insert(
            """
            INSERT INTO security_supply_journal
            (module_id, timestamp, security)
            VALUES
            ($moduleId, $timestamp, $security)
        """
        )
    }

    fun getPowerValueSum(moduleId: Int): Int? {
        return connection?.let { connection ->
            val query = """
                SELECT SUM(value) as total FROM power_supply_description 
                WHERE module_id = $moduleId
                """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            resultSet.getInt("total")
        }
    }

    fun insertPowerJournal(moduleId: Int, timestamp: Long, energy: Double) {
        insert(
            """
            INSERT INTO power_supply_journal
            (module_id, timestamp, energy_consumption)
            VALUES
            ($moduleId, $timestamp, $energy)
        """
        )
    }

    fun insertHAJournal(
        moduleId: Int,
        timestamp: Long,
        kettle: Int,
        fridge: Int,
        microwave: Int,
        washingMachine: Int,
        dishwasher: Int,
        stove: Int
    ) {
        insert(
            """
            INSERT INTO household_appliance_supply_journal
            (module_id, timestamp, kettle, fridge, microwave, washing_machine, dishwasher, stove)
            VALUES
            ($moduleId, $timestamp, $kettle, $fridge, $microwave, $washingMachine, $dishwasher, $stove)
        """
        )
    }

    fun insertEquipmentJournal(moduleId: Int, timestamp: Long, tv: Int, computer: Int, console: Int) {
        insert(
            """
            INSERT INTO equipment_supply_journal (module_id, timestamp, tv, computer, console)
            VALUES ($moduleId, $timestamp, $tv, $computer, $console)
        """
        )
    }

    fun insertClimateJournal(moduleId: Int, timestamp: Long, temperature: Double, humidity: Double) {
        insert(
            """
            INSERT INTO climate_supply_journal (module_id, timestamp, temperature, humidity) 
            VALUES ($moduleId, $timestamp, $temperature, $humidity)
        """
        )
    }

    private fun insert(query: String) {
        connection?.let { connection ->
            val statement = connection.createStatement()
            statement.executeUpdate(query.trimIndent())
            statement.closeOnCompletion()
        }
    }

    fun getJournal(name: String, houseId: Int): List<HashMap<String, Any>> {
        val list = mutableListOf<HashMap<String, Any>>()
        connection?.let { connection ->
            val query = """
                SELECT * FROM ${name}_journal 
                WHERE module_id = (
                    SELECT id FROM $name
                    WHERE house_id = $houseId
                    LIMIT 1
                )
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            val metaData = resultSet.metaData
            val colCount = metaData.columnCount
            val columnNames = mutableListOf<String>()
            for (i in 1..colCount) {
                columnNames.add(metaData.getColumnName(i))
            }

            while (resultSet.next()) {
                val entry = linkedMapOf<String, Any>()

                for (item in columnNames) {
                    entry[LOWER_UNDERSCORE.to(LOWER_CAMEL, item)] = resultSet.getObject(item)
                }

                list.add(entry)
            }

            return list
        }

        return list
    }

    fun getModules(houseId: Int): Modules {
        val modules = Modules()

        modules.powerSupply = getModule("power_supply", houseId)
        modules.waterSupply = getModule("water_supply", houseId)
        modules.climateSupply = getModule("climate_supply", houseId)
        modules.equipmentSupply = getModule("equipment_supply", houseId)
        modules.householdApplianceSupply = getModule("household_appliance_supply", houseId)
        modules.videoMonitoringSupply = getModule("video_monitoring_supply", houseId)
        modules.securitySupply = getModule("security_supply", houseId)
        modules.controlSupply = getModule("control_supply", houseId)

        return modules
    }

    private fun getModule(name: String, houseId: Int): MutableList<HashMap<String, String>>? {
        connection?.let { connection ->
            val list = mutableListOf<HashMap<String, String>>()
            val query = """
                SELECT key, value FROM ${name}_description
                WHERE module_id = (
                	SELECT id FROM $name
                	WHERE house_id = $houseId
                	LIMIT 1
                )
            """.trimIndent()

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            while (resultSet.next()) {
                list.add(
                    hashMapOf(
                        "key" to resultSet.getString("key"),
                        "value" to resultSet.getString("value")
                    )
                )
            }
            return if (list.size > 0) list else null
        }
        return null
    }

    fun getHouses(): MutableList<House> {
        val list = mutableListOf<House>()
        connection?.let { connection ->
            val query = "SELECT * FROM houses"

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(query)
            statement.closeOnCompletion()

            while (resultSet.next()) {
                list.add(
                    House(
                        resultSet.getInt("id"),
                        resultSet.getInt("area"),
                        resultSet.getInt("room_count"),
                        resultSet.getBoolean("bathroom_separated"),
                        resultSet.getString("description"),
                        resultSet.getString("scheme")
                    )
                )
            }
            return list
        }
        return list
    }
}