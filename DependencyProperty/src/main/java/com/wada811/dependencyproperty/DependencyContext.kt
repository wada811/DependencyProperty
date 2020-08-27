@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application

/**
 * DependencyContext is DependencyModules holder.
 * Your Application class must implement this.
 *
 * ```
 * class App : Application(), DependencyContext {
 *     val dependencyModules: DependencyModules by dependencyModules()
 * }
 * ```
 */
interface DependencyContext {
    /**
     * ```
     * class App : Application(), DependencyContext {
     *     val dependencyModules: DependencyModules by dependencyModules()
     * }
     * ```
     */
    val dependencyModules: DependencyModules

    fun <T> T.dependencyModules(vararg modules: DependencyModule): Lazy<DependencyModules> where T : Application, T : DependencyContext {
        return lazy(LazyThreadSafetyMode.NONE) { DependencyModules(*modules) }
    }
}
