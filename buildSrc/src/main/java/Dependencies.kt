object AndroidConfig {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 29
}

object Versions {
    const val kotlin = "1.6.0"
    const val gradle = "4.1.2"
    const val junit = "4.12"
    const val core_ktx = "1.3.0"
    const val androidx_appcompat = "1.1.0"
    const val androidx_constraintlayout = "1.1.3"
    const val routing_navigator = "1.0.0"
    const val coroutines = "1.5.0"
    const val retrofit = "2.9.0"
    const val retrofit_moshi = "2.6.2"
    const val logging_interceptor = "4.8.0"
    const val hilt = "2.38"
    const val groupie = "2.9.0"
    const val room = "2.3.0"
    const val android_lifecycle = "2.3.0-alpha05"
    const val timber = "1.5.1"
    const val android_navigation = "2.3.5"
    const val detekt = "1.15.0"
    const val mockk = "1.10.5"
    const val latestFastAdapterRelease = "5.4.1"
}


object AndroidLib {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintlayout}"
    const val routing_navigator = "com.github.florent37:navigator:${Versions.routing_navigator}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val retrofit_android = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_moshi}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_processor_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupie_viewbinding = "com.xwray:groupie-viewbinding:${Versions.groupie}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_coroutine = "androidx.room:room-ktx:${Versions.room}"
    const val viewmodel_ktx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.android_lifecycle}"
    const val viewmodel_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.android_lifecycle}"
    const val viewmodel_extension = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val viewmodel_compiler =
        "androidx.lifecycle:lifecycle-compiler:${Versions.android_lifecycle}"
    const val timber = "com.github.ajalt:timberkt:${Versions.timber}"
    const val chart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    const val sdp = "com.intuit.sdp:sdp-android:1.0.6"
    const val ssp = "com.intuit.ssp:ssp-android:1.0.6"
}

object AndroidTestLib {
    const val junit_lib = "junit:junit:${Versions.junit}"
    const val android_test_junit = "androidx.test.ext:junit:1.1.1"
    const val android_test_espresso_core = "androidx.test.espresso:espresso-core:3.2.0"
}
