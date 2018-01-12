package org.crystal.stopwatch

open class Timer {
    var startTime: Long = 0L
    var stopTime: Long = 0L
    private var millisCounted: Long = 0L
    private val mainRunnable: TimerRunnable
    private var initialised: Boolean = false
    private var currentState = 0

    /**
     * Thread which controls the timer.
     * @property[milliIncrement] How many milliseconds to increment by.
     */
    private inner class TimerRunnable(val milliIncrement: Long) : Runnable {
        private var millisCounted: Long = 0L
        private var paused: Boolean = false
        private var timerThread: Thread? = null

        fun start() {
            timerThread = Thread(this, "timer")
            timerThread?.start()
        }

        override fun run() {
            try {
                while(true) {
                    Thread.sleep(milliIncrement)
                    if (!paused) {
                        millisCounted += milliIncrement
                        this@Timer.setTime(millisCounted)
                    }
                }
            } catch (e: InterruptedException) {
                // Let the thread terminate
            }
        }

        fun pause() { this.timerThread?.interrupt() }

        fun resume() {
            this.timerThread?.start() ?: println("No thread to resume")
        }

        fun reset() {
            millisCounted = 0L
            this@Timer.setTime(millisCounted)
        }
    }

    init {
        this.mainRunnable = TimerRunnable(5L)
    }

    /**
     * Start the stopwatch to count upwards.
     */
    fun start() {
        if (initialised) {
            this.mainRunnable.resume()
        } else {
            this.mainRunnable.start()
        }

        this.currentState = 1
    }

    fun pause() {
        this.mainRunnable.pause()
        this.currentState = 2
    }

    /**
     * Stop the stopwatch.
     */
    fun stop() {
        this.mainRunnable.pause()
        this.mainRunnable.reset()
        this.currentState = 0
    }

    /**
     * Return the stopwatch time in milliseconds
     */
    fun getStopwatchTime(): Long = this.millisCounted

    /** Retrieve the state of the stopwatch */
    fun getState(): Int = this.currentState

    /**
     * Return the stopwatch time in seconds.
     */
    fun getStopwatchSec(): Int = (this.getStopwatchTime()/1000L).toInt() % 60

    fun getStopwatchMin(): Int = (this.getStopwatchTime()/1000L).toInt()/60 % 60

    fun getStopwatchHund(): Int = (this.getStopwatchTime()/10L).toInt() % 100

    fun hundString(): String = this.stringify(this.getStopwatchHund(), 2)

    fun secString(): String = this.stringify(this.getStopwatchSec(), 2)

    fun minString(): String = this.stringify(this.getStopwatchMin(), 2)

    /**
     * Convert an integer v to a string, with a minimum of z digits.
     * Added digits are zeros.
     */
    private fun stringify(v: Int, z: Int): String {
        var toReturn: String = v.toString()
        if (z - toReturn.length > -2) {
            toReturn = toReturn.padStart(z, '0')
        }
        return toReturn
    }

    /**
     * Method for the internal thread to update the time.
     */
    private fun setTime(time: Long) {
        this.millisCounted = time
    }
}