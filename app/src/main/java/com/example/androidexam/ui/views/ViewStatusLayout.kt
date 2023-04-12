package com.example.androidexam.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.example.androidexam.R
import com.example.androidexam.databinding.DataStatusLayoutBinding

sealed class ViewStatus{

    object Loading : ViewStatus()

    object Success : ViewStatus()

    class Error(
        val message: String,
        @StringRes val buttonText: Int = R.string.retry,
        val action: (() -> Unit)? = null
    ) : ViewStatus()
}


class ViewStatusLayout(context: Context, attrs: AttributeSet): FrameLayout(context, attrs) {

    private val bindingObj = DataStatusLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        /** 2 views already added to show Loader and Error */
        if (childCount > 3){
            throw Exception("Don't add more than one child to Data status layout.")
        }
    }

    private fun showView(
        loading: Boolean = false,
        error: Boolean = false,
        success: Boolean = false) {

        getChildAt(0).isVisible = loading
        getChildAt(1).isVisible = error
        getChildAt(2)?.isVisible = success
    }

    fun setStatus(status: ViewStatus?){
        when(status){
            ViewStatus.Loading -> showView(loading = true)
            ViewStatus.Success -> showView(success = true)
            is ViewStatus.Error -> {
                showView(error = true)
                bindingObj.apply {
                    message.text = status.message
                    action.apply {
                        setText(status.buttonText)
                        isVisible = status.action != null
                        setOnClickListener {
                            status.action?.invoke()
                        }
                    }
                }
            }
            else -> showView() /** Hide all views */
        }
    }
}