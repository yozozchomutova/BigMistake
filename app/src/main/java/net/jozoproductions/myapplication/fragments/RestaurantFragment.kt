package net.jozoproductions.myapplication.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import net.jozoproductions.myapplication.MainActivity
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.restaurant.Restaurant
import net.jozoproductions.myapplication.tiles.Tile
import java.lang.Exception

class RestaurantFragment : Fragment(R.layout.fragment_restaurant), View.OnClickListener {

    private lateinit var fragmentView: View

    //Views
    private lateinit var buildModeLayout: ConstraintLayout
    private lateinit var placableTileRoot: ConstraintLayout

    private lateinit var playerMoneyText: TextView

    //BUILD MODE
    private lateinit var lastSelectedTile: ImageView
    private lateinit var selectedPlaceableTile: Tile.SelectedTile
    private lateinit var selectedTile: Tile
    private var selectedTileIndex: Int = 0

    //Build mode views
    private lateinit var tileName: TextView
    private lateinit var tilePrice: TextView

    private lateinit var tileIcon: ImageView

    private lateinit var tileMoveLeft: ImageButton
    private lateinit var tileMoveRight: ImageButton

    private lateinit var tileBuy: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildModeLayout = view.findViewById(R.id.build_mode_panel)

        playerMoneyText = view.findViewById(R.id.money_text)

        //Build mode views initialize
        tileName = view.findViewById(R.id.tile_name)
        tilePrice = view.findViewById(R.id.build_price)
        tileIcon = view.findViewById(R.id.tile_icon)

        tileMoveLeft = view.findViewById(R.id.item_left)
        tileMoveRight = view.findViewById(R.id.item_right)

        tileBuy = view.findViewById(R.id.tile_buy)

        //Button listener
        view.findViewById<ImageButton>(R.id.enter_build_mode).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.exit_build_mode).setOnClickListener(this)
        tileBuy.setOnClickListener(this)
        tileMoveLeft.setOnClickListener(this)
        tileMoveRight.setOnClickListener(this)

        //Generate Restaurant visuals
        fragmentView = view
        GenerateRestaurant(view)
        UpdatePlayerMoney()
    }

    fun GenerateRestaurant(view: View) {
        val tiles: ConstraintLayout = view.findViewById(R.id.tiles)
        tiles.removeAllViews()

        placableTileRoot = view.findViewById(R.id.placable_tile_graph)

        val tileMapWidth = Player.restaurant?.restaurantType?.width ?: 0
        val tileMapHeight = Player.restaurant?.restaurantType?.height ?: 0

        val tileWidth = MainActivity.SCREEN_WIDTH / tileMapWidth
        val tileHeight = tileWidth

        //Render
        for (x in 0..tileMapWidth-1) {
            for (y in 0..tileMapHeight-1) {
                val imageView = ImageView(view.context)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.layoutParams = ConstraintLayout.LayoutParams(tileWidth.toInt(), tileHeight.toInt())
                imageView.x = x * tileWidth
                imageView.y = y * tileHeight

                //Is it place for food export
                if (x == 1 && y == 0) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tile_food_exporter))
                }

                //Is it wall rounded?
                else if (x == 0 && y == 0) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_round))
                    imageView.rotation = -90f
                } else if (x == tileMapWidth-1 && y == 0) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_round))
                    imageView.rotation = 0f
                } else if (x == 0 && y == tileMapHeight-1) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_round))
                    imageView.rotation = 180f
                } else if (x == tileMapWidth-1 && y == tileMapHeight-1) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_round))
                    imageView.rotation = 90f
                } else {
                    //Is it then wall straight?
                    if (x == 0) { //LEFT
                        imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_straight))
                        imageView.rotation = -90f
                    } else if (y == 0) { //TOP
                        imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_straight))
                        imageView.rotation = 0f
                    } else if (y == tileMapHeight-1) { //RIGHT
                        imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_straight))
                        imageView.rotation = 180f
                    } else if (x == tileMapWidth-1) { //BOTTOM
                        imageView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.wall_straight))
                        imageView.rotation = 90f
                    } else { //Then it must be something normal
                        //Idk what the hell is this and what my android studio just did with this piece of code, but i hope, it will work
                        val tileDrawableId: Int? = Player.restaurant?.tiles?.get(x)?.get(y)?.drawableId
                        imageView.setImageDrawable(tileDrawableId?.let {
                            ContextCompat.getDrawable(view.context,
                                it
                            )
                        })

                        //Add placeable tiles, ofc
                        var placableTile = ImageView(context)
                        placableTile.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.place_indicator) })
                        placableTile.scaleType = ImageView.ScaleType.FIT_XY
                        placableTile.layoutParams = ConstraintLayout.LayoutParams(tileWidth.toInt(), tileHeight.toInt())
                        placableTile.x = x * tileWidth
                        placableTile.y = y * tileHeight

                        placableTileRoot.addView(placableTile)

                        //And listener
                        placableTile.setOnClickListener {
                            try {
                                lastSelectedTile.setColorFilter(Color.BLACK)
                            } catch (e: UninitializedPropertyAccessException) {}

                            selectedPlaceableTile = Tile.SelectedTile(x, y, placableTile)
                            placableTile.setColorFilter(Color.GREEN)
                            lastSelectedTile = placableTile
                        }
                    }
                }

                //Add to our, lonely, grzeczny, root ConstraintLayout
                tiles.addView(imageView)
            }
        }

        //Floor size + position correction
        val floor = view.findViewById<ImageView>(R.id.floor)
        floor.layoutParams = ConstraintLayout.LayoutParams(MainActivity.SCREEN_WIDTH.toInt(), MainActivity.SCREEN_WIDTH.toInt())
        floor.x = 0f
        floor.y = 0f
    }

    override fun onClick(v: View?) {
        val viewId = v?.id

        if (viewId == R.id.enter_build_mode) {
            placableTileRoot.visibility = View.VISIBLE
            buildModeLayout.visibility = View.VISIBLE

            //Default settings
            selectedTile = Tile.AIR
            selectedTileIndex = 0
            UpdateBuildModeInfo()
        } else if (viewId == R.id.exit_build_mode) {
            placableTileRoot.visibility = View.INVISIBLE
            buildModeLayout.visibility = View.INVISIBLE
        } else if (viewId == R.id.tile_buy) {
            if (Player.money!! >= selectedTile.buildPrice) {
                Player.money = Player.money!! - selectedTile.buildPrice.toFloat()

                Player.restaurant?.tiles?.get(selectedPlaceableTile.x)?.set(selectedPlaceableTile.y, selectedTile)
                GenerateRestaurant(fragmentView) //Regenerate map
                UpdatePlayerMoney()
            }
        } else if (viewId == R.id.item_left) {
            selectedTileIndex--

            if(selectedTileIndex == -1) {
                selectedTileIndex = Tile.values().size-1
            }

            selectedTile = Tile.values()[selectedTileIndex]
            UpdateBuildModeInfo()
        } else if (viewId == R.id.item_right) {
            selectedTileIndex++

            if(selectedTileIndex == Tile.values().size) {
                selectedTileIndex = 0
            }

            selectedTile = Tile.values()[selectedTileIndex]
            UpdateBuildModeInfo()
        }
    }

    private fun UpdateBuildModeInfo() {
        tileName.text = selectedTile.name
        tilePrice.text = selectedTile.buildPrice.toString()
        tileIcon.setImageDrawable(context?.let { ContextCompat.getDrawable(it, selectedTile.drawableId) })
    }

    private fun UpdatePlayerMoney() {
        playerMoneyText.text = Player.money.toString()
    }
}