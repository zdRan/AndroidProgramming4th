package com.zdran.beatbox

import android.content.res.AssetManager
import android.util.Log

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sound"

class BeatBox(private val assetManager: AssetManager) {
    fun loadSound(): List<String> {
        val soundNames = assetManager.list(SOUND_FOLDER)!!
        Log.d(TAG, "loadSound: 找到 ${soundNames.size} 个文件")
        return soundNames.asList()
    }
}