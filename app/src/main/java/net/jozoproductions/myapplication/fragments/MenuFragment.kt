package net.jozoproductions.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.recyclerviews.IngredientsRVAdapter
import net.jozoproductions.myapplication.recyclerviews.RecipesRVAdapter
import net.jozoproductions.myapplication.somegoodenums.Ingredient

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private lateinit var ingredientsRVAdapter: IngredientsRVAdapter
    private lateinit var recipesRVAdapter: RecipesRVAdapter

    private lateinit var storageInfo: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storageInfo = view.findViewById(R.id.storage_info)
        UpdateStorageInfoTextView()

        //Setup recyclerViews
        var ingredientsRV: RecyclerView = view.findViewById(R.id.ingredients_rv)
        ingredientsRVAdapter = IngredientsRVAdapter(this)
        ingredientsRV.layoutManager = LinearLayoutManager(context)
        ingredientsRV.adapter = ingredientsRVAdapter

        var recipesRV: RecyclerView = view.findViewById(R.id.recipes_rv)
        recipesRVAdapter = RecipesRVAdapter(this)
        recipesRV.layoutManager = LinearLayoutManager(context)
        recipesRV.adapter = recipesRVAdapter
    }

    public fun RefreshIngredientsRecyclerView(position: Int) {
        ingredientsRVAdapter.notifyItemChanged(position)
        UpdateStorageInfoTextView()
    }

    private fun UpdateStorageInfoTextView() {
        storageInfo.text = "Storage: " + Player.getIngredientCount() + "/" + (Player.restaurant?.getMaxStorage()
            ?: -1)
    }
}