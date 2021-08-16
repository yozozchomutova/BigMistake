package net.jozoproductions.myapplication.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.fragments.MenuFragment
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.somegoodenums.Ingredient

class IngredientsRVAdapter(var parentFragment: MenuFragment) : RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parentFragment.context).inflate(R.layout.viewholder_ingredient, parent, false), parentFragment)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ingredient = Player.ingredients.get(position)
        holder.arrayPosition = position

        holder.onBind()
    }

    override fun getItemCount(): Int {
        return Player.ingredients.size
    }

    class ViewHolder(var view: View, var parentFragment: MenuFragment) : RecyclerView.ViewHolder(view), View.OnClickListener {

        lateinit var ingredient: Ingredient
        var arrayPosition: Int = -1

        //Components
        lateinit var nameText: TextView
        lateinit var countText: TextView
        lateinit var icon: ImageView

        fun onBind() {
            nameText = view.findViewById(R.id.name)
            countText = view.findViewById(R.id.count)
            icon = view.findViewById(R.id.icon)

            nameText.text = ingredient.ingredientName
            countText.text = Player.ingredientsCount.get(arrayPosition).toString() + "x"
            icon.setImageDrawable(ContextCompat.getDrawable(icon.context, ingredient.drawableId))

            val imageButton: ImageButton = view.findViewById(R.id.remove)
            imageButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (!Player.RemoveIngredient(arrayPosition, 1)) {
                countText.text = Player.ingredientsCount.get(arrayPosition).toString() + "x"
            }

            parentFragment.RefreshIngredientsRecyclerView(arrayPosition)
        }

    }
}