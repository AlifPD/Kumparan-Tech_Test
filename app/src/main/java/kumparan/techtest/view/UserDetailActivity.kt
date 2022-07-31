package kumparan.techtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kumparan.techtest.databinding.ActivityUserDetailBinding
import kumparan.techtest.network.GetAlbumsResponseItem
import kumparan.techtest.network.MainViewModel
import kumparan.techtest.network.ViewModelFactory

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getIntExtra("DATA", 0)
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.getUsers(data)
        viewModel.users.observe(this){
            binding.tvNameDetailuser.text = it[0].name
            binding.tvUsernameDetailuser.text = it[0].username
            binding.tvEmailDetailuser.text = it[0].email
            binding.tvAddressStreetDetailuser.text = it[0].address?.street
            binding.tvAddressSuiteDetailuser.text = it[0].address?.suite
            binding.tvAddressCityDetailuser.text = it[0].address?.city
            binding.tvAddressZipcodeDetailuser.text = it[0].address?.zipcode
            binding.tvAddressGeoDetailuser.text = "${it[0].address?.geo?.lat} , ${it[0].address?.geo?.lng}"
            binding.tvCompanyDetailuser.text = it[0].company?.name
            it[0].id?.let { it1 -> viewModel.getAlbums(it1) }
        }

        viewModel.albums.observe(this){
            showAlbum(it)
        }

    }

    private fun showAlbum(it: List<GetAlbumsResponseItem>) {
        binding.rvAlbums.layoutManager = GridLayoutManager(this, 2)

        val albumsAdapter = AlbumsAdapter(it)
        binding.rvAlbums.adapter = albumsAdapter

        albumsAdapter.setOnItemClickCallback(object: AlbumsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GetAlbumsResponseItem) {
                Log.d("UserDetailActivity", "onItemClicked: ${data.title} clicked")
                val toDetailIntent = Intent(this@UserDetailActivity, DetailAlbumActivity::class.java)
                toDetailIntent.putExtra("DATA", data)
                startActivity(toDetailIntent)
            }

        })
    }

    private fun showLoading(b: Boolean) {
        if(b){
            binding.loadingUserDetail.visibility = View.VISIBLE
        }else{
            binding.loadingUserDetail.visibility = View.GONE
        }
    }
}