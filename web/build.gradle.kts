plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    with(Deps.Kotlinx) {
        implementation(htmlJs)
    }

    implementation(project(":common"))
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}