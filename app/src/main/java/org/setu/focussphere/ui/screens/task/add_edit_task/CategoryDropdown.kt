package org.setu.focussphere.ui.screens.task.add_edit_task

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.setu.focussphere.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    viewModel: AddEditTaskViewModel,
    modifier: Modifier = Modifier,
) {
    val categories by viewModel.categories.collectAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(viewModel.selectedCategoryName) }
    var typedText by remember { mutableStateOf("") }

    val filteredCategories = categories.filter{
        it.categoryName.contains(typedText, ignoreCase = true)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    )
    {
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.add_edit_task_textfield_label_category)) },
            modifier = Modifier.menuAnchor(),
            placeholder = { Text(text = if (selectedCategory.isBlank()) stringResource(R.string.add_edit_task_category_hint) else selectedCategory )},
            value = typedText,
            onValueChange = {value ->
                typedText = value
                expanded = true },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            filteredCategories.forEach {category ->
                DropdownMenuItem(
                    text = { Text(category.categoryName) },
                    onClick = {
                        selectedCategory = category.categoryName
                        typedText = category.categoryName
                        expanded = false
                        viewModel.updateCategory(category.categoryId)
                    }
                )
            }
            if (typedText.isNotEmpty() && filteredCategories.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("Add $typedText as a new Category") },
                    onClick = {
                        viewModel.createCategory(typedText).let { newCategoryId ->
                            selectedCategory = typedText
                            typedText = ""
                            expanded = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryDropdownPreview(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    var expanded by remember { mutableStateOf(false)}
    var selectedCategory by remember { mutableStateOf("")}

    TextField(
        value = selectedCategory,
        onValueChange = {selectedCategory = it},
        modifier = modifier
            .clickable { expanded = !expanded },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "textfield_dropdown",
                modifier = Modifier.clickable { expanded = !expanded}
            )
        }
    )
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
    ) {
        categories.forEach { category ->
            DropdownMenuItem(
                text = { category.categoryName },
                onClick = {
                    selectedCategory = category.categoryName
                    expanded = false
                })
        }
    }
}

//fake Category data class for preview
data class Category(val categoryId: Long, val categoryName: String)

@Preview(showBackground = true)
@Composable
fun CategoryDropdownPreview() {
    val fakeCategories = listOf(
        Category(categoryId = 1L, categoryName = "Work"),
        Category(categoryId = 2L, categoryName = "Personal"),
        Category(categoryId = 3L, categoryName = "Shopping")
    )
    CategoryDropdownPreview(categories = fakeCategories)
}

