package net.jozoproductions.myapplication.restaurant

enum class RestaurantType(var width: Int, var height: Int, var baseVisitorsCapacity: Short) {
    LEVEL_1(5, 5, 8),
    LEVEL_2(8, 8, 16),
    LEVEL_3(11, 11, 32),
    LEVEL_4(13, 13, 64),
    LEVEL_5(15, 15, 100);
}