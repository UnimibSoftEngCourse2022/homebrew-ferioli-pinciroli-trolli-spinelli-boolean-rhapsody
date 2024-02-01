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

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("org.mockito:mockito-core:5.10.0")
    implementation ("androidx.test:core:1.5.0")
    implementation ("androidx.test:runner:1.5.2")
    implementation ("androidx.test:rules:1.5.0")
    implementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.test.espresso:espresso-contrib:3.5.1")
    implementation ("org.mockito:mockito-core: 5.10.0")
    implementation ("com.google.dexmaker:dexmaker:1.2")
    implementation ("com.google.dexmaker:dexmaker-mockito:1.2")

}