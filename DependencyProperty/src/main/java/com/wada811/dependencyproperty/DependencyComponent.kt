@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application

interface DependencyComponent {
    val dependencyModules: DependencyModules

    fun <T> T.dependencyModules(): Lazy<DependencyModules> where T : Application, T : DependencyComponent {
        return lazy(LazyThreadSafetyMode.NONE) { DependencyModules() }
    }
}