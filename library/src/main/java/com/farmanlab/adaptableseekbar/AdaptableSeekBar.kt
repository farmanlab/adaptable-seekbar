package com.farmanlab.adaptableseekbar

class AdaptableSeekBar @JvmOverloads constructor(
    context: android.content.Context,
    attr: android.util.AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatSeekBar(context, attr, defStyleAttr) {
    var adapter: com.farmanlab.wifiautoswitcher.util.widget.AdaptableSeekBarAdapter<*>? = null
        set(value) {
            field = value?.apply {
                seekBar = this@AdaptableSeekBar
            }
        }

    override fun setMin(min: Int) {
        // min always 0.
        super.setMin(0)
    }

    override fun setProgress(progress: Int) {
        if (adapter == null) {
            Logger.w("Adapter is null.")
            super.setProgress(0)
            return
        }

        super.setProgress(progress)
    }

    override fun setProgress(progress: Int, animate: Boolean) {
        if (adapter == null) {
            Logger.w("Adapter is null.")
            super.setProgress(0, animate)
            return
        }

        super.setProgress(progress, animate)
    }
}
