@file:Suppress("DEPRECATION", "unused")

package com.wada811.dependencyproperty

import android.app.Application
import android.app.Service
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.work.ListenableWorker

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Application.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> Application.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> FragmentActivity.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> FragmentActivity.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Fragment.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> Fragment.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> AndroidViewModel.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> AndroidViewModel.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Service.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> Service.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> Context.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> Context.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)

@Deprecated("Use dependency<T, R>(resolve)", ReplaceWith("this.dependency<T, R>(resolve)"), DeprecationLevel.WARNING)
fun <T : DependencyModule, R> ListenableWorker.dependency(clazz: Class<T>, resolve: T.() -> R): Lazy<R> = lazy { dependencyModule(clazz).resolve() }
inline fun <reified T : DependencyModule, R> ListenableWorker.dependency(noinline resolve: (T) -> R): Lazy<R> = dependency(T::class.java, resolve)