package com.app.yuru.coreandroid.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/*
https://chetangupta.net/viewbinding/
 */

abstract class BaseFragmentBinding<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    private var _baseActivity: Activity? = null
    protected val baseActivity get() = _baseActivity!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _baseActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(binding)
    }

    abstract fun setupView(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        _baseActivity = null
    }
}