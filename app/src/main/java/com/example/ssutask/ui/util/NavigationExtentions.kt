package com.example.ssutask.ui.util

import android.os.Bundle

class NavigationConstants {
    companion object {
        const val bookIdKey = "bookId"
    }
}

fun getBookIdFromArgs(arguments: Bundle?): Long {
    return arguments?.getLong(NavigationConstants.bookIdKey)!!;
}

fun addBookIdArg(bookId: Long): Bundle {
    val bundle = Bundle()
    bundle.putLong(NavigationConstants.bookIdKey, bookId)

    return bundle
}

fun hasKeyInArgs(arguments: Bundle?): Boolean {
    return arguments?.containsKey("bookId")!!
}