package org.setu.focussphere.helpers

import androidx.room.TypeConverter
import org.setu.focussphere.data.enums.PriorityLevel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {

    //Room type converter for LocalDateTime Object to Long
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    //Room type converter for Long to LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
        }
    }
    
    @TypeConverter
    fun fromPriorityLevel(priorityLevel: PriorityLevel): Int {
        return priorityLevel.order
    }

    @TypeConverter
    fun toPriorityLevel(order: Int): PriorityLevel {
        return PriorityLevel.values().first { it.order == order }
    }

}