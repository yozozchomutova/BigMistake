package net.jozoproductions.myapplication.somegoodenums


enum class Recipe(var recipeName: String, var ingredients: Array<Ingredient>, var requiredTasks: Array<Task>) {
    FRIES("Fries",
        arrayOf(Ingredient.POTATO, Ingredient.POTATO),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.CUT, Task.CUT, Task.COOK)),
    COOKED_POTATOES("Cooked potatoes",
        arrayOf(Ingredient.POTATO, Ingredient.POTATO, Ingredient.POTATO, Ingredient.POTATO, Ingredient.OIL),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.CUT, Task.CUT, Task.CUT, Task.COOK)),
    COOKED_POTATOES_WITH_MEAT("Cooked potatoes with Meat",
        arrayOf(Ingredient.POTATO, Ingredient.POTATO, Ingredient.POTATO, Ingredient.POTATO, Ingredient.MEAT, Ingredient.OIL),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.TAKE_FROM_FRIDGE, Task.CUT, Task.CUT, Task.CUT, Task.COOK)),
    COOKED_VEGETABLES("Cooked vegetables",
        arrayOf(Ingredient.TOMATO, Ingredient.CABBAGE, Ingredient.CARROT, Ingredient.PEA, Ingredient.OIL),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.TAKE_FROM_FRIDGE, Task.CUT, Task.CUT, Task.CUT, Task.COOK)),
    SPAGHETTI("Spaghetti",
        arrayOf(Ingredient.PASTA, Ingredient.TOMATO),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.TAKE_FROM_FRIDGE, Task.CUT, Task.COOK)),
    TOMATO_SOUP("Tomato soup",
        arrayOf(Ingredient.PASTA, Ingredient.TOMATO),
        arrayOf(Task.TAKE_FROM_STORAGE, Task.TAKE_FROM_FRIDGE, Task.CUT, Task.CUT, Task.COOK)),
    ;
}