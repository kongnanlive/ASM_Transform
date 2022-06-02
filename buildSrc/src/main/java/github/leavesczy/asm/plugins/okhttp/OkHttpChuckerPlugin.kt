package github.leavesczy.asm.plugins.okhttp

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class OkHttpChuckerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val appExtension: AppExtension = project.extensions.getByType()
        appExtension.registerTransform(OkHttpChuckerTransform())
    }

}