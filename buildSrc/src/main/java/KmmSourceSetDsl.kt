import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

typealias Container = NamedDomainObjectContainer<KotlinSourceSet>

fun Container.androidMain(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("androidMain", setup)

fun Container.commonMain(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("commonMain", setup)

fun Container.jvmMain(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("jvmMain", setup)

fun Container.desktopMain(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("desktopMain", setup)

fun Container.iosArm64Main(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("iosArm64Main", setup)

fun Container.iosMain(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("iosMain", setup)

fun Container.iosX64Main(setup: KotlinSourceSet.() -> Unit) = kotlinSourceSet("iosX64Main", setup)

private fun Container.kotlinSourceSet(
    name: String,
    setup: KotlinSourceSet.() -> Unit
) = named(name, setup).get()