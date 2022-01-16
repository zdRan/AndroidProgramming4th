package com.zdran.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * 时间选择对话框
 */
private const val ARGS_TIME = "time"

class TimePickerFragment : DialogFragment() {
    interface Callbacks {
        fun onTimeSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val timeListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->

                val calendar = Calendar.getInstance()
                calendar.time = Date()
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val resultTime: Date = calendar.time

                targetFragment?.let {
                    (it as Callbacks).onTimeSelected(resultTime)
                }

            }

        val date = arguments?.getSerializable(ARGS_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        return TimePickerDialog(
            requireContext(),
            timeListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    companion object {
        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARGS_TIME, date)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}