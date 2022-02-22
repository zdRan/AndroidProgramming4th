package com.zdran.beatbox

private const val WAR = ".war"

class Sound(val assetPath: String, var soundId: Int? = null) {
    val name = assetPath.split("/").last().removeSuffix(WAR)
}