package net.jozoproductions.myapplication.player

import net.jozoproductions.myapplication.restaurant.Restaurant
import net.jozoproductions.myapplication.somegoodenums.Ingredient
import net.jozoproductions.myapplication.somegoodenums.Recipe

class Player {

    companion object {

        var money: Float? = 100000f
        var restaurant: Restaurant? = null

        var ingredients = mutableListOf<Ingredient>()
        var ingredientsCount = mutableListOf<Int>()
        var unlockedRecipes = mutableListOf(Recipe.FRIES)

        fun AddIngredient(ingredient: Ingredient) {
            val ingredientsSize = ingredients.size

            var newIngredient = true
            for (ingredientIndex in 0 until ingredientsSize) {
                if (ingredients.get(ingredientIndex) == ingredient) {
                    ingredientsCount[ingredientIndex] += 1
                    newIngredient = false
                }
            }

            if (newIngredient) {
                ingredients.add(ingredient)
                ingredientsCount.add(1)
            }
        }

        //Boolean = If ingredient was removed
        fun RemoveIngredient(arrayPosition: Int, count: Int) : Boolean {
            ingredientsCount[arrayPosition] -= count

            if (ingredientsCount[arrayPosition] <= 0) {
                ingredients.removeAt(arrayPosition)
                ingredientsCount.removeAt(arrayPosition)

                return true
            }

            return false
        }

        fun AddRecipe(recipe: Recipe) {
            unlockedRecipes.add(recipe)
        }

        fun getIngredientCount(): Int {
            var ingredientCount = 0

            for (i in ingredients.indices) {
                ingredientCount += ingredientsCount[i]
            }

            return ingredientCount
        }
    }

}