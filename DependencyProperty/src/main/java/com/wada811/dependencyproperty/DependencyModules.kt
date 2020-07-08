@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

/**
 * DependencyModules is DependencyModule holder.
 */
class DependencyModules internal constructor(vararg modules: DependencyModule) {
    private val modules: MutableMap<Class<out DependencyModule>, DependencyModule> = modules.map { it.javaClass to it }.toMap().toMutableMap()

    @Suppress("UNCHECKED_CAST")
    fun <T : DependencyModule> findModule(clazz: Class<T>): T {
        return modules.filter { clazz.isAssignableFrom(it.key) }.map { it.value }.firstOrNull() as? T
            ?: throw IllegalStateException("${clazz.simpleName} is missing. Add module before use it.")
    }

    /**
     * For Dynamic Feature Module
     */
    fun <T : DependencyModule> addModule(module: T) {
        if (modules[module.javaClass] == null) {
            modules[module.javaClass] = module
        }
    }

    /**
     * For Scope
     */
    fun <T : DependencyModule> replaceModule(module: T) {
        modules[module.javaClass] = module
    }
}

fun <T : DependencyModule> Application.addModule(module: T) = (this as DependencyContext).dependencyModules.addModule(module)
fun <T : DependencyModule> FragmentActivity.addModule(module: T) = application.addModule(module)
fun <T : DependencyModule> Fragment.addModule(module: T) = requireActivity().application.addModule(module)
fun <T : DependencyModule> AndroidViewModel.addModule(module: T) = getApplication<Application>().addModule(module)

fun <T : DependencyModule> Application.replaceModule(module: T) = (this as DependencyContext).dependencyModules.replaceModule(module)
fun <T : DependencyModule> FragmentActivity.replaceModule(module: T) = application.replaceModule(module)
fun <T : DependencyModule> Fragment.replaceModule(module: T) = requireActivity().application.replaceModule(module)
fun <T : DependencyModule> AndroidViewModel.replaceModule(module: T) = getApplication<Application>().replaceModule(module)