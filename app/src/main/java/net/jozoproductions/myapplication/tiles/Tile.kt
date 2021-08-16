package net.jozoproductions.myapplication.tiles

import android.widget.ImageView
import net.jozoproductions.myapplication.R

enum class Tile(var drawableId: Int, var buildPrice: Int) {
    AIR(R.drawable.air, 0),
    STORAGE_INGREDIENTS(R.drawable.tile_storage_ingredients, 20),
    STORAGE_CUTLERY(R.drawable.tile_storage_cutlery, 20),
    CUTTING_TABLE(R.drawable.tile_cutting_table, 20),
    COOKER(R.drawable.tile_cooker, 70),
    FRIDGE(R.drawable.tile_fridge, 40),
    SINK(R.drawable.tile_sink, 50),
    TRASH_CAN(R.drawable.tile_trash_can, 10),
    FIRE_EXHAUSTER(R.drawable.tile_fire_exhauster, 10);

    public class SelectedTile(var x: Int, var y: Int, var tile: ImageView) {
    }
}