package com.e.mbulak.utils

import android.text.Editable

import android.text.TextWatcher
import android.util.Log


class MaskWatcher(var mask: String) : TextWatcher {

    private var isRunning = false
    private var isDeleting = false
    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(editable: Editable) {

        Log.d("ololo", "mask = " + mask)
        Log.d("ololo", "editable = " + editable)
        if (isRunning || isDeleting) {
            return
        }
        isRunning = true
        val editableLength = editable.length
        Log.d("ololo", "editableLEngth = " + editableLength)
        Log.d("ololo", "mask = " + mask)
        Log.d("ololo", "masklenght = " + mask.length)
        Log.d("ololo", "                                                           ")
        if (editableLength < mask.length) {
            if (mask[editableLength] != '#') {
                editable.append(mask[editableLength])
            } else if (mask[editableLength - 1] != '#') {
                editable.insert(editableLength - 1, mask, editableLength - 1, editableLength)
            }
        }
        isRunning = false
    }

}