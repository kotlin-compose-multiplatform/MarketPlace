package com.komekci.marketplace.core.utils

class DateHelper {
    companion object {
        fun convertOnlyDate(dateString: String): String {
            try {
                val s = dateString.split("T")
                return s[0]
            } catch (ex: Exception) {
                ex.printStackTrace()
                return dateString
            }
        }

        fun convertOnlyTime(dateString: String): String {
            try {
                val s = dateString.split("T")[1].split(":")
                return "${s[0]}:${s[1]}"
            } catch (ex: Exception) {
                ex.printStackTrace()
                return dateString
            }
        }

        fun convertDateAndTime(dateString: String): String {
            return convertOnlyDate(dateString).plus(" ").plus(convertOnlyTime(dateString))
        }
    }
}