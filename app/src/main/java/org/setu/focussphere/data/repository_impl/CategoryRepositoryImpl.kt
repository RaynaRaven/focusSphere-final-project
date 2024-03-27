package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.CategoryDao
import org.setu.focussphere.data.entities.Category
import org.setu.focussphere.data.repository.CategoryRepository

class CategoryRepositoryImpl (
    private val dao: CategoryDao
) : CategoryRepository {
    override suspend fun insertCategory(category: Category): Long {
       return dao.insertCategory(category)
    }

    override fun getAllCategories(): Flow<List<Category>> {
       return dao.getAllCategories()
    }
}