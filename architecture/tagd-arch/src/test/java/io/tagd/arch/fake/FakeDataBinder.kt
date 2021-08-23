package io.tagd.arch.fake

import io.tagd.arch.data.DataObject
import io.tagd.arch.present.mvb.BindableView
import io.tagd.arch.present.mvb.DataBinder

class FakeDataBinder<V : BindableView<DataObject>>(view: V) :
    DataBinder<DataObject, BindableView<DataObject>>(view) {

    override fun onBind(model: DataObject, vararg optionals: Any?) {
    }
}