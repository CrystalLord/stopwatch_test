package org.crystal.stopwatch

import javafx.application.Platform
import javafx.beans.property.StringProperty

class UIPainter : Runnable {
    /** Time between refreshes, in milliseconds. */
    var updateDelta: Long = 10L
    var prop: StringProperty? = null
    private var internalThread: Thread? = null

    fun start() {
        internalThread = Thread(this, "UIPainter")
        internalThread?.start()
    }

    override fun run() {
        try {
            while (true) {
                this.updateTimer()
                Thread.sleep(updateDelta)
            }
        } catch (e: InterruptedException) {
            // Exit thread gracefully.
        }

    }

    fun close() {
        this.internalThread?.interrupt()
    }

    private fun updateTimer() {
        Platform.runLater {
            prop?.set(
                    Controller.timer.minString() + ":"
                    + Controller.timer.secString() + "."
                    + Controller.timer.hundString())
        }
    }
}