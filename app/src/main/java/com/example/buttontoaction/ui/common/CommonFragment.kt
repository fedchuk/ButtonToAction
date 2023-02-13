package com.example.buttontoaction.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.buttontoaction.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach

abstract class CommonFragment<B : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B) :
    Fragment() {
    private var _binding: B? = null
    val binding: B get() = _binding ?: error("Must only access binding while fragment is attached.")
    abstract val viewModel: CommonViewModel?
    private var snackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.eventsFlow?.onEach {
            when (it) {
                is Event.ShowSnackBarError -> snackBar(it.code, it.text)
            }
        }
    }

    private fun hideSnackBar() {
        snackBar?.let {
            if (it.isShown) {
                it.dismiss()
            }
        }
    }

    private fun snackBar(code: Int, message: String) {
        hideSnackBar()
        activity?.findViewById<ConstraintLayout>(R.id.root_content)?.let { view ->
            snackBar = Snackbar.make(
                view,
                "[${code}]: $message",
                3000
            )
            snackBar?.show()
            view.isEnabled = false
        }
    }
}