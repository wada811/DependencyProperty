@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

/**
 * DependencyProperty is dependency resolver by delegated property.
 * You can use this in Activity/Fragment/AndroidViewModel.
 * ```
 * class App : Application(), DependencyComponent {
 *     val dependencyModules: DependencyModules by dependencyModules()
 * }
 * ```
 */
internal class DependencyProperty<T : DependencyModule, R>(
    private val dependencyModules: DependencyModules,
    private val clazz: Class<T>,
    internal val provide: T.() -> R
) : Lazy<R> {
    private var _value: R? = null
    override fun isInitialized(): Boolean = _value != null
    override val value: R
        get() = _value ?: dependencyModules.findModule(clazz).provide()
}

@Deprecated("Use dependency<T, R>(provide)", ReplaceWith("this.dependency<T, R>(provide)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Application.dependency(clazz: Class<T>, provide: (T) -> R): Lazy<R> {
    return DependencyProperty((this as DependencyComponent).dependencyModules, clazz, provide)
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> Application.dependency(noinline provide: (T) -> R): Lazy<R> {
    return dependency(T::class.java, provide)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(provide)", ReplaceWith("this.dependency<T, R>(provide)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> FragmentActivity.dependency(clazz: Class<T>, provide: (T) -> R): Lazy<R> {
    return application.dependency(clazz, provide)
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> FragmentActivity.dependency(noinline provide: (T) -> R): Lazy<R> {
    return dependency(T::class.java, provide)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(provide)", ReplaceWith("this.dependency<T, R>(provide)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Fragment.dependency(clazz: Class<T>, provide: (T) -> R): Lazy<R> {
    return requireActivity().application.dependency(clazz, provide)
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> Fragment.dependency(noinline provide: (T) -> R): Lazy<R> {
    return dependency(T::class.java, provide)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(provide)", ReplaceWith("this.dependency<T, R>(provide)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> AndroidViewModel.dependency(clazz: Class<T>, provide: (T) -> R): Lazy<R> {
    return getApplication<Application>().dependency(clazz, provide)
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> AndroidViewModel.dependency(noinline provide: (T) -> R): Lazy<R> {
    return dependency(T::class.java, provide)
}
