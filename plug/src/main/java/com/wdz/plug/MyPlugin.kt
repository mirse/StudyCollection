package com.wdz.plug

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin:Plugin<Project> {
    override fun apply(target: Project) {
        System.out.println("hello")
    }
}