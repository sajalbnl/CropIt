package com.example.cropit

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    object Home: BottomBarScreen(
        route = "home",
        title = "home",
        icon = R.drawable.home,
        icon_focused = R.drawable.home
    )

//    object Recent : BottomBarScreen(
//        "recent",
//        title = "Recent",
//        icon = R.drawable.recent,
//        icon_focused = R.drawable.recent
//    )
}