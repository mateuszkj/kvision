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
package pl.treksoft.kvision.form

import pl.treksoft.kvision.core.Component
import pl.treksoft.kvision.types.KFile
import kotlin.js.Date

/**
 * Input controls sizes.
 */
enum class InputSize(val className: String) {
    LARGE("input-lg"),
    SMALL("input-sm")
}

interface FormInput : Component {
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean
    /**
     * Input control field size.
     */
    var size: InputSize?
    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String?
}

/**
 * Base interface of a form control.
 */
interface FormControl : Component {
    /**
     * Determines if the field is disabled.
     */
    var disabled: Boolean
        get() = input.disabled
        set(value) {
            input.disabled = value
        }
    /**
     * Input control field size.
     */
    var size: InputSize?
        get() = input.size
        set(value) {
            input.size = value
        }
    /**
     * The name attribute of the generated HTML input element.
     */
    var name: String?
        get() = input.name
        set(value) {
            input.name = value
        }
    /**
     * The actual input component.
     */
    val input: FormInput
    /**
     * Form field label.
     */
    val flabel: FieldLabel
    /**
     * Validation info component.
     */
    val validationInfo: HelpBlock

    /**
     * Returns the value of the control.
     * @return the value
     */
    fun getValue(): Any?

    /**
     * Sets the value of the control.
     * @param v the value
     */
    fun setValue(v: Any?)

    /**
     * Returns the value of the control as a String.
     */
    fun getValueAsString(): String?

    /**
     * @suppress
     * Internal function
     * Re-renders the current component.
     * @return current component
     */
    fun refresh(): Component

    /**
     * Validator error message.
     */
    var validatorError: String?
        get() = validationInfo.content
        set(value) {
            validationInfo.content = value
            validationInfo.visible = value != null
            refresh()
        }

    /**
     * Makes the input element focused.
     */
    fun focus()

    /**
     * Makes the input element blur.
     */
    fun blur()
}

/**
 * Base interface of a form control with a text value.
 */
interface StringFormControl : FormControl {
    /**
     * Text value.
     */
    var value: String?

    override fun getValue(): String? = value
    override fun setValue(v: Any?) {
        value = v as? String
    }

    override fun getValueAsString(): String? = value
}

/**
 * Base interface of a form control with a numeric value.
 */
interface NumberFormControl : FormControl {
    /**
     * Numeric value.
     */
    var value: Number?

    override fun getValue(): Number? = value
    override fun setValue(v: Any?) {
        value = v as? Number
    }

    override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a boolean value.
 */
interface BoolFormControl : FormControl {
    /**
     * Boolean value.
     */
    var value: Boolean

    override fun getValue(): Boolean = value
    override fun setValue(v: Any?) {
        value = v as? Boolean ?: false
    }

    override fun getValueAsString(): String? = value.toString()
}

/**
 * Base interface of a form control with a date value.
 */
interface DateFormControl : FormControl {
    /**
     * Date value.
     */
    var value: Date?

    override fun getValue(): Date? = value
    override fun setValue(v: Any?) {
        value = v as? Date
    }

    override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a list of files value.
 */
interface KFilesFormControl : FormControl {
    /**
     * List of files value.
     */
    var value: List<KFile>?

    override fun getValue(): List<KFile>? = value
    override fun setValue(v: Any?) {
        if (v == null) value = null
    }

    override fun getValueAsString(): String? = value?.joinToString { it.name }
}
