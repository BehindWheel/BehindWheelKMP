package com.egoriku.grodnoroads.extension

fun <T> List<T>.second(): T {
    if (size < 2)
        throw NoSuchElementException("List size < 2.")
    return this[1]
}

fun <T> List<T>.third(): T {
    if (size < 3)
        throw NoSuchElementException("List size < 3.")
    return this[2]
}