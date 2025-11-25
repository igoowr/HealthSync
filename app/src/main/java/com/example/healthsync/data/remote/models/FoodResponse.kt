package com.example.healthsync.data.remote.models

data class Food(
    val foodId: String,
    val label: String,
    val nutrients: Nutrients,
    val image: String?
)

data class ParsedFood(
    val food: Food
)

data class Hint(
    val food: Food
)

data class Nutrients(
    val ENERC_KCAL: Double, // Calories
    val PROCNT: Double,    // Protein
    val CHOCDF: Double,    // Carbs
    val FAT: Double        // Fat
)

data class FoodResponse(
    val parsed: List<ParsedFood>,
    val hints: List<Hint>
)
