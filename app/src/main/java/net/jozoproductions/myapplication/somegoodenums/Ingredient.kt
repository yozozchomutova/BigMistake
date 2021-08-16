package net.jozoproductions.myapplication.somegoodenums

import net.jozoproductions.myapplication.R

enum class Ingredient(var ingredientName: String, var drawableId: Int) {
    TOAST("Toast", R.drawable.ing_toast),
    OIL("Oil", R.drawable.ing_oil),
    EGG("Egg", R.drawable.ing_egg),
    MILK("Milk", R.drawable.ing_milk),
    CABBAGE("Cabbage", R.drawable.ing_cabbage),
    TOMATO("Tomato", R.drawable.ing_tomato),
    PASTA("Pasta", R.drawable.ing_pasta),
    POTATO("Potato", R.drawable.ing_potato),
    STEAK("Steak", R.drawable.ing_steak),
    MEAT("Meat", R.drawable.ing_meat),
    SALMON("Salmon", R.drawable.ing_salmon),
    RICE("Rice", R.drawable.ing_rice),
    PEA("Pea", R.drawable.ing_pea),
    CARROT("Carrot", R.drawable.ing_carrot),
    ;
}