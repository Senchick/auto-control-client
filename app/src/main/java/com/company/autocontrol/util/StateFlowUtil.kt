package com.company.autocontrol.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/*inline fun <S, reified T : S> MutableStateFlow<S>.updateWithState(f: (T) -> S) {
    if (value is T) {
        update {
            f(it as T)
        }
    }
}*/

suspend inline fun <S, reified T : S> MutableStateFlow<S>.updateWithState(
    context: CoroutineContext = EmptyCoroutineContext,
    crossinline f: suspend (T) -> S
) = withContext(
    context
) {
    while (true) {
        val current = value
        if (current is T) {
            val updated = f(current)
            if (compareAndSet(current, updated)) {
                break
            }
        } else {
            break
        }
    }
}
