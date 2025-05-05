package com.komekci.marketplace.core.locale

import androidx.compose.runtime.Composable
import com.komekci.marketplace.state.LocalSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.reflect.KProperty1

@Suppress("UNCHECKED_CAST")
suspend inline fun <reified R> readInstancePropertyWithContext(
    instance: Any, propertyName: String
): R = withContext(Dispatchers.Default) {
    val property = instance::class.members
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force an invalid cast exception if an incorrect type here
    property.get(instance) as R
}

suspend inline fun <reified R> getLanguageValueWithContext(
    instance: Any, property: String, language: String
): R = readInstancePropertyWithContext(instance,"${property}_${language}")


fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.members
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}

fun <R> getLanguageValue(instance: Any, property: String, language: String, prefix: String = "_"): R {
    try {
        return readInstanceProperty(instance,"${property}${prefix}${language}")
    } catch (ex: Exception) {
        return readInstanceProperty(instance,"${property}${prefix}${Locales.TK}")
    }
}

@Composable
fun translateValue(instance: Any, property: String, prefix: String = "_"): String {

        val appSettingsState = LocalSettings.current
        return getLanguageValue(instance,property,appSettingsState.languageTag,prefix)

}