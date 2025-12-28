package com.example.priomatrix.ui

sealed class FilterOption(val title: String) {

    sealed class Completion(title: String) : FilterOption(title) {
        object All : Completion("All tasks")
        object Completed : Completion("Completed")
        object Incomplete : Completion("Incomplete")
    }

    sealed class Name(title: String) : FilterOption(title) {
        object AtoZ : Name("Name (A → Z)")
        object ZtoA : Name("Name (Z → A)")
    }
}