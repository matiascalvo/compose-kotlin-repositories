# Kotlin Repositories

![Workflow result](https://github.com/matiascalvo/compose-kotlin-repositories/workflows/CI/badge.svg)


## :bulb: Motivation and Context

The app shows a paginated list of the most starred Kotlin repositories in Github and also let you search for repos.
This is app was made to continue learning Jetpack compose.

## Development

* UI written in [Jetpack Compose](https://developer.android.com/jetpack/compose).
* Built 100% in Kotlin and uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html).
* Uses many of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/), including Lifecycle and Navigation.
* [Hilt](https://dagger.dev/hilt/) for dependency injection.
* Animations are displayed with [Lottie](https://airbnb.io/lottie/).
* Images are shown using [Coil](https://coil-kt.github.io/coil/).
* [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs for [Github API](https://docs.github.com/es/rest).
* API is mocked for tests using [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver).
* [Gradle version catalog TOML file](https://docs.gradle.org/current/userguide/platforms.html) for sharing dependencies.
* This project uses [detekt](https://detekt.dev/) as static code analysis tool and
  [spotless](https://github.com/diffplug/spotless) for checking code style
  (Kotlin with [ktlint](https://github.com/pinterest/ktlint), XMLs and Gradle Files).
* Uses [Github Actions](https://github.com/features/actions) as CI/CD.


## :camera_flash: Screenshots

### 🌞 Light Mode
Home | Search | Details
--- | --- | --- |
<img src="/screenshots/home_light.png" width="260"> | <img src="/screenshots/search_light.png" width="260"> | <img src="/screenshots/details_light.png" width="260">

### 🌚 Dark Mode
Welcome | Log in | Home
--- | --- | --- |
<img src="/screenshots/home_dark.png" width="260"> | <img src="/screenshots/search_dark.png" width="260"> | <img src="/screenshots/details_dark.png" width="260">


## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/matiascalvo/compose-kotlin-repositories/stargazers)__ for this repository. :star:
