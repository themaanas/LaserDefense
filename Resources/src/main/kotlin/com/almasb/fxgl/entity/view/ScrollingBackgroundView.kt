/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.entity.view

import com.almasb.fxgl.app.FXGL
import com.almasb.fxgl.texture.Texture
import javafx.geometry.Orientation
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

/**
 * EntityView for scrollable backgrounds.
 * Ensure that your viewport x, y cannot go < 0.
 * In other words, limit min x, y to 0.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class ScrollingBackgroundView
@JvmOverloads constructor(texture: Texture,
                          val orientation: Orientation = Orientation.HORIZONTAL,
                          val speed: Double = 1.0) : EntityView() {

    private val canvas: Canvas
    private val g: GraphicsContext

    private val image = texture.image

    private var sx = 0.0
    private var sy = 0.0

    init {
        val viewport = FXGL.getApp().gameScene.viewport

        canvas = Canvas(viewport.width, viewport.height)
        g = canvas.graphicsContext2D

        if (orientation == Orientation.HORIZONTAL) {
            translateXProperty().addListener { _, _, x ->

                if (x.toInt() < 0)
                    throw IllegalStateException("Background x cannot be < 0")

                sx = (x.toDouble() * speed) % image.width
                redraw()
            }

            translateXProperty().bind(viewport.xProperty())
        } else {
            translateYProperty().addListener { _, _, y ->

                if (y.toInt() < 0)
                    throw IllegalStateException("Background y cannot be < 0")

                sy = (y.toDouble() * speed) % image.height
                redraw()
            }

            translateYProperty().bind(viewport.yProperty())
        }

        addNode(canvas)

        redraw()
    }

    private fun redraw() {
        g.clearRect(0.0, 0.0, canvas.width, canvas.height)

        if (orientation == Orientation.HORIZONTAL) {
            redrawX()
        } else {
            redrawY()
        }
    }

    private fun redrawX() {
        var w = canvas.width
        val h = canvas.height

        val overflowX = sx + w > image.width

        if (overflowX) {
            w = image.width - sx
        }

        g.drawImage(image, sx, sy, w, h,
                0.0, 0.0, w, h)

        if (overflowX) {
            g.drawImage(image, 0.0, 0.0, canvas.width - w, h,
                    w, 0.0, canvas.width - w, h)
        }
    }

    private fun redrawY() {
        val w = canvas.width
        var h = canvas.height

        val overflowY = sy + h > image.height

        if (overflowY) {
            h = image.height - sy
        }

        g.drawImage(image, sx, sy, w, h,
                0.0, 0.0, w, h)

        if (overflowY) {
            g.drawImage(image, 0.0, 0.0, w, canvas.height - h,
                    0.0, h, w, canvas.height - h)
        }
    }
}