package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.company.autocontrol.ui.state.AdminState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor() : ViewModel() {
    private val _adminState = MutableStateFlow<AdminState>(AdminState.Idle)
    val adminState: StateFlow<AdminState> = _adminState
}