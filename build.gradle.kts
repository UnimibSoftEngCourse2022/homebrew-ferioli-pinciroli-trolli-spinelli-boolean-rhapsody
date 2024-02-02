// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
}

sonar {
    properties {
        property("sonar.projectKey", "UnimibSoftEngCourse2022_homebrew-ferioli-pinciroli-trolli-spinelli-boolean-rhapsody")
        property("sonar.organization", "unimibsoftengcourse2022")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.tests", "src/androidTest")
        property("sonar.core.codeCoveragePlugin", "jacoco")
        property("sonar.sources", "app")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    }
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.register("runAllTests") {
    group = "verification"
    description = "Esegui tutti i test di unità e i test strumentati"

    dependsOn("test", "connectedCheck")
}

tasks.withType<JacocoReport> {
    reports {
        xml.required = true
    }
}

tasks.withType<JacocoCoverageVerification> {
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

                        // Unit Test Classes
                        "**/*Test*.*",

                        // Sealed and Data Classes
                        "**/*$Result.*"
                )
            }
    )
}

