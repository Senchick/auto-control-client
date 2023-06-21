package com.company.autocontrol.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <S, reified T : S> MutableStateFlow<S>.updateWithState(f: (T) -> S) {
    if (value is T) {
        update {
            f(it as T)
        }
    }
}
