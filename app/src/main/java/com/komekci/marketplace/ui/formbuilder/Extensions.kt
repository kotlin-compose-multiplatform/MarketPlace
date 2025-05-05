package com.komekci.marketplace.ui.formbuilder

fun String.isNumeric(): Boolean {
    return this.toIntOrNull()?.let { true } ?: false
}