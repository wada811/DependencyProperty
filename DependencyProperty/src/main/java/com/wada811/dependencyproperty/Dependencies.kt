package com.wada811.dependencyproperty

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface DependenciesComponent {
    val dependencies: Dependencies

    fun <T> T.initDependencies(): Lazy<Dependencies> where T : Application, T : DependenciesComponent {
        return lazy(LazyThreadSafetyMode.NONE) { Dependencies() }
    }
}

interface Dependency

class Dependencies internal constructor() {
    private val dependencies: MutableMap<Class<out Dependency>, Dependency> = mutableMapOf()
    @Suppress("UNCHECKED_CAST")
    fun <T : Dependency> findDependency(clazz: Class<T>): T {
        return dependencies.filter { clazz.isAssignableFrom(it.key) }.map { it.value }.firstOrNull() as? T
            ?: throw IllegalArgumentException("${clazz.simpleName} is missing")
    }

    fun <T : Dependency> addDependency(dependency: T) {
        dependencies[dependency::class.java] = dependency
    }
}

fun <T : Dependency> Application.addDependency(module: T) = (this as DependenciesComponent).dependencies.addDependency(module)
fun <T : Dependency> FragmentActivity.addDependency(module: T) = application.addDependency(module)
fun <T : Dependency> Fragment.addDependency(module: T) = requireActivity().application.addDependency(module)
