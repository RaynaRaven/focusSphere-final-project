package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Category


@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category) : Long

    @Query("SELECT * FROM category")
    fun getAllCategories() : Flow<List<Category>>

    @Query("SELECT * FROM category WHERE categoryId = :categoryId")
    suspend fun getCategoryById(categoryId: Long?): Category?

}