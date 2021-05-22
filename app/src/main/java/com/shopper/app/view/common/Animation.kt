package com.shopper.app.view.common

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator

interface Animation {

    fun animate(view: View)
}

class ScaleAnimation(
    private val scaleXFrom: Float = .7f,
    private val scaleYFrom: Float = .7f,
    private val interpolator: Interpolator = OVERSHOOT,
    private val animationDurationMs: Long = 200
) : Animation {

    companion object {
        private val OVERSHOOT = OvershootInterpolator(.7f)
    }

    override fun animate(view: View) {
        listOf(
            ObjectAnimator.ofFloat(view, "scaleX", scaleXFrom, 1f),
            ObjectAnimator.ofFloat(view, "scaleY", scaleYFrom, 1f)
        ).forEach { animator ->
            animator.interpolator = interpolator
            animator.setDuration(animationDurationMs).start()
        }
    }
}

fun View.animateWith(animation: Animation?) = animation?.animate(this)
