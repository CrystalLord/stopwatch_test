package org.crystal.stopwatch

import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage

class Stopwatch : Application() {

    var uiPainter: UIPainter? = null

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hello World!"

        uiPainter = UIPainter()

        val toggleButton = Button()
        val resetButton = Button()
        val timeLabel = Label()
        val timeProp: StringProperty = SimpleStringProperty("CLOCK")
        val grid = GridPane()

        grid.padding = Insets(10.0, 10.0, 10.0, 10.0)
        grid.vgap = 10.0
        grid.hgap = 10.0

        grid.add(timeLabel, 0, 0)
        grid.add(toggleButton, 0, 1)
        grid.add(resetButton, 1, 1)

        toggleButton.text = "Start"
        toggleButton.onAction = EventHandler<ActionEvent> {
            if (Controller.timer.getState() != 1) {
                toggleButton.text = "Stop"
            } else {
                toggleButton.text = "Start"
            }
            Controller.handleTimer()
        }

        resetButton.text = "Reset"
        resetButton.onAction = EventHandler<ActionEvent> {
            Controller.resetTimer()
        }

        timeLabel.textProperty().bind(timeProp)
        timeLabel.font = Font.font("Courier New", FontWeight.NORMAL, 17.0)

        primaryStage.scene = Scene(grid, 180.0, 100.0)

        uiPainter?.prop = timeProp
        uiPainter?.start()

        primaryStage.show()
    }

    override fun stop() {
        println("closed")
        uiPainter?.close()
        Controller.close()
    }
}