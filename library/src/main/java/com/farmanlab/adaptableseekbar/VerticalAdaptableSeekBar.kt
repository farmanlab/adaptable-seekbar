package com.farmanlab.wifiautoswitcher.util.widget

class VerticalAdaptableSeekBar @JvmOverloads constructor(
    context: android.content.Context,
    attr: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) : com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar(context, attr, defStyleAttr) {
    var adapter: com.farmanlab.wifiautoswitcher.util.widget.AdaptableSeekBarAdapter<*>? = null
        set(value) {
            field = value?.apply {
                seekBar = this@VerticalAdaptableSeekBar
            }
        }

    override fun setMin(min: Int) {
        // min always 0.
        super.setMin(0)
    }

    override fun setProgress(progress: Int) {
        if (adapter == null) {
            timber.log.Timber.w("Adapter is null.")
            super.setProgress(0)
            return
        }

        super.setProgress(progress)
    }

    override fun setProgress(progress: Int, animate: Boolean) {
        if (adapter == null) {
            timber.log.Timber.w("Adapter is null.")
            super.setProgress(0, animate)
            return
        }

        super.setProgress(progress, animate)
    }
}
