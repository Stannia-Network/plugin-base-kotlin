import kr.entree.spigradle.data.Load
import kr.entree.spigradle.kotlin.spigot

plugins {
    kotlin("jvm") version "1.6.10"
    id("kr.entree.spigradle") version "2.3.3"
}

group = "com.stannia.plugin"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    //implementation("com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.3")
    implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.14")

    compileOnly(spigot("1.18.1"))
}

// plugin.yml generation with Spigradle
spigot {
    name = getPropertyAsString("pluginName")
    description = getPropertyAsString("pluginDescription")
    authors = getPropertyAsList("pluginAuthors")
    apiVersion = getPropertyAsString("pluginApiVersion")
    load = Load.valueOf(getPropertyAsString("pluginLoad"))
    softDepends = getPropertyAsList("pluginSoftDependencies")
    depends = getPropertyAsList("pluginDependencies")
    version = getPropertyAsString("version")
    libraries = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib:1.6.10",
        "com.michael-bull.kotlin-inline-logger:kotlin-inline-logger:1.0.3",
        "com.michael-bull.kotlin-result:kotlin-result:1.1.14",
    )
}


/**
 * Helper method to get the property value as a trimmed list of strings.
 */
fun getPropertyAsList(property: String): List<String> {
    if (!project.hasProperty(property)) return emptyList()
    val value = (project.property(property) ?: return emptyList()).toString()
    return value.split(",").map { it.trim() }.filter { it.isNotBlank() }
}

/**
 * Helper method to get the property value as a string.
 */
fun getPropertyAsString(property: String) = (project.property(property) ?: "NOT_DEFINED").toString()