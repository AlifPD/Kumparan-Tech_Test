package kumparan.techtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kumparan.techtest.databinding.ActivityDetailPostBinding
import kumparan.techtest.network.GetCommentsResponseItem
import kumparan.techtest.network.GetPostsResponseItem
import kumparan.techtest.network.MainViewModel
import kumparan.techtest.network.ViewModelFactory

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<GetPostsResponseItem>("DATA")
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.tvTitleDetail.text = data?.title
        binding.tvBodyDetail.text = data?.body

        data?.userId?.let { viewModel.getUsers(it) }
        viewModel.users.observe(this){
            binding.tvUsernameDetail.text = "${it[0].username}"
        }
        binding.tvUsernameDetail.setOnClickListener{
            val toUserDetailIntent = Intent(this@DetailPostActivity, UserDetailActivity::class.java)
            toUserDetailIntent.putExtra("DATA", data?.userId)
            startActivity(toUserDetailIntent)
        }

        data?.id?.let { viewModel.getComments(it) }
        viewModel.comments.observe(this){
            showComments(it)
        }

    }

    private fun showComments(it: List<GetCommentsResponseItem>) {
        binding.rvComments.layoutManager = LinearLayoutManager(this)

        val commentsAdapter = CommentsAdapter(it)
        binding.rvComments.adapter = commentsAdapter
    }

    private fun showLoading(b: Boolean) {
        if(b){
            binding.loadingDetailPost.visibility = View.VISIBLE
        }else{
            binding.loadingDetailPost.visibility = View.GONE
        }
    }
}