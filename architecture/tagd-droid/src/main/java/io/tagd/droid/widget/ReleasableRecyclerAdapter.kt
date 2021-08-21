package io.tagd.droid.widget

import androidx.recyclerview.widget.RecyclerView
import io.tagd.arch.data.DataObject
import io.tagd.core.Releasable

abstract class ReleasableRecyclerAdapter<T : DataObject, VH : BindableRecyclerViewHolder<T, *, *>> :
    RecyclerView.Adapter<VH>(), Releasable {

    val items: MutableList<T> = mutableListOf()
    private var recyclerView: RecyclerView? = null

    fun setDataModel(list: List<T>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        release()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun release() {
        releaseViewHolders(recyclerView)
        items.clear()
        recyclerView = null
    }

    private fun releaseViewHolders(recyclerView: RecyclerView?) {
        recyclerView?.layoutManager?.let { layoutManager ->
            val viewCount = layoutManager.childCount
            for (index in 0..viewCount) {
                layoutManager.getChildAt(index)?.let {
                    recyclerView.getChildViewHolder(it)?.let { viewHolder ->
                        (viewHolder as BindableRecyclerViewHolder<*, *, *>).onDestroy()
                    }
                }
            }
        }
    }
}