package com.example.priomatrix

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.set
import kotlin.collections.toMutableMap

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

        // add to matrix
        _matrixTasks.value =
            _matrixTasks.value.toMutableMap().apply {
                val updated = (this[priority] ?: emptyList()) +
                        dragged.copy(priority = priority)
                this[priority] = updated
            }.toMap() // 🔥 FORCE NEW REF

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

}