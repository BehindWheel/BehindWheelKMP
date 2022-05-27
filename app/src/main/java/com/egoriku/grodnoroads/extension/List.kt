package com.egoriku.grodnoroads.extension

fun <T> List<T>.second(): T {
    if (size < 2)
        throw NoSuchElementException("List is empty.")
    return this[1]
}