package com.farmanlab.adaptableseekbar

import android.widget.SeekBar
import kotlin.apply
import kotlin.collections.first
import kotlin.collections.getOrNull
import kotlin.collections.last
import kotlin.collections.toList
import kotlin.collections.toMutableList
import kotlin.let

class AdaptableSeekBarAdapter<T> {
    internal var seekBar: androidx.appcompat.widget.AppCompatSeekBar? = null
        set(value) {
            field = value?.apply {
                setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        listener?.onProgressChanged(this@apply, itemList[progress], progress, fromUser)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        listener?.onStartTrackingTouch(this@apply)
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        listener?.onStopTrackingTouch(this@apply)
                    }
                })
            }
            updateProgressValue()
        }
    private var itemList: MutableList<T> = mutableListOf()

    var listener: OnAdaptableSeekBarChangeListener<T>? = null

    @androidx.annotation.UiThread
    fun setItemList(list: List<T>) {
        itemList = list.toMutableList()
        updateProgressValue()
    }

    fun getItemList(): List<T> = itemList.toList()

    @androidx.annotation.UiThread
    fun addItem(item: T, index: Int? = null) {
        index?.let { itemList.add(index, item) } ?: itemList.add(item)
        updateProgressValue()
    }

    @androidx.annotation.UiThread
    fun removeItem(item: T, index: Int? = null) {
        index?.let { itemList.removeAt(index) } ?: itemList.remove(item)
        updateProgressValue()
    }

    @androidx.annotation.UiThread
    fun setItem(item: T, index: Int) {
        itemList[index] = item
        updateProgressValue()
    }

    fun getCurrentItem(): T = itemList[checkNotNull(seekBar).progress]
    fun getItem(progress: Int): T = itemList[progress]
    fun getItemOrNull(progress: Int): T? = itemList.getOrNull(progress)

    @androidx.annotation.UiThread
    fun setCurrentItem(item: T) {
        updateProgressValue(item)
    }

    // for initialize item list before set seekbar.
    private fun internalGetCurrentItem(): T? = seekBar?.let { itemList[it.progress] }

    @Suppress("UNCHECKED_CAST")
    private fun updateProgressValue(item: T? = internalGetCurrentItem()) {
        if (item == null) return
        seekBar?.apply {
            max = itemList.size - 1
        }
        val indexOf = itemList.indexOf(item)
        seekBar?.let { seekBar ->
            if (indexOf == -1) {
                // When item is out of bounds and comparable, adjust index.
                (item as? Comparable<T>)?.let {
                    val min = itemList.min() ?: itemList.first()
                    val max = itemList.max() ?: itemList.last()
                    when {
                        it < min -> seekBar.progress = itemList.indexOf(min)
                        it > max -> seekBar.progress = itemList.indexOf(max)
                        else -> Logger.w("item can not match.")
                    }
                }
            } else {
                seekBar.progress = indexOf
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun List<T>.min(): T? {
        val iterator = iterator()
        if (!iterator.hasNext()) return null
        var min = iterator.next() as? Comparable<T> ?: return null
        while (iterator.hasNext()) {
            val e = iterator.next()
            if (min > e) min = e as Comparable<T>
        }
        return min as T
    }

    @Suppress("UNCHECKED_CAST")
    private fun List<T>.max(): T? {
        val iterator = iterator()
        if (!iterator.hasNext()) return null
        var max = iterator.next() as? Comparable<T> ?: return null
        while (iterator.hasNext()) {
            val e = iterator.next()
            if (max < e) max = e as Comparable<T>
        }
        return max as T
    }

    interface OnAdaptableSeekBarChangeListener<T> {
        fun onProgressChanged(seekBar: androidx.appcompat.widget.AppCompatSeekBar, item: T, progress: Int, fromUser: Boolean)
        fun onStartTrackingTouch(seekBar: androidx.appcompat.widget.AppCompatSeekBar)
        fun onStopTrackingTouch(seekBar: androidx.appcompat.widget.AppCompatSeekBar)
    }
}
