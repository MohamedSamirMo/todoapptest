package com.task.apptest.common.utils

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
fun Fragment.enableEdgeToEdge() {
    requireActivity().window.apply {
        statusBarColor = android.graphics.Color.TRANSPARENT
        navigationBarColor = android.graphics.Color.TRANSPARENT

        ViewCompat.setOnApplyWindowInsetsListener(decorView) { _, insets ->
            insets
        }

        WindowInsetsControllerCompat(this, decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }
}
