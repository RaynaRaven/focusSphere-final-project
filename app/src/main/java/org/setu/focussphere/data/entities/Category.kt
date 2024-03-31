package org.setu.focussphere.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
data class Category(

    @ColumnInfo(name = "categoryName")
    var categoryName: String,

    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0)
