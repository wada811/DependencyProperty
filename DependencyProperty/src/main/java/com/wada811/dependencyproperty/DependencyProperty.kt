package com.wada811.dependencyproperty

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class DependencyProperty<T : Dependency, R>(
    private val dependencies: Dependencies,
    private val clazz: Class<T>,
    internal val provide: T.() -> R
) : ReadOnlyProperty<Any, R> {
    override fun getValue(thisRef: Any, property: KProperty<*>): R = dependencies.findDependency(clazz).provide()
}

fun <T : Dependency, R> Application.dependency(clazz: Class<T>, provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return DependencyProperty((this as DependenciesComponent).dependencies, clazz, provide)
}

inline fun <reified T : Dependency, R> Application.dependency(noinline provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return dependency(T::class.java, provide)
}

fun <T : Dependency, R> FragmentActivity.dependency(clazz: Class<T>, provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return application.dependency(clazz, provide)
}

inline fun <reified T : Dependency, R> FragmentActivity.dependency(noinline provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return application.dependency(provide)
}

fun <T : Dependency, R> Fragment.dependency(clazz: Class<T>, provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return requireActivity().application.dependency(clazz, provide)
}

inline fun <reified T : Dependency, R> Fragment.dependency(noinline provide: (T) -> R): ReadOnlyProperty<Any, R> {
    return requireActivity().application.dependency(provide)
}


