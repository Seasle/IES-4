package model

data class Modules(
    var powerSupply: MutableList<HashMap<String, String>>? = null,
    var waterSupply: MutableList<HashMap<String, String>>? = null,
    var climateSupply: MutableList<HashMap<String, String>>? = null,
    var equipmentSupply: MutableList<HashMap<String, String>>? = null,
    var householdApplianceSupply: MutableList<HashMap<String, String>>? = null,
    var videoMonitoringSupply: MutableList<HashMap<String, String>>? = null,
    var securitySupply: MutableList<HashMap<String, String>>? = null,
    var controlSupply: MutableList<HashMap<String, String>>? = null
)