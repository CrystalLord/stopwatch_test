package org.crystal.deprecated

import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import org.crystal.stopwatch.Timer
import tornadofx.*
import kotlin.concurrent.thread

class TestView : View() {
    override val root = BorderPane()
    val timer: Timer = Timer()

    val topView = TopView(timer)
    val botView = BotView(timer)

    init {
        with(root) {
            top = topView.root;
            bottom = botView.root;
        }
    }
}

class TopView(val timer: Timer): View() {
    override val root = Label(timer.getStopwatchTime().toString())
    val prop: StringProperty = SimpleStringProperty(timer.secString())

    init {
        thread(start=true) {
            root.textProperty().bind(prop)
            while (true) {
                Platform.runLater {
                    prop.set(timer.getStopwatchTime().toString())
                }
                Thread.sleep(10)
            }
        }
    }
}

class BotView(val timer: Timer): View() {
    val buttonState: StringProperty = SimpleStringProperty("Start")

    override val root = button(buttonState) {
        useMaxWidth = true
        action {
            if(buttonState.get() == "Stop") {
                buttonState.set("Start")
                timer.pause()
            } else {
                buttonState.set("Stop")
                timer.start()
            }
        }
    }

    init {
        thread {
            root.textProperty().bind(buttonState)
        }
    }
}