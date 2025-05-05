@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    id("org.jetbrains.kotlin.plugin.serialization")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.komekci.marketplace"
    compileSdk = 34


    signingConfigs {
       create("release") {
           storeFile = file("../komekchi.jks")
           storePassword = "QwertyWeb123"
           keyAlias = "upload"
           keyPassword = "QwertyWeb123"
       }
    }


    defaultConfig {
        applicationId = "com.komekci.marketplace"
        minSdk = 21
        targetSdk = 34
        versionCode = 15
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // compose-navigation
    implementation(libs.navigation)

    //  Dagger hilt DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // compose
    implementation(libs.lifecycle.viewmodel.compose)

    // async/await
    implementation(libs.kotlinx.coroutines.android)

    // api call
    implementation(libs.retrofit)
    implementation(libs.moshi.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)


    // Room Database (Sqlite)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)


    // Language change (Locale)
    implementation(libs.lyricist)
    ksp(libs.lyricist.processor)
    ksp(libs.lyricist.processor.xml)

    // Image load
    implementation(libs.coil)

    // Blur library
    implementation(libs.blur)

    // Collapsing toolbar
    implementation(libs.collapsing)

    // Image zoom in / out
    implementation(libs.zoomable)

    // Lottie file
    implementation(libs.lottie)

    // Data store
    implementation(libs.datastore)

    // kotlin-reflect
    implementation(kotlin("reflect"))

    // kotlin-seralization
    implementation(libs.serialization)

    // socket io
    implementation(libs.socket) {
        exclude(group = "org.json", module = "json")
    }

    // permissions
    implementation(libs.permissions)

    // cupertiono
    implementation(libs.cupertion.core)
    implementation(libs.cupertion.native)
    implementation(libs.cupertino.icons.extended)

    // haze blur
    implementation(libs.haze)

    // webview
    implementation(libs.webview)

    // tree select
    implementation(libs.tree)

}

kapt {
    correctErrorTypes = true
}

ksp {
    arg("lyricist.internalVisibility", "true")
    // Required
    arg(
        "lyricist.xml.resourcesPath",
        android.sourceSets.getByName("main").resources.srcDirs.first().absolutePath
    )

    // Optional
    arg("lyricist.packageName", "com.komekci.marketplace")
    arg("lyricist.xml.moduleName", "xml")
    arg("lyricist.xml.defaultLanguageTag", "en")
}