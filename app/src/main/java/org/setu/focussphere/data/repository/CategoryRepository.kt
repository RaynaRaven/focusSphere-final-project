package org.setu.focussphere.data.repository

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Category

interface CategoryRepository {

    suspend fun insertCategory(category: Category) : Long
    fun getAllCategories() : Flow<List<Category>>
}