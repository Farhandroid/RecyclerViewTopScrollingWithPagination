package com.example.chatlikescrollingmedium

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatlikescrollingmedium.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var currentPage = 1
    private val recordPerPage = 20
    lateinit var mLayoutManger: LinearLayoutManager
    lateinit var recyclerAdapter: RecyclerViewAdapter
    private var isLoadMore = false
    private lateinit var dataBinding: ActivityMainBinding
    private val firstVisibleItemPosition: Int
        get() = mLayoutManger.findFirstCompletelyVisibleItemPosition()

    private val dataSet: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initRecyclerView()
        generateData()
    }

    private fun initRecyclerView() {
        mLayoutManger = LinearLayoutManager(this@MainActivity)
        recyclerAdapter = RecyclerViewAdapter()
        dataBinding.recyclerView.apply {
            layoutManager = mLayoutManger
            adapter = recyclerAdapter
        }
        initRecyclerViewScrollListener()
    }

    private fun initRecyclerViewScrollListener() {
        dataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0 && firstVisibleItemPosition == 0 && !isLoadMore) {
                    loadMoreData()
                }
            }
        })
    }

    private fun generateData() {
        for (i in 0..recordPerPage) {
            dataSet.add(0, "chat message $i")
        }
        recyclerAdapter.setData(dataSet)
    }

    private fun loadMoreData() {
        if (!isLoadMore) {
            dataBinding.loadMoreProgressbar.visibility = View.VISIBLE
            isLoadMore = true
            currentPage++
            for (i in dataSet.size - 1..currentPage * recordPerPage) {
                dataSet.add(0, "chat message $i")
            }

            Handler(Looper.getMainLooper()).postDelayed({
                dataBinding.recyclerView.post {
                    recyclerAdapter.setData(ArrayList(dataSet.subList(0, recordPerPage)))
                }
                isLoadMore = false
                dataBinding.loadMoreProgressbar.visibility = View.INVISIBLE
            }, 1000)
        }
    }
}