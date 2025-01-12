plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true


    // packagingOptions 추가하여 중복된 리소스 제외
    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/NOTICE.md",
                "META-INF/LICENSE.md"
            )
        }
    }
}

dependencies {

implementation(libs.appcompat)
implementation(libs.material)
implementation(libs.activity)
implementation(libs.constraintlayout)
implementation(libs.lifecycle.livedata.ktx)
implementation(libs.lifecycle.viewmodel.ktx)
implementation(libs.navigation.fragment)
implementation(libs.navigation.ui)
testImplementation(libs.junit)
androidTestImplementation(libs.ext.junit)
androidTestImplementation(libs.espresso.core)


// JavaMail API 의존성 추가
implementation("com.sun.mail:android-mail:1.6.6")
implementation("com.sun.mail:android-activation:1.6.6")

// Google Play Services Auth API Phone 추가
implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1")
}
}
