package com.komekci.marketplace.core.utils

class PhoneNumber {
    companion object {
        fun prettierPhone(phone: String): String {
            var result = phone.trim()
            if(phone.startsWith("+").not()) {
                result = "+$phone"
            }
            return result
        }
        fun removeExtra(phone: String?): String {
            return phone?.replace("+","")?.trim()?:""
        }
    }
}