pluginManagement {
    repositories {
<<<<<<< HEAD
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

=======
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
>>>>>>> 05016b0212eaf5d3c1c2398839be99833088a8fb
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
<<<<<<< HEAD
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "CURR"
include(":app")
=======
    }
}

rootProject.name = "CUR"
include(":app")
 
>>>>>>> 05016b0212eaf5d3c1c2398839be99833088a8fb
