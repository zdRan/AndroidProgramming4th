package com.zdran.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

private const val TAG = "BeatBox"
private const val SOUND_FOLDER = "sample_sound"
private const val MAX_SOUND = 5


class BeatBox(private val assetManager: AssetManager) : BaseObservable() {
    val sounds: List<Sound>
    var rate = 1
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val rateString: String
        get() = rate.toString()

    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUND).build()

    init {
        sounds = loadSound()
    }

    private fun loadSound(): List<Sound> {
        val soundNames = assetManager.list(SOUND_FOLDER)
        Log.d(TAG, "loadSound: 找到 ${soundNames?.size} 个文件")

        val sounds = mutableListOf<Sound>()
        soundNames?.forEach {
            val sound = Sound("${SOUND_FOLDER}/${it}")
            load(sound)
            sounds.add(sound)
        }
        return sounds
    }

    private fun load(sound: Sound) {
        sound.soundId = soundPool.load(assetManager.openFd(sound.assetPath), 1)
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate.toFloat())
        }
    }

    fun release() {
        soundPool.release()
    }

    fun setRate(rate: Float) {
        this.rate = rate.toInt()
    }
}