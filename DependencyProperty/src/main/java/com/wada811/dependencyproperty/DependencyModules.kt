@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class DependencyModules internal constructor() {
    private val modules: MutableMap<Class<out DependencyModule>, DependencyModule> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <T : DependencyModule> findModule(clazz: Class<T>): T {
        return modules.filter { clazz.isAssignableFrom(it.key) }.map { it.value }.firstOrNull() as? T
            ?: throw IllegalStateException("${clazz.simpleName} is missing. Add module before use it.")
    }

    fun <T : DependencyModule> addModule(dependency: T) {
        modules[dependency::class.java] = dependency
    }
}

fun <T : DependencyModule> Application.addModule(module: T) = (this as DependencyComponent).dependencyModules.addModule(module)
fun <T : DependencyModule> FragmentActivity.addModule(module: T) = application.addModule(module)
fun <T : DependencyModule> Fragment.addModule(module: T) = requireActivity().application.addModule(module)