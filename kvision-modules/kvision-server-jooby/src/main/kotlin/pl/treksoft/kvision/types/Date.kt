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
package pl.treksoft.kvision.types

import com.github.andrewoma.kwery.mapper.SimpleConverter
import com.github.andrewoma.kwery.mapper.TableConfiguration
import com.github.andrewoma.kwery.mapper.reifiedConverter
import com.github.andrewoma.kwery.mapper.standardConverters
import com.github.andrewoma.kwery.mapper.util.camelToLowerUnderscore
import java.sql.Timestamp
import java.text.SimpleDateFormat

actual typealias Date = java.util.Date

actual fun String.toDateF(format: String): Date = SimpleDateFormat(format).parse(this)

actual fun Date.toStringF(format: String): String = SimpleDateFormat(format).format(this)

object DateConverter : SimpleConverter<Date>(
    { row, c -> Date(row.timestamp(c).time) },
    { Timestamp(it.time) }
)

val kvTableConfig = TableConfiguration(
    converters = standardConverters + reifiedConverter(DateConverter),
    namingConvention = camelToLowerUnderscore
)
