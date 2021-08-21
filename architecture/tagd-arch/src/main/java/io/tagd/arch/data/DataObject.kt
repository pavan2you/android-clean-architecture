package io.tagd.arch.data

import io.tagd.arch.data.bind.BindableSubject
import io.tagd.core.Initializable

open class DataObject : BindableSubject(), Initializable {

    enum class CrudOperation(val value: String) {
        CREATE("C"), UPDATE("U"), DELETE("D"), READ("R")
    }

    var crudOperation: CrudOperation = CrudOperation.CREATE

    override fun initialize() {
        crudOperation = CrudOperation.CREATE
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }

}



