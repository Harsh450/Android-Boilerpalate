package com.example.androidboilerplate.core


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.example.androidboilerplate.R
import com.example.androidboilerplate.utils.CustomProgressDialog
import com.example.androidboilerplate.utils.Logger
import com.example.androidboilerplate.utils.NetworkConnectivityCallback
import com.example.androidboilerplate.utils.showDialog

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var networkConnectivity: NetworkConnectivityCallback

    private var isShowBackButtonOnToolbar: Boolean? = false
    private var progress: CustomProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = CustomProgressDialog(this)
        networkConnectivity = NetworkConnectivityCallback(this)
        networkConnectivity.observeForever { isConnected ->
            if (!isConnected) {
                showDialog(message = "Please check your Internet Connection!!")
            }
            Logger.d("Internet: $isConnected")
        }

    }

    fun showProgress() {
        if (!this.isFinishing) {
            progress?.show()
        }
    }

    fun hideProgress() {
        if (!this.isFinishing && progress?.isShowing == true) {
            progress?.dismiss()
        }
    }

    /**
     * Method for perform multiple action by single line
     *
     * @param toolbar - pass your toolbar added in your layout
     * @param title - screen title
     * @param isShowBackButtonOnToolbar - want to show back button or not in activity
     * @param titleTextColor - color for toolbar title
     * @param toolbarColor - color for toolbar background
     * @param backButtonColor - color for back button
     */
    fun setupToolbar(
        toolbar: Toolbar,
        title: String,
        isShowBackButtonOnToolbar: Boolean? = false,
        titleTextColor: Int? = R.color.colorBlack,
        toolbarColor: Int? = null,
        backButtonColor: Int? = null,
        onBackClickListener: (() -> Unit)? = null,
    ) {
        this.toolbar = toolbar
        this.isShowBackButtonOnToolbar = isShowBackButtonOnToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (isShowBackButtonOnToolbar!!) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)
            backButtonColor?.let {
                toolbar.navigationIcon?.setTint(it)
            }
            toolbar.setNavigationOnClickListener {
                onBackClickListener?.invoke()
                onBackPressedDispatcher.onBackPressed()
            }
        }
        toolbar.findViewById<AppCompatTextView>(R.id.txt_header).text = title
        toolbar.findViewById<AppCompatTextView>(R.id.txt_header).setTextColor(titleTextColor!!)

        if (toolbarColor != null) {
            toolbar.background = ColorDrawable(toolbarColor)
        } else {
            toolbar.background = ColorDrawable(Color.TRANSPARENT)
        }
    }
}