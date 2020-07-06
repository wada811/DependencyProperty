@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application

/**
 * DependencyComponent is DependencyModules holder.
 * Your Application class must implement this.
 *
 * ```
 * class App : Application(), DependencyComponent {
 *     val dependencyModules: DependencyModules by dependencyModules()
 * }
 * ```
 */
interface DependencyComponent {
    /**
     * val dependencyModules: DependencyModules by dependencyModules()
     */
    val dependencyModules: DependencyModules

    fun <T> T.dependencyModules(): Lazy<DependencyModules> where T : Application, T : DependencyComponent {
        return lazy(LazyThreadSafetyMode.NONE) { DependencyModules() }
    }
}
