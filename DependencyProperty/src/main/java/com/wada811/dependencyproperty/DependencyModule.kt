@file:Suppress("unused")

package com.wada811.dependencyproperty

import android.app.Application
import android.app.Service
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

/**
 * DependencyModule is maker interface.
 * Your module must implement this.
 *
 * You define the dependency as property or function in class implemented DependencyModule.
 */
interface DependencyModule

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> Application.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> Application.dependencyModule(): T = dependencyModule(T::class.java)

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> FragmentActivity.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> FragmentActivity.dependencyModule(): T = dependencyModule(T::class.java)

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> Fragment.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> Fragment.dependencyModule(): T = dependencyModule(T::class.java)

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> AndroidViewModel.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> AndroidViewModel.dependencyModule(): T = dependencyModule(T::class.java)

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> Service.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> Service.dependencyModule(): T = dependencyModule(T::class.java)

@Deprecated("Use dependencyModule<T>()", ReplaceWith("this.dependencyModule<T>()"), DeprecationLevel.WARNING)
fun <T : DependencyModule> Context.dependencyModule(clazz: Class<T>): T = dependencyModules.findModule(clazz)

@Suppress("DEPRECATION")
inline fun <reified T : DependencyModule> Context.dependencyModule(): T = dependencyModule(T::class.java)
