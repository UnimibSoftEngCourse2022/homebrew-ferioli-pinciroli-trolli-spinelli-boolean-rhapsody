plugins {
    id("com.android.application")
    id("jacoco")
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
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
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

    tasks.register("runAllTests") {
        group = "verification"
        description = "Esegui tutti i test di unità e i test strumentati"

        dependsOn("test", "connectedCheck")
    }

    tasks.register("jacocoTestReport", JacocoReport::class) {
        group = "reporting"
        description = "generate jacoco coverage reports"

        reports {
            reports {
                xml.required = true
                html.required = true
            }
        }

        sourceDirectories.setFrom(files("${project.projectDir}/src/main"))


        classDirectories.setFrom(
                fileTree("$buildDir/intermediates/javac/debug/classes").matching {
                    exclude(
                            // Android Data Binding
                            "android/databinding/**/*.class",
                            "**/android/databinding/*Binding.class",
                            "**/android/databinding/*",
                            "**/BR.*",

                            // Android Resources
                            "**/R.class",
                            "**/R$*.class",
                            "**/BuildConfig.*",
                            "**/Manifest*.*",
                            "**/*_Impl*.class",

                            // Unit Test Classes
                            "**/*Test*.*",

                            // Sealed and Data Classes
                            "**/*$Result.*"
                    )
                }
        )
    }
}

jacoco {
    toolVersion = "0.8.7"
}


val appcompatVersion = "1.6.1"
val materialVersion = "1.11.0"
val constraintLayoutVersion = "2.1.4"
val roomRuntimeVersion = "2.6.1"
val androidxJunitVersion = "1.1.5"
val junitVersion = "4.13.2"
val testRunnerVersion = "1.5.2"
val testRulesVersion = "1.5.0"

val roomAnnotationCompilerVersion = "2.6.1"

dependencies {

    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.room:room-runtime:$roomRuntimeVersion")
    implementation("androidx.test.ext:junit:$androidxJunitVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.test:rules:$testRulesVersion")

    annotationProcessor("androidx.room:room-compiler:$roomAnnotationCompilerVersion")
}