package com.e.mbulak.utils

import com.squareup.moshi.*
import java.lang.Exception
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*



class CustomMoshiAdapter : JsonAdapter<Date>() {

    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e : Exception) {
            null
        }
    }

    companion object {
        const val SERVER_FORMAT = ("dd.mm.yyyy") // define your server format here
    }

}