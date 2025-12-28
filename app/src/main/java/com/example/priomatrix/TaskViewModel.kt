package com.example.priomatrix

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.example.priomatrix.ui.FilterOption
import com.example.priomatrix.ui.SortOption
import com.example.priomatrix.ui.TaskStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.orEmpty
import kotlin.collections.sortedBy
import kotlin.collections.sortedByDescending

class TaskViewModel : ViewModel() {

    companion object {
        const val TAG = "TaskViewModel"
    }


    private val _backlogTasks = MutableStateFlow(list)
    val backlogTasks = _backlogTasks.asStateFlow()

    private val _matrixTasks =
        MutableStateFlow<Map<Priority, List<Task>>>(
            mapOf(
                PRIORITY_ONE to emptyList(),
                PRIORITY_TWO to emptyList(),
                PRIORITY_THREE to emptyList(),
                PRIORITY_FOUR to emptyList()
            )
        )
    val matrixTasks = _matrixTasks.asStateFlow()

    private val _dragState = MutableStateFlow(DragState())
    val dragState = _dragState.asStateFlow()

    private val _isFilterPopupOpen = MutableStateFlow(false)
    val isFilterPopupOpen = _isFilterPopupOpen.asStateFlow()

    private val _isSortPopupOpen = MutableStateFlow(false)
    val isSortPopupOpen = _isSortPopupOpen.asStateFlow()

    fun setSortPopupStateOpen(){
        _isSortPopupOpen.value = true
    }

    fun setSortPopupStateClose(){
        _isSortPopupOpen.value = false
    }

    fun setFilterPopupStateOpen(){
        _isFilterPopupOpen.value = true
    }
    fun setFilterPopupStateClose(){
        _isFilterPopupOpen.value = false
    }

    private val _completionFilter =
        MutableStateFlow<FilterOption.Completion>(FilterOption.Completion.All)

    private val _nameFilter =
        MutableStateFlow<FilterOption.Name>(FilterOption.Name.AtoZ)

    private val _sortOption =
        MutableStateFlow<SortOption>(SortOption.Relevance)

    val completionFilter = _completionFilter.asStateFlow()
    val nameFilter = _nameFilter.asStateFlow()
    val sortOption = _sortOption.asStateFlow()

    fun setCompletionFilter(option: FilterOption.Completion) {
        _completionFilter.value = option
    }

    fun setNameFilter(option: FilterOption.Name) {
        _nameFilter.value = option
    }

    fun setSortOption(option: SortOption) {
        _sortOption.value = option
    }

    fun startDrag(task: Task, offset: Offset) {
        _dragState.value = DragState(task, offset, true)
    }

    fun updateDrag(offset: Offset) {
        _dragState.value = _dragState.value.copy(position = offset)
    }

    fun endDrag() {
        _dragState.value = _dragState.value.copy(isDragging = false)
    }

    fun dropInto(priority: Priority) {
        val dragged = _dragState.value.task ?: return

        // remove from backlog
        _backlogTasks.value =
            _backlogTasks.value.filterNot { it.id == dragged.id }

        // add to matrix (newest first)
        _matrixTasks.value =
            _matrixTasks.value.toMutableMap().apply {
                val updated =
                    listOf(dragged.copy(priority = priority)) +
                            (this[priority] ?: emptyList())
                this[priority] = updated
            }.toMap()

        _dragState.value = DragState()
    }

    fun rollbackTask(task: Task) {

        _matrixTasks.value =
            _matrixTasks.value.toMutableMap().apply {
                this[task.priority] =
                    (this[task.priority] ?: emptyList())
                        .filterNot { it.id == task.id }
            }.toMap()

        _backlogTasks.value += task.copy(priority = PRIORITY_NONE)

    }

    fun filteredAndSortedTasksForPriority(
        priority: Priority,
        matrixTasks: Map<Priority, List<Task>>,
        completion: FilterOption.Completion,
        nameFilter: FilterOption.Name,
        sortOption: SortOption
    ): List<Task> {

        val tasks = matrixTasks[priority].orEmpty()

        // ---- 1. Completion filter ----
        val completionFiltered = tasks.filter { task ->
            when (completion) {
                FilterOption.Completion.All -> true
                FilterOption.Completion.Completed -> task.isCompleted
                FilterOption.Completion.Incomplete -> !task.isCompleted
            }
        }

        // ---- 2. Name filter (A–Z / Z–A) ----
        val nameFiltered = when (nameFilter) {
            FilterOption.Name.AtoZ ->
                completionFiltered.sortedBy { it.title.lowercase() }

            FilterOption.Name.ZtoA ->
                completionFiltered.sortedByDescending { it.title.lowercase() }
        }

        // ---- 3. Sort (final ordering) ----
        return when (sortOption) {

            SortOption.Relevance ->
                nameFiltered

            SortOption.Date ->
                nameFiltered.sortedByDescending { it.createdAt }

            SortOption.Name ->
                nameFiltered.sortedBy { it.title.lowercase() }
        }
    }


}