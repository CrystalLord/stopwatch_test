package org.crystal.deprecated

import tornadofx.App
import tornadofx.UIComponent
import kotlin.reflect.KClass

class TestApp : App() {
    override val primaryView: KClass<out UIComponent> = TestView::class
}