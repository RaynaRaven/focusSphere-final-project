package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Routine(
    var title: String = "",

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0 )