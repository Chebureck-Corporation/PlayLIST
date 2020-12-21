package com.chebureck.playlist.utils

object ListsUtils {
    fun <T1, T2> and(lists: List<T1>, equalTargetGetter: (T1) -> List<T2>): List<T2> {
        val startElements = equalTargetGetter(lists[0])
        return startElements.filter { element ->
            lists.all { equalTargetGetter(it).contains(element) }
        }
    }

    fun <T1, T2> or(lists: List<T1>, equalTargetGetter: (T1) -> List<T2>): List<T2> {
        val result = mutableListOf<T2>()
        lists.forEach { element ->
            result.addAll(equalTargetGetter(element).filter { !result.contains(it) })
        }
        return result
    }

    fun <T1, T2> xor(lists: List<T1>, equalTargetGetter: (T1) -> List<T2>): List<T2> {
        val result = mutableListOf<T2>()
        for (tempElements in lists) {
            for (tempElement in equalTargetGetter(tempElements)) {
                if (tempElement in result) {
                    result.remove(tempElement)
                } else {
                    result.add(tempElement)
                }
            }
        }
        return result
    }
}