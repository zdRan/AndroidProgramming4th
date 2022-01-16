package com.zdran.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*


private const val TAG = "CrimeFragment"

private const val ARGS_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "DialogDate"
private const val DIALOG_TIME = "DialogTime"

//时间选择框的返回code
private const val REQUEST_DATE = 0

class CrimeFragment : Fragment(), DatePickerFragment.Callbacks,TimePickerFragment.Callbacks{
    private lateinit var crime: Crime
    private lateinit var titleFiled: EditText
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var solvedCheckBox: CheckBox
    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProvider(this)[CrimeDetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId: UUID = arguments?.getSerializable(ARGS_CRIME_ID) as UUID
        Log.d(TAG, "onCreate: $crimeId")
        crimeDetailViewModel.loadCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleFiled = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        timeButton = view.findViewById(R.id.crime_time) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            { crime ->
                run {
                    crime?.let {
                        this.crime = crime
                        updateUI()
                    }
                }
            })
    }

    override fun onStart() {
        super.onStart()

        //标题的监听器
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(TAG, "beforeTextChanged: beforeTextChanged called")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
                Log.d(TAG, "beforeTextChanged: onTextChanged called")

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d(TAG, "beforeTextChanged: afterTextChanged called")
            }
        }
        titleFiled.addTextChangedListener(titleWatcher)

        //checkBox 的监听器
        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
        //时间按钮的监听器
        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(crime.data).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }
        timeButton.setOnClickListener{
            TimePickerFragment.newInstance(crime.data).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.requireFragmentManager(),DIALOG_TIME)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARGS_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }

        }
    }

    private fun updateUI() {
        titleFiled.setText(crime.title)
        dateButton.text = DateFormat.format("yyyy-MM-dd", crime.data)
        timeButton.text = DateFormat.format("HH:mm:ss", crime.data)
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    override fun onDateSelected(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val crimeCalendar = Calendar.getInstance()
        crimeCalendar.time = crime.data

        crimeCalendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))

        crime.data = crimeCalendar.time
        updateUI()
    }

    override fun onTimeSelected(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        val crimeCalendar = Calendar.getInstance()
        crimeCalendar.time = crime.data
        crimeCalendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY))
        crimeCalendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE))
        crimeCalendar.set(Calendar.MILLISECOND,calendar.get(Calendar.MILLISECOND))

        crime.data = crimeCalendar.time
        updateUI()
    }
}