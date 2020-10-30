DependencyProperty
=====

`DependencyProperty` is a dependency resolution library by Delegated Property.

## Overview

`DependencyProperty` is

- simple in defining and resolving dependencies.
- usable in classes accessible to Application instance like Activity, Fragment, and etc.
- able to inject to ViewModel's constructor without affecting other classes.
- less code for testing than Dagger Hilt.
- easy to use in multi-module and Dynamic Feature Module.
- easy to manage modules lifecycle.
- faster build time than Dagger.
- faster execution time than Dagger and Koin.

## Usage
### Configure DependencyProperty in Application
Application class must implements `DependencyModulesHolder` like the following.

```kt
class App: Application(), DependencyModulesHolder {
    override val dependencyModules: DependencyModules by dependencyModules(AppModule(this), CoroutinesModule())
}
```

You can pass `DependencyModule` to `dependencyModules()` as variadic arguments.

### Define dependencies in DependencyModule
`DependencyModule` is marker interface.
You can define dependencies as property or function.

```kt
open class CoroutinesModule : DependencyModule {
    open val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
    open val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    open val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    open val mainImmediateDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
}
```

You can resolve other DependencyModule using the following extension methods.

- `inline fun <reified T : DependencyModule> Application.dependencyModule(): T`
- `inline fun <reified T : DependencyModule> FragmentActivity.dependencyModule(): T`
- `inline fun <reified T : DependencyModule> Fragment.dependencyModule(): T`
- `inline fun <reified T : DependencyModule> AndroidViewModel.dependencyModule(): T`
- `inline fun <reified T : DependencyModule> Service.dependencyModule(): T`
- `inline fun <reified T : DependencyModule> Context.dependencyModule(): T`

```kt
class AppModule(private val application: Application) : DependencyModule {
    private val coroutinesModule: CoroutinesModule by lazy {
        application.dependencyModule<CoroutinesModule>()
    }
    val loadItemsUseCase: LoadItemsUseCase
        get() = LoadItemsUseCase(coroutineModule.ioDispatcher)
}
```

- If You want to define dependency as singleton, you can use lazy.
- If You want to define dependency as not singleton, you can use getter.
- If You want to define dependency using parameters, you can use function.

### Resolve dependencies

You can resolve dependency by delegated property using the following extension methods.

- `fun <T: DependencyModule, R> Application.dependency<T, R>(resolve: (T) -> R): Lazy<R>`
- `fun <T: DependencyModule, R> FragmentActivity.dependency<T, R>(resolve: (T) -> R): Lazy<R>`
- `fun <T: DependencyModule, R> Fragment.dependency<T, R>(resolve: (T) -> R): Lazy<R>`
- `fun <T: DependencyModule, R> AndroidViewModel.dependency<T, R>(resolve: (T) -> R): Lazy<R>`
- `fun <T: DependencyModule, R> Service.dependency<T, R>(resolve: (T) -> R): Lazy<R>`
- `fun <T: DependencyModule, R> Context.dependency<T, R>(resolve: (T) -> R): Lazy<R>`

Activity's example is the following.

```kt
class MainActivity : AppCompatActivity() {
    private val loadItemsUseCase by dependency<AppModule, LoadItemsUseCase> { it.loadItemsUseCase }
}
```

Another example is the following.

```kt
class MainActivity : AppCompatActivity() {
    private val loadItemsUseCase: LoadItemsUseCase by lazy { dependencyModule<AppModule>().loadItemsUseCase }
}
```

For testing, ViewModel inherits AndroidViewModel and its constructor is annotated `@JvmOverloads`.

```kt
class MainViewModel @JvmOverloads constructor(
    application: Application,
    savedStateHandle: SavedStateHandle
    private val loadItemsUseCase: LoadItemsUseCase = application.dependencyModule<AppModule>().loadItemsUseCase
) : AndroidViewModel(application) {
    // You can use loadItemsUseCase
}
```

By @JvmOverloads, ViewModel's dependencies is passed as default arguments.

### Unit Test

In Unit Test, DependencyProperty is not used.
You can inject to constructor.

```kt
@Test
fun test() {
    // init loadItemsUseCase
    // ...
    // init ViewModel
    val viewModel = MainViewModel(Application(), SavedStateHandle(), loadItemsUseCase)
    // test ViewModel
}
```

### UI Test

You need only to define CustomTestRunner and Application for testing.

```gradle
android {
    defaultConfig {
        // Replace com.example with your class path.
        testInstrumentationRunner "com.example.CustomTestRunner"
    }
}
```

```kt
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
```

You can override DependencyModule by passing inherited DependencyModule.

```kt
class TestApp: Application(), DependencyModulesHolder {
    override val dependencyModules: DependencyModules by dependencyModules(AppModule(this), TestCoroutinesModule())
}
```

TestCoroutinesModule inherits CoroutinesModule and overrides its properties.

```kt
class TestCoroutinesModule : CoroutinesModule() {
    override val defaultDispatcher: CoroutineDispatcher = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
    override val ioDispatcher: CoroutineDispatcher = AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
    override val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    override val mainImmediateDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
}
```

### Multi-module and Dynamic Feature Module

In multi-module, no extra settings.
You can use other module's DependencyModule in app module.

```kt
class App: Application(), DependencyModulesHolder {
    override val dependencyModules: DependencyModules by dependencyModules(AppModule(this), CoroutinesModule())
}
```

In Dynamic Feature Module, you can add DependencyModule dynamically using the following extension methods.

```kt
val Application.dependencyModules: DependencyModules
    get() = (this as DependencyModulesHolder).dependencyModules
val FragmentActivity.dependencyModules: DependencyModules
    get() = application.dependencyModules
val Fragment.dependencyModules: DependencyModules
    get() = requireActivity().application.dependencyModules
val AndroidViewModel.dependencyModules: DependencyModules
    get() = getApplication<Application>().dependencyModules
val Service.dependencyModules: DependencyModules
    get() = application.dependencyModules
val Context.dependencyModules: DependencyModules
    get() = (applicationContext as Application).dependencyModules
```

Activity's example is the following.

```kt
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dependencyModules.addModule(DynamicModule()) // add only once
    }
}
```

### Lifecycle of DependencyModule

You can manage lifecycle of DependencyModule using the following methods.

- `fun <T: DependencyModule> DependencyModules.addModule(module: T)`
- `fun <T: DependencyModule> DependencyModules.removeModule<T>()`
- `fun <T: DependencyModule> DependencyModules.replaceModule(module: T)`

Activity's example is the following.

```kt
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Replace an existing DependencyModule of the same type with a new DependencyModule
        dependencyModules.replaceModule(AppModule(application))
    }
}
```

## Gradle

[![](https://jitpack.io/v/wada811/DependencyProperty.svg)](https://jitpack.io/#wada811/DependencyProperty)

```groovy
repositories {
    maven { url "https://www.jitpack.io" }
}

dependencies {
    implementation 'com.github.wada811:DependencyProperty:x.y.z'
}
```

## License

Copyright (C) 2020 wada811

Licensed under the Apache License, Version 2.0
