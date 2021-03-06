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
package pl.treksoft.kvision.remote

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asDeferred
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import pl.treksoft.kvision.utils.JSON
import kotlin.js.js
import kotlin.reflect.KClass
import kotlin.js.JSON as NativeJSON

/**
 * Client side agent for JSON-RPC remote calls with Jooby.
 */
@Suppress("LargeClass", "TooManyFunctions")
@UseExperimental(ImplicitReflectionSerializer::class)
open class JoobyRemoteAgent<T : Any>(val serviceManager: JoobyServiceManager<T>) : RemoteAgent {

    val callAgent = CallAgent()

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(noinline function: T.(Request?) -> Deferred<RET>): Deferred<RET> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified RET : Any, T> call(
        noinline function: T.(Request?) -> Deferred<List<RET>>
    ): Deferred<List<RET>> {
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, method = method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<RET>, p: PAR
    ): Deferred<RET> {
        val data = serialize(p)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR, reified RET : Any, T> call(
        noinline function: T.(PAR, Request?) -> Deferred<List<RET>>, p: PAR
    ): Deferred<List<RET>> {
        val data = serialize(p)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, Request?) -> Deferred<List<RET>>, p1: PAR1, p2: PAR2
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2, p3: PAR3
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, Request?) -> Deferred<List<RET>>, p1: PAR1, p2: PAR2, p3: PAR3
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<RET>, p1: PAR1, p2: PAR2, p3: PAR3, p4: PAR4
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, Request?) -> Deferred<List<RET>>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
            reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<RET>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): Deferred<RET> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                @Suppress("UNCHECKED_CAST")
                deserialize<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnum(RET::class as KClass<Any>, it) as RET
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer(), it)
                }
            }
        }.asDeferred()
    }

    /**
     * Executes defined call to a remote web service.
     */
    @Suppress("LongParameterList")
    inline fun <reified PAR1, reified PAR2, reified PAR3, reified PAR4, reified PAR5,
            reified RET : Any, T> call(
        noinline function: T.(PAR1, PAR2, PAR3, PAR4, PAR5, Request?) -> Deferred<List<RET>>,
        p1: PAR1,
        p2: PAR2,
        p3: PAR3,
        p4: PAR4,
        p5: PAR5
    ): Deferred<List<RET>> {
        val data1 = serialize(p1)
        val data2 = serialize(p2)
        val data3 = serialize(p3)
        val data4 = serialize(p4)
        val data5 = serialize(p5)
        val (url, method) =
                serviceManager.getCalls()[function.toString()] ?: throw IllegalStateException("Function not specified!")
        return callAgent.jsonRpcCall(url, listOf(data1, data2, data3, data4, data5), method).then {
            try {
                deserializeList<RET>(it, RET::class.js.name)
            } catch (t: NotStandardTypeException) {
                try {
                    @Suppress("UNCHECKED_CAST")
                    tryDeserializeEnumList(RET::class as KClass<Any>, it) as List<RET>
                } catch (t: NotEnumTypeException) {
                    JSON.nonstrict.parse(RET::class.serializer().list, it)
                }
            }
        }.asDeferred()
    }


    /**
     * @suppress
     * Internal function
     */
    inline fun <reified PAR> serialize(value: PAR): String? {
        return value?.let {
            @Suppress("UNCHECKED_CAST")
            trySerialize((PAR::class as KClass<Any>), it as Any)
        }
    }

}
