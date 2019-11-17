DependencyProperty
=====

`DependencyProperty` is a dependency injection library by Delegated Property.

## Usage
### Application
```kt
class App: Application(), DependenciesComponent {
    override val dependencies: Dependencies by initDependencies()
    override fun onCreate() {
        dependencies.addDependency(AppDependency())
    }
}
```

### Dependency
```kt
class AppDependency : Dependency {
    val singleton: String by lazy { "singleton" }
    val factory: String get() = "factory"
    val params: List<String> by lazy { listOf(singleton, factory) }
    fun binds(instance: Int): Pair<String, Int> = singleton to instance
}
```

### Activity/Fragment
```kt
class MainActivity : AppCompatActivity() {
    private val singleton by dependency<AppDependency, String> { it.singleton }
    private val factory by dependency<AppDependency, String> { it.factory }
    private val params by dependency<AppDependency, List<String>> { it.params }
    private val binds by dependency<AppDependency, Pair<String, Int>> { it.binds(42) }
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

Copyright (C) 2019 wada811

Licensed under the Apache License, Version 2.0
