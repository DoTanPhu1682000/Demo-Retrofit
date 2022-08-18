package com.dotanphu.demoretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dotanphu.demoretrofit.adapter.CommentAdapter
import com.dotanphu.demoretrofit.databinding.ActivityMainBinding
import com.dotanphu.demoretrofit.model.CommentItem
import com.dotanphu.demoretrofit.network.CommentClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CommentAdapter
    private var commentList = arrayListOf<CommentItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()
//        getAllCommentSync()
//        getAllCommentAsync()
//        getAllCommentWithCoroutines()
        getAllCommentWithRx()
    }

    private fun getAllCommentWithRx() {
        CommentClient.invoke().getAllCommentWithRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{data ->
                commentList.addAll(data)
                adapter.notifyDataSetChanged()
            }
        //here is result data
    }

    private fun getAllCommentWithCoroutines() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = CommentClient.invoke().getAllCommentWithCoroutines()
            commentList.addAll(data)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getAllCommentAsync() {
        CommentClient.invoke().getAllComment().enqueue(object : Callback<List<CommentItem>> {
            override fun onResponse(
                call: Call<List<CommentItem>>, response: Response<List<CommentItem>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        commentList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<CommentItem>>, t: Throwable) {
                Log.d("phudt", "${t.message}")
                t.printStackTrace()
            }

        })
    }

    private fun getAllCommentSync() {
        lifecycleScope.launch(Dispatchers.IO) {
            val response: Response<List<CommentItem>> =
                CommentClient.invoke().getAllComment().execute()
            if (response.isSuccessful) {
                val data = response.body()
                data?.let {
                    commentList.addAll(it)
                    withContext(Dispatchers.Main) {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun setupRV() {
        adapter = CommentAdapter(commentList)
        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}