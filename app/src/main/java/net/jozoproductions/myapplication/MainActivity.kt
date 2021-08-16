package net.jozoproductions.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import net.jozoproductions.myapplication.fragments.*
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.restaurant.Restaurant
import net.jozoproductions.myapplication.restaurant.RestaurantType
import net.jozoproductions.myapplication.tiles.Tile


class MainActivity : AppCompatActivity() {

    companion object {
        public var SCREEN_WIDTH: Float = 0f
        public var SCREEN_HEIGHT: Float = 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        SCREEN_WIDTH = displayMetrics.widthPixels.toFloat()
        SCREEN_HEIGHT = displayMetrics.heightPixels.toFloat()

        setContentView(R.layout.activity_main)

        //Main Tab Initialize
        val mainTabListener = MainTabListener(this)
        val mainTab: TabLayout = findViewById(R.id.mainTabSelection)

        mainTab.addOnTabSelectedListener(mainTabListener)

        //LoadPlayer
        Player.restaurant = Restaurant()
        Player.restaurant!!.restaurantType = RestaurantType.LEVEL_1
        Load()
    }

    public fun replaceFragments(fragment: Fragment, animEnabled: Boolean) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        if (animEnabled)
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        fragmentTransaction.replace(R.id.mainFragment, fragment, "h")
        fragmentTransaction.addToBackStack("h")
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        Save()
        super.onDestroy()
    }

    private fun Save() {
        val spEditor = getSharedPreferences("kotlinsrestaurant", Context.MODE_PRIVATE).edit()

        Player.money?.let { spEditor.putFloat("money", it) }

        //Save restaurant tiles
        var restaurantTilesSet = ArrayList<String>()

        for (x in Player.restaurant!!.tiles.indices) {
            for (y in Player.restaurant!!.tiles[x].indices) {
                Player.restaurant!!.tiles[x][y]?.let { restaurantTilesSet.add(it.name) }
            }
        }

        println("Serialized: " + ObjectSerializer.serialize(restaurantTilesSet))
        spEditor.putString("restaurant_tiles", ObjectSerializer.serialize(restaurantTilesSet))

        spEditor.commit()
    }

    private fun Load() {
        val sharedPreferences = getSharedPreferences("kotlinsrestaurant", Context.MODE_PRIVATE)

        //Player.money = sharedPreferences.getFloat("money", 100f)

        //Load restaurant tiles
        val tileMapWidth = Player.restaurant?.restaurantType?.width ?: 0
        val tileMapHeight = Player.restaurant?.restaurantType?.height ?: 0
        val restaurantTilesSet = ObjectSerializer.deserialize(sharedPreferences.getString("restaurant_tiles", ObjectSerializer.serialize(GetAIRTileMap()))) as ArrayList<String>

        println("Elements: " + restaurantTilesSet.size)
        for (x in 0..tileMapWidth-1-2) {
            for (y in 0..tileMapHeight-1-2) {
                println("X: $x Y: $y | | " + restaurantTilesSet[y + x * (tileMapWidth-2)])
                Player.restaurant!!.tiles[x][y] =
                    Tile.valueOf(restaurantTilesSet[y + x * (tileMapWidth-2)])
            }
        }
    }

    private class MainTabListener(private var mainActivity: MainActivity?) : TabLayout.OnTabSelectedListener {

        override fun onTabSelected(tab: TabLayout.Tab?) {
            val tabId = tab?.position

            //Decide which fragment should be shown
            if (tabId == 0)
            {
                val replacingFragment = CatchIngredientsFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
            else if (tabId == 1)
            {
                val replacingFragment = RestaurantFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
            else if (tabId == 2)
            {
                val replacingFragment = EmployeesFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
            else if (tabId == 3)
            {
                val replacingFragment = UpgradesFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
            else if (tabId == 4)
            {
                val replacingFragment = SettingsFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
            else if (tabId == 5)
            {
                val replacingFragment = MenuFragment();
                mainActivity?.replaceFragments(replacingFragment, true)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}

    }

    private fun GetAIRTileMap(): ArrayList<String> {
        val arrayList = ArrayList<String>()
        println("ENGAGED")

        //Gas with Air
        val tileMapWidth = Player.restaurant?.restaurantType?.width ?: 0
        val tileMapHeight = Player.restaurant?.restaurantType?.height ?: 0

        for (x in 1..tileMapWidth-2) {
            for (y in 1..tileMapHeight-2) {
                arrayList.add(Tile.AIR.name)
            }
        }

        return arrayList
    }
}