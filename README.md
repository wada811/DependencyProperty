DependencyProperty
=====

`DependencyProperty` is a dependency resolution library by Delegated Property.

## Usage
### Application
```kt
class App: Application(), DependencyContext {
    override val dependencyModules: DependencyModules by dependencyModules(AppModule())
}
```

### Module
```kt
class AppModule : DependencyModule {
    val singleton: String by lazy { "singleton" }
    val factory: String get() = "factory"
    fun binds(instance: Int): Pair<String, Int> = singleton to instance
}
```

### Activity/Fragment/AndroidViewModel
```kt
class MainActivity : AppCompatActivity() {
    private val singleton by dependency<AppModule, String> { it.singleton }
    private val factory by dependency<AppModule, String> { it.factory }
    private val binds by dependency<AppModule, Pair<String, Int>> { it.binds(42) }
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
