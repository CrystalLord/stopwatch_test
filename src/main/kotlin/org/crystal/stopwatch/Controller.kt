package org.crystal.stopwatch

class Controller {
    companion object {
        val timer = Timer()

        fun handleTimer() {
            when (timer.getState()) {
                0 -> timer.start()
                1 -> timer.pause()
                else -> timer.start()
            }
            println("Timer state changed")
        }

        fun close() {
            timer.stop()
        }

        fun resetTimer() {
            timer.stop()
        }
    }
}