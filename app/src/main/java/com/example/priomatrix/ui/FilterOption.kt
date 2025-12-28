package com.example.priomatrix.ui

sealed class FilterOption(val title: String) {

    sealed class Completion(title: String) : FilterOption(title) {
        object All : Completion("All")
        object Completed : Completion("Completed")
        object Incomplete : Completion("Incomplete")
    }

    sealed class Name(title: String) : FilterOption(title) {
        object AtoZ : Name("(A → Z)")
        object ZtoA : Name("(Z → A)")
    }
}