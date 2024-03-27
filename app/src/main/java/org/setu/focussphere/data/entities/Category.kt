package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class Category(

    var categoryName: String,

    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0)
