@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Application.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> {
    return lazy { dependencyModule(clazz).resolve() }
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> Application.dependency(noinline resolve: (T) -> R): Lazy<R> {
    return dependency(T::class.java, resolve)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> FragmentActivity.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> {
    return lazy { dependencyModule(clazz).resolve() }
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> FragmentActivity.dependency(noinline resolve: (T) -> R): Lazy<R> {
    return dependency(T::class.java, resolve)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Fragment.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> {
    return lazy { dependencyModule(clazz).resolve() }
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> Fragment.dependency(noinline resolve: (T) -> R): Lazy<R> {
    return dependency(T::class.java, resolve)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> AndroidViewModel.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> {
    return lazy { dependencyModule(clazz).resolve() }
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> AndroidViewModel.dependency(noinline resolve: (T) -> R): Lazy<R> {
    return dependency(T::class.java, resolve)
}

@Suppress("DEPRECATION")
@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Service.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> {
    return lazy { dependencyModule(clazz).resolve() }
}

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule, R> Service.dependency(noinline resolve: (T) -> R): Lazy<R> {
    return dependency(T::class.java, resolve)
}
