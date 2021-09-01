package io.tagd.androidx.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers private constructor() {

    lateinit var Main: CoroutineDispatcher
        private set

    lateinit var Default: CoroutineDispatcher
        private set

    lateinit var IO: CoroutineDispatcher
        private set

    lateinit var Unconfined: CoroutineDispatcher
        private set

    class Builder {

        private val cooking =
            Dispatchers()

        fun Main(dispatcher: CoroutineDispatcher): Builder {
            cooking.Main = dispatcher
            return this
        }

        fun IO(dispatcher: CoroutineDispatcher): Builder {
            cooking.IO = dispatcher
            return this
        }

        fun Default(dispatcher: CoroutineDispatcher): Builder {
            cooking.Default = dispatcher
            return this
        }

        fun Unconfined(dispatcher: CoroutineDispatcher): Builder {
            cooking.Unconfined = dispatcher
            return this
        }

        fun build(): Dispatchers {
            return cooking
        }
    }

    companion object {

        @JvmStatic
        private var active: Dispatchers? = null

        @JvmStatic
        fun set(provider: Dispatchers) {
            active = provider
        }

        @JvmStatic
        fun get(): Dispatchers = active
            ?: throw IllegalAccessException("call `Dispatchers.set` before accessing")

        val Main
            get() = try {
                get().Main
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Main
            }

        val Default
            get() = try {
                get().Default
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Default
            }

        val IO
            get() = try {
                get().IO
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.IO
            }

        val Unconfined
            get() = try {
                get().Unconfined
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Unconfined
            }
    }
}