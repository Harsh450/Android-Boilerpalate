package com.example.androidboilerplate.utils

import android.content.Context
import android.os.SystemClock
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.androidboilerplate.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.regex.Pattern


/**
 * Extension functions for set visibility of any view by calling
 * yourView.visible()
 * yourView.gone()
 */
fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Extension functions for Toast
 */
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Extension functions for setOnClickListener
 */
fun View.setOnSafeClickListener(action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < 600L) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

/**
 * Extension functions for Dialog
 */

fun Context.showDialog(
    title: String = resources.getString(R.string.app_name),
    message: String,
    positiveBtnText: String? = this.resources.getString(R.string.ok),
    negativeBtnText: String? = this.resources.getString(R.string.cancel),
    isShowNegativeButton: Boolean? = false,
    setCancelable: Boolean = false,
    icon: Int? = null,
    themeStyle: Int? = R.style.AppTheme_Material3_AlertDialog,
    onPositiveBtnClick: (() -> Unit)? = null,
    onNegativeBtnClick: (() -> Unit)? = null,
) {
    val mDialog =
        MaterialAlertDialogBuilder(this, themeStyle ?: R.style.AppTheme_Material3_AlertDialog)
    mDialog.setCancelable(setCancelable)
    mDialog.setPositiveButton(positiveBtnText) { dialogInterface, _ ->
        onPositiveBtnClick?.invoke()
        dialogInterface.dismiss()
    }
    if (isShowNegativeButton == true) {
        mDialog.setNegativeButton(negativeBtnText) { dialogInterface, _ ->
            onNegativeBtnClick?.invoke()
            dialogInterface.dismiss()
        }
    }
    mDialog.setMessage(message).setTitle(title)
    if (icon != null) {
        mDialog.setIcon(icon)
    }
    mDialog.create().show()
}

fun isEmailValid(email: String): Boolean {
    if (email.isBlank()) {
        return false
    }
    val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
    return matcher.matches()
}

fun isPasswordValid(password: String): Boolean {
    if (password.isBlank()) {
        return false
    }
    val passwordPattern = Pattern.compile("^[A-Z][a-z]*\\d*@.*[A-Za-z\\d]+\$")
    val matcher = passwordPattern.matcher(password)
    return matcher.matches()
}

