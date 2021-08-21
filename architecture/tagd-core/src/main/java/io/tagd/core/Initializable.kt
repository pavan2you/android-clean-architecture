package io.tagd.core

import java.io.Serializable

/**
 * This is to resolve Gson not calling the init method of kotlin classes.
 */
interface Initializable : Serializable {

    /**
     * Trigger this explicitly to initialise an object state
     */
    fun initialize()
}