@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import android.app.Service
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

/**
 * DependencyModules is DependencyModule holder.
 */
class DependencyModules internal constructor(vararg modules: DependencyModule) {
    private val modules: MutableMap<Class<out DependencyModule>, DependencyModule> = modules.map { it.javaClass to it }.toMap().toMutableMap()

    @Suppress("UNCHECKED_CAST")
    internal fun <T : DependencyModule> findModule(clazz: Class<T>): T {
        return modules.filter { clazz.isAssignableFrom(it.key) }.map { it.value }.firstOrNull() as? T
            ?: throw IllegalStateException("${clazz.simpleName} is missing. Add module before use it.")
    }

    fun <T : DependencyModule> addModule(module: T) {
        if (modules[module.javaClass] == null) {
            modules[module.javaClass] = module
        }
    }

    fun <T : DependencyModule> replaceModule(module: T) {
        modules[module.javaClass] = module
    }

    @Suppress("DEPRECATION")
    inline fun <reified T : DependencyModule> removeModule() {
        removeModule(T::class.java)
    }

    @Deprecated("Use removeModule<T>()", ReplaceWith("this.removeModule<T>()"), DeprecationLevel.WARNING)
    fun <T : DependencyModule> removeModule(clazz: Class<T>) {
        modules.remove(clazz)
    }
}

val Application.dependencyModules: DependencyModules get() = (this as DependencyModulesHolder).dependencyModules
val FragmentActivity.dependencyModules: DependencyModules get() = application.dependencyModules
val Fragment.dependencyModules: DependencyModules get() = requireActivity().application.dependencyModules
val AndroidViewModel.dependencyModules: DependencyModules get() = getApplication<Application>().dependencyModules
val Service.dependencyModules: DependencyModules get() = application.dependencyModules
