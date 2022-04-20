package com.zdran.nerdlauncher

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ResolveInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "NerdLauncherActivity"

class NerdLauncherActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nerd_launcher)
        recyclerView = findViewById(R.id.app_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupAdapter()
    }

    /**
     * 禁用返回键，主屏幕不允许返回
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.i(TAG, "onKeyDown: 禁用返回")
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupAdapter() {
        val setupIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val activities = packageManager.queryIntentActivities(setupIntent, 0)
        Log.i(TAG, "setupAdapter: found ${activities.size} activities")
        activities.sortWith(Comparator { a, b ->
            String.CASE_INSENSITIVE_ORDER.compare(
                a.loadLabel(packageManager).toString(),
                b.loadLabel(packageManager).toString()
            )
        })
        recyclerView.adapter = ActivityAdapter(activities)
    }

    /**
     * 列表视图的 ViewHolder，绑定启动应用的名称
     */
    private class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val nameTextView = itemView.findViewById<TextView>(R.id.app_name_text_view)
        private val appIconImageView = itemView.findViewById<ImageView>(R.id.app_icon_image_view)

        private lateinit var resolverInfo: ResolveInfo

        //为视图指定点击事件
        init {
            nameTextView.setOnClickListener(this)
        }

        fun bindActivity(info: ResolveInfo) {
            //同名会有问题的
            this.resolverInfo = info

            nameTextView.text = info.loadLabel(itemView.context.packageManager).toString()
            appIconImageView.setImageDrawable(info.loadIcon(itemView.context.packageManager))
        }


        override fun onClick(view: View) {
            val activityInfo = resolverInfo.activityInfo
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            view.context.startActivity(intent)
        }
    }

    private class ActivityAdapter(val resolveInfoList: List<ResolveInfo>) :
        RecyclerView.Adapter<ActivityViewHolder>() {
        /**
         * 加载列表itemView，这里使用了系统自带的简单view
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.activity_list_item, parent, false)
            return ActivityViewHolder(view)
        }

        override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
            holder.bindActivity(resolveInfoList[position])
        }

        override fun getItemCount(): Int {
            return resolveInfoList.size
        }

    }
}