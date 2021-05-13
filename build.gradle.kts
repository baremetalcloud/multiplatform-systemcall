version="0.1"
group="com.baremetalcloud"

plugins {
  kotlin("multiplatform") version("1.5.0") 
  id("com.vanniktech.maven.publish") version("0.15.1") 
}

repositories {
  mavenCentral()
}


kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
      kotlinOptions.freeCompilerArgs = listOf("-Xmulti-platform")
      kotlinOptions.allWarningsAsErrors
    }
    testRuns["test"].executionTask.configure {
      useJUnit()
    }
  }
  explicitApiWarning()
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
        implementation("com.baremetalcloud:multiplatform-runblocking:0.52")
        implementation("com.baremetalcloud:multiplatform-file:0.53")
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
      }
    }
  }
}


plugins.withId("com.vanniktech.maven.publish") {
  mavenPublish {
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
  }
}




