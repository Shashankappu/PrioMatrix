package com.example.priomatrix.ui

sealed class SortOption(val title: String) {
    object Relevance : SortOption("Relevance")
    object Date : SortOption("Date")
    object Name : SortOption("Name")
}