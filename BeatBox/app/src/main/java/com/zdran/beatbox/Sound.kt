package com.zdran.beatbox

private const val WAR = ".war"

class Sound(private val assetPath: String) {
    val name = assetPath.split("/").last().removeSuffix(WAR)
}