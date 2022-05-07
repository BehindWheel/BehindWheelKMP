package com.egoriku.retrosheetkmm.util

class QueryConverter(
    private val smartQuery: String,
    private val smartQueryMap: Map<String, String>
) {
    /**
     * To generate final query.
     * @return [smartQuery] replaced with [smartQueryMap] (fields) and [paramMap] (values)
     */
    fun convert(): String {
        var outputQuery = smartQuery

        // Replacing keys
        for (entry in smartQueryMap) {
            outputQuery = outputQuery.replace(entry.key, entry.value)
        }
        return outputQuery
    }
}
