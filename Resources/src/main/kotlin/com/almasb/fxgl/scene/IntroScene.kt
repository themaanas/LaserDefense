/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.scene

import com.almasb.fxgl.app.FXGL
import com.almasb.fxgl.scene.intro.IntroFinishedEvent

/**
 * Intro animation / video played before game starts
 * if intro is enabled in settings.
 *
 * Call [finishIntro] when your intro completed
 * so that the game can proceed to the next state.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
abstract class IntroScene : FXGLScene() {

    /**
     * Closes intro and initializes the next game state, whether it's a menu or game.
     *
     * Note: call this when your intro completes, otherwise
     * the game won't proceed to next state.
     */
    protected fun finishIntro() {
        FXGL.getEventBus().fireEvent(IntroFinishedEvent())
    }

    /**
     * Starts the intro animation / video.
     */
    abstract fun startIntro()
}