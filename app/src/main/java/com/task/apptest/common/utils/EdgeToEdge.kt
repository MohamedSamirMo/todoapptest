package com.task.apptest.common.utils

import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.enableEdgeToEdge() {
    requireActivity().window.apply {
        @Suppress("DEPRECATION")
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    }
}
