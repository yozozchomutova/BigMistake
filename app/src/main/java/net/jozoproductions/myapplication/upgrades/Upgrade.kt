package net.jozoproductions.myapplication.upgrades

import net.jozoproductions.myapplication.R

enum class Upgrade(var upgradeName: String, var upgradeDescription: String, var drawableId: Int, var upgradeLevel: Int, var baseEffect: Float, var basePrice: Float, var effectIncrease: Float, var priceIncrease: Float) {
    CATCHING_FALL_SPEED("Fall speed", "Falling of ingredients will be faster.", R.drawable.upgrade_fall_speed, 0, 1f, 100f,  1.05f, 50f),
    CATCHING_FALL_COOLDOWN("Fall cooldown", "More ingredients will spawn.", R.drawable.upgrade_fall_cooldown, 0,300f, 100f, -5f, 100f),

    EMPLOYEE_MOVEMENT_SPEED("Movement speed", "Employees will be faster.", R.drawable.upgrade_employee_movement_speed, 0,1f, 70f, 1.08f, 100f),
    EMPLOYEE_FASTER_CUTTING("Faster cutting",  "Employees will faster cut.", R.drawable.upgrade_employee_faster_cutting, 0, 1f, 300f, 1.08f, 120f),
    EMPLOYEE_FASTER_COOKING("Faster cooking", "Employees will faster cook.", R.drawable.upgrade_employee_faster_cooking, 0, 1f, 300f, 1.08f, 120f),
    BUILDING_MORE_STORAGE("More storage", "More space for storing ingredients.", R.drawable.upgrade_building_more_storage, 0, 30f, 70f, 10f, 50f); //Storage per 1 storage tile

    public fun CalculateEffect() : Float {
        return baseEffect + (effectIncrease * upgradeLevel)
    }
}