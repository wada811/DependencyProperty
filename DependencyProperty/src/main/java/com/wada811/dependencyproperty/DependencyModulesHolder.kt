@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application

/**
 * DependencyModulesHolder is DependencyModules holder.
 * Your Application class must implement this.
 *
 * ```
 * class App : Application(), DependencyModulesHolder {
 *     val dependencyModules: DependencyModules by dependencyModules()
 * }
 * ```
 */
interface DependencyModulesHolder {
    /**
     * ```
     * class App : Application(), DependencyModulesHolder {
     *     val dependencyModules: DependencyModules by dependencyModules()
     * }
     * ```
     */
    val dependencyModules: DependencyModules

    fun <T> T.dependencyModules(vararg modules: DependencyModule): Lazy<DependencyModules> where T : Application, T : DependencyModulesHolder {
        return lazy(LazyThreadSafetyMode.NONE) { DependencyModules(*modules) }
    }
}
