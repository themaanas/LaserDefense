/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.texture

import com.almasb.fxgl.app.FXGLMock
import javafx.scene.image.Image
import javafx.scene.image.WritableImage
import javafx.util.Duration
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

/**
 *
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class AnimationChannelTest {

    companion object {
        private lateinit var image: Image

        @BeforeAll
        @JvmStatic fun before() {
            FXGLMock.mock()

            image = WritableImage(320, 320)
        }
    }

    @Test
    fun `Channel 1`() {
        val channel = AnimationChannel(image, 10, 32, 32, Duration.seconds(1.0), 0, 9)

        assertThat(channel.frameDuration, `is`(0.1))
        assertThat(channel.frameWidth, `is`(32))
        assertThat(channel.frameHeight, `is`(32))
        assertThat(channel.framesPerRow, `is`(10))
        assertThat(channel.image, `is`(image))

        assertThat(channel.sequence.size, `is`(10))
        assertThat(channel.sequence, hasItems(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
    }

    @Test
    fun `Channel 2`() {
        val channel = AnimationChannel(image, 10, 32, 32, Duration.seconds(3.0), 10, 12)

        assertThat(channel.frameDuration, `is`(1.0))
        assertThat(channel.frameWidth, `is`(32))
        assertThat(channel.frameHeight, `is`(32))
        assertThat(channel.framesPerRow, `is`(10))
        assertThat(channel.image, `is`(image))

        assertThat(channel.sequence.size, `is`(3))
        assertThat(channel.sequence, hasItems(10, 11, 12))
    }
}