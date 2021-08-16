package net.jozoproductions.myapplication.restaurant

import net.jozoproductions.myapplication.tiles.Tile

class Restaurant {
    var restaurantType: RestaurantType? = null

    var tiles = Array(25) { arrayOfNulls<Tile>(25) }

    public fun getMaxStorage(): Int {
        var totalStorage = 0

        //Calculate every tile
        for (x in tiles.indices) {
            for (y in tiles[x].indices) {
                if (tiles[x][y] == Tile.STORAGE_INGREDIENTS) {
                    totalStorage += 50
                }
            }
        }

        return totalStorage
    }
}