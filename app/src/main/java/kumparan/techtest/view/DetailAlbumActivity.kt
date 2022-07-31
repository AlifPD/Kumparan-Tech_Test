package kumparan.techtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kumparan.techtest.databinding.ActivityDetailAlbumBinding
import kumparan.techtest.network.GetAlbumsResponseItem
import kumparan.techtest.network.GetPhotosResponseItem
import kumparan.techtest.network.MainViewModel
import kumparan.techtest.network.ViewModelFactory

class DetailAlbumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAlbumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAlbumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<GetAlbumsResponseItem>("DATA")
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[MainViewModel::class.java]

        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.tvAlbumDetailDesc.text = "Album - ${data?.id}"
        data?.id?.let { viewModel.getPhotos(it) }
        viewModel.photos.observe(this){
            showPhotos(it)
        }
    }

    private fun showPhotos(it: List<GetPhotosResponseItem>) {
        binding.rvThumbnailDetail.layoutManager = GridLayoutManager(this, 3)

        val photosAdapter = PhotosAdapter(it)
        binding.rvThumbnailDetail.adapter = photosAdapter
    }

    private fun showLoading(b: Boolean) {
        if(b){
            binding.loadingDetailAlbum.visibility = View.VISIBLE
        }else{
            binding.loadingDetailAlbum.visibility = View.GONE
        }
    }
}