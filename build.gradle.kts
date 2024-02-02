// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.sonarqube") version "4.4.1.3373"
    jacoco
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

