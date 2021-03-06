/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pl.treksoft.kvision.navbar

import pl.treksoft.kvision.core.StringBoolPair
import pl.treksoft.kvision.html.TAG
import pl.treksoft.kvision.html.Tag

/**
 * The Bootstrap Nav container.
 *
 * @constructor
 * @param rightAlign determines if the nav is aligned to the right
 * @param classes a set of CSS class names
 * @param init an initializer extension function
 */
open class Nav(rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (Nav.() -> Unit)? = null) :
    Tag(TAG.UL, classes = classes) {

    /**
     * Determines if the nav is aligned to the right.
     */
    var rightAlign by refreshOnUpdate(rightAlign)

    init {
        @Suppress("LeakingThis")
        init?.invoke(this)
    }

    override fun getSnClass(): List<StringBoolPair> {
        val cl = super.getSnClass().toMutableList()
        cl.add("nav" to true)
        cl.add("navbar-nav" to true)
        if (rightAlign) {
            cl.add("navbar-right" to true)
        }
        return cl
    }

    companion object {
        /**
         * DSL builder extension function.
         *
         * It takes the same parameters as the constructor of the built component.
         */
        fun Navbar.nav(
            rightAlign: Boolean = false, classes: Set<String> = setOf(), init: (Nav.() -> Unit)? = null
        ): Nav {
            val nav = Nav(rightAlign, classes).apply { init?.invoke(this) }
            this.add(nav)
            return nav
        }
    }
}
