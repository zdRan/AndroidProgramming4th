package com.zdran.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerList: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: total crimes :${crimeListViewModel.crimes.size}")
    }

    //处理视图
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)

        crimeRecyclerList = view.findViewById(R.id.crime_recycler_view)
        // RecycleView 需要指定视图管理器
        crimeRecyclerList.layoutManager = LinearLayoutManager(context)

        //更新视图
        updateUI()


        return view
    }

    private fun updateUI() {
        crimeRecyclerList.adapter = CrimeAdapter(crimeListViewModel.crimes)
    }

    //伴生对象,用于初始化 CrimeListFragment
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var crime: Crime

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        //初始化，绑定事件监听
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            dateTextView.text = crime.data.toString()
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${crime.title} pressed！", Toast.LENGTH_SHORT).show()
        }
    }

    //初始化 adapter 的时候需要指定数据集
    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            //生成 view 视图，并放到 holder 里
            return CrimeHolder(layoutInflater.inflate(R.layout.list_item_crime, parent, false))
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            //初始化视图需要展示的内容
            holder.bind(crimes[position])
        }

        override fun getItemCount(): Int {
            //RecycleView 中总共的 item 个数
            return crimes.size
        }

    }
}