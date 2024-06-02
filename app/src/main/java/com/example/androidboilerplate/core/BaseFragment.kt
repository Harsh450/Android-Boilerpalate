package com.example.androidboilerplate.core


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.androidboilerplate.R
import com.example.androidboilerplate.utils.CustomProgressDialog

open class BaseFragment : Fragment() {

    private lateinit var toolbar: Toolbar

    private var progress: CustomProgressDialog? = null
    private var isShowBackButtonOnToolbar: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progress = CustomProgressDialog(requireContext())
    }

    fun showProgress() {
        if (!requireActivity().isFinishing) {
            progress?.show()
        }
    }

    fun hideProgress() {
        if (!requireActivity().isFinishing && progress?.isShowing == true) {
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
        (requireActivity() as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayShowTitleEnabled(
            false
        )
        if (isShowBackButtonOnToolbar!!) {
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(
                false
            )
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.setHomeButtonEnabled(true)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black)
            backButtonColor?.let {
                toolbar.navigationIcon?.setTint(it)
            }
            toolbar.setNavigationOnClickListener {
                onBackClickListener?.invoke()
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

    protected fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String
    ) {
        childFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commit()
    }

    protected fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        addToBackStack: Boolean? = false
    ) {
        childFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(if (addToBackStack!!) fragment::class.java.simpleName else null)
            .commit()
    }
}