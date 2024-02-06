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

val roomAnnotationCompilerVersion = "2.6.1"

dependencies {

    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.room:room-runtime:$roomRuntimeVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidxJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressocoreVersion")
    annotationProcessor("androidx.room:room-compiler:$roomRuntimeVersion")
}