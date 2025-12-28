package com.example.priomatrix.ui

import com.example.priomatrix.R

sealed class QuickActionItem(
    val id: Int,
    val name: String,
    val icon: Int
) {
    object SortBy : QuickActionItem(
        id = 1,
        name = "Sort By",
        icon = R.drawable.sort
    )

    object FilterBy : QuickActionItem(
        id = 2,
        name = "Filter By",
        icon = R.drawable.filter
    )
}