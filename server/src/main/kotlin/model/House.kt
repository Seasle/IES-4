package model

data class House(
        val id: Int,
        val area: Int,
        val roomCount: Int,
        val bathroomSeparated: Boolean,
        val description: String,
        val scheme: String
)