import model.House
import model.Modules
import java.sql.Connection
import java.sql.DriverManager
import kotlin.collections.HashMap

object Database {
    private var connection: Connection?

    init {
        val path = javaClass.getResource("/raw/smart-house.db")
        connection = DriverManager.getConnection("jdbc:sqlite::resource:$path")
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

                for(item in columnNames){
                    entry[item] = resultSet.getObject(item)
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