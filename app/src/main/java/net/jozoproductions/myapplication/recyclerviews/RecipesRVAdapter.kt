package net.jozoproductions.myapplication.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.fragments.MenuFragment
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.somegoodenums.Recipe

class RecipesRVAdapter(var parentFragment: MenuFragment) : RecyclerView.Adapter<RecipesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parentFragment.context).inflate(R.layout.viewholder_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipe = Player.unlockedRecipes.get(position)
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return Player.unlockedRecipes.size
    }

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        lateinit var recipe: Recipe

        //Components
        lateinit var nameText: TextView
        lateinit var icon: ImageView

        fun onBind() {
            //Layout setup
            val tasksLayout = view.findViewById<LinearLayout>(R.id.required_tasks)
            val ingredientsLayout = view.findViewById<LinearLayout>(R.id.required_ingredients)

            nameText = view.findViewById(R.id.name)
            icon = view.findViewById(R.id.icon)

            nameText.text = recipe.recipeName
            icon.setImageDrawable(ContextCompat.getDrawable(icon.context, R.drawable.tile_food_exporter))

            for (i in recipe.ingredients.indices) {
                var ingredientIcon = ImageView(ingredientsLayout.context)
                ingredientIcon.layoutParams = ViewGroup.LayoutParams(64, 64)
                ingredientIcon.setImageDrawable(ContextCompat.getDrawable(icon.context, recipe.ingredients[i].drawableId))

                ingredientsLayout.addView(ingredientIcon)
            }

            for (i in recipe.requiredTasks.indices) {
                var taskIcon = ImageView(tasksLayout.context)
                taskIcon.layoutParams = ViewGroup.LayoutParams(64, 64)
                taskIcon.setImageDrawable(ContextCompat.getDrawable(icon.context, R.drawable.tile_food_exporter))

                tasksLayout.addView(taskIcon)
            }
        }
    }
}