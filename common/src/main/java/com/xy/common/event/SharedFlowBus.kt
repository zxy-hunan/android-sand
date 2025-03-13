
import FlowBusTag
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * https://juejin.cn/post/7028067962200260615/
 *
 */
object SharedFlowBus {
    private var events = ConcurrentHashMap<FlowBusTag, MutableSharedFlow<Any>>()
    private var stickyEvents = ConcurrentHashMap<FlowBusTag, MutableSharedFlow<Any>>()

    private fun <T> with(key: FlowBusTag): MutableSharedFlow<T> {
        if (!events.containsKey(key)) {
            events[key] = MutableSharedFlow(0, 1, BufferOverflow.DROP_OLDEST)
        }
        return events[key] as MutableSharedFlow<T>
    }

    fun <T> post(key: FlowBusTag, value: T): Boolean = with<T>(key).tryEmit(value)

    private fun <T> withSticky(key: FlowBusTag): MutableSharedFlow<T> {
        if (!stickyEvents.containsKey(key)) {
            stickyEvents[key] = MutableSharedFlow(1, 1, BufferOverflow.DROP_OLDEST)
        }
        return stickyEvents[key] as MutableSharedFlow<T>
    }

    fun <T> postSticky(key: FlowBusTag, value: T): Boolean = withSticky<T>(key).tryEmit(value)

    private fun <T> on(key: FlowBusTag): SharedFlow<T> {
        return with<T>(key).asSharedFlow()
    }

    fun <T> observer(key: FlowBusTag, lifecycle: Lifecycle?, listen: (T) -> Unit) {
        lifecycle ?: return
        on<T>(key).collectWithLifecycle(lifecycle) {
            listen.invoke(it)
        }
    }

    private fun <T> onSticky(key: FlowBusTag): SharedFlow<T> {
        return withSticky<T>(key).asSharedFlow()
    }

    fun <T> observerSticky(key: FlowBusTag, lifecycle: Lifecycle?, listen: (T) -> Unit) {
        lifecycle ?: return
        onSticky<T>(key).collectWithLifecycle(lifecycle) {
            listen.invoke(it)
        }
    }

}

/**
 * 页面上解决Flow多层嵌套以及内存泄露
 * lifecycleScope 是一个绑定到 Android 组件生命周期的 CoroutineScope。当组件（如 Activity 或 Fragment）被销毁时
 */
fun <T> Flow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    minActivityState: Lifecycle.State = Lifecycle.State.CREATED,
    collector: FlowCollector<T>,
) {
    lifecycle.coroutineScope.launch(Dispatchers.Main) {
        //// 在这里执行异步操作
        var flow : Flow<T> = flowWithLifecycle(lifecycle, minActivityState)
        /**
         * collect方法 接受给定的收集器并将值发射到其中。
         * 此方法可与FlowCollector的SAM转换一起使用：
         */
        flow.collect(collector)
    }
}