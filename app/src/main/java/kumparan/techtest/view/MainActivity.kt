package kumparan.techtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kumparan.techtest.R
import kumparan.techtest.databinding.ActivityMainBinding
import kumparan.techtest.local.User
import kumparan.techtest.network.GetPostsResponseItem
import kumparan.techtest.network.MainViewModel
import kumparan.techtest.network.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        viewModel.isLoading.observe(this){
            showLoading(it)
        }
        viewModel.getPosts()
        viewModel.listPosts.observe(this){
            for(i in it){
                i.userId?.let { it1 -> viewModel.getUsers(it1) }
            }
            viewModel.getAllUser().observe(this){listUser->
                showPosts(it, listUser)
            }
        }
    }

    private fun showPosts(it: List<GetPostsResponseItem>, listUser: List<User>) {
        binding.rvPosts.layoutManager = LinearLayoutManager(this)

        val postsAdapter = PostsAdapter(it, listUser)
        binding.rvPosts.adapter = postsAdapter

        postsAdapter.setOnItemClickCallback(object: PostsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GetPostsResponseItem) {
                Log.d("MainActivity", "onItemClicked: ${data.title} clicked")
                val toDetailIntent = Intent(this@MainActivity, DetailPostActivity::class.java)
                toDetailIntent.putExtra("DATA", data)
                startActivity(toDetailIntent)
            }

        })
    }

    private fun showLoading(b: Boolean) {
        if(b){
            binding.loadingMain.visibility = View.VISIBLE
        }else{
            binding.loadingMain.visibility = View.GONE
        }
    }
}