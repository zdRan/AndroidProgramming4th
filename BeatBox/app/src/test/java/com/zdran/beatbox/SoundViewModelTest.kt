package com.zdran.beatbox

import junit.framework.TestCase
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest : TestCase() {
    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel
    private lateinit var beatBox: BeatBox


    @Before
    override fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox)
        subject.sound = sound

    }

    @Test
    fun testExposeSoundNameAsTitle() {
        print(sound.name)
        assertThat(subject.title, `is`(sound.name))
    }

    @Test
    fun testCallBeatBoxPlay() {
        subject.onButtonClicked()
        verify(beatBox).play(sound)
    }

}