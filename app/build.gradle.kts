plugins {
    id("com.android.application")
}

android {
    namespace = "it.unimib.brewday"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.unimib.brewday"
        minSdk = 24
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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildFeatures {
        viewBinding = true
    }
}


val appcompatVersion = "1.6.1"
val materialVersion = "1.11.0"
val constraintLayoutVersion = "2.1.4"
val roomRuntimeVersion = "2.6.1"
val androidxJunitVersion = "1.1.5"
val junitVersion = "4.13.2"
val testRunnerVersion = "1.5.2"
val testRulesVersion = "1.5.0"
val navigationFragmentVersion = "2.7.6"
val navigatioUiVersion = "2.7.6"
val espressocoreVersion = "3.5.1"
val dexmakermockitoVersion = "1.2"
val lifecycleVersion = "2.6.2"
val legacySupportVersion = "1.0.0"

val roomAnnotationCompilerVersion = "2.6.1"

dependencies {

    // ANDROID
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.navigation:navigation-fragment:$navigationFragmentVersion")
    implementation("androidx.navigation:navigation-ui:$navigatioUiVersion")
    implementation("androidx.legacy:legacy-support-v4:$legacySupportVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // MOCKITO
    implementation ("org.mockito:mockito-core")
    implementation ("com.google.dexmaker:dexmaker-mockito:$dexmakermockitoVersion")

    // ROOM
    implementation("androidx.room:room-runtime:$roomRuntimeVersion")
    annotationProcessor("androidx.room:room-compiler:$roomAnnotationCompilerVersion")

    // TESTING
    implementation("androidx.test.ext:junit:$androidxJunitVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.test:rules:$testRulesVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressocoreVersion")

    implementation ("it.xabaras.android:recyclerview-swipedecorator:1.4")








    //Dipendenze per fragment
    val  fragment_version = "1.6.2"
    implementation("androidx.fragment:fragment:$fragment_version")
    implementation("androidx.fragment:fragment-ktx:$fragment_version")


}