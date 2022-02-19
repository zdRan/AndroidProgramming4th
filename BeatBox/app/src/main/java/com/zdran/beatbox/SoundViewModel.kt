package com.zdran.beatbox

class SoundViewModel {

    var sound: Sound? = null

    val title: String?
        get() = sound?.name
}