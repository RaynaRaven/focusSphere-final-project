package org.setu.focussphere.helpers

import org.junit.Assert.assertEquals
import org.junit.Test
import org.setu.focussphere.data.enums.PriorityLevel
import org.setu.focussphere.util.Converters

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun test_PriorityLevelConversion() {
        val original = PriorityLevel.HIGH
        val converted = converters.fromPriorityLevel(original)
        val result = converters.toPriorityLevel(converted)
        assertEquals(original, result)
    }

}