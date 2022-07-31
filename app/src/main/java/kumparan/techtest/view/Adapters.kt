package kumparan.techtest.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import kumparan.techtest.databinding.ItemAlbumsBinding
import kumparan.techtest.databinding.ItemCommentsBinding
import kumparan.techtest.databinding.ItemPhotosBinding
import kumparan.techtest.databinding.ItemPostBinding
import kumparan.techtest.local.User
import kumparan.techtest.network.GetAlbumsResponseItem
import kumparan.techtest.network.GetCommentsResponseItem
import kumparan.techtest.network.GetPhotosResponseItem
import kumparan.techtest.network.GetPostsResponseItem

class PostsAdapter(
    private val listPosts: List<GetPostsResponseItem>,
    private val listUser: List<User>,
) :
    RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitleItem.text = listPosts[position].title
        holder.binding.tvBodyItem.text = listPosts[position].body

        Log.d("Adapter", "onBindViewHolder: $listUser")
        listUser.forEach {
            if (listPosts[position].userId == it.id) {
                holder.binding.tvUsernameItem.text = it.username
                holder.binding.tvCompanyItem.text = it.company
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listPosts[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listPosts.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GetPostsResponseItem)
    }
}

class CommentsAdapter(private val listComments: List<GetCommentsResponseItem>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCommentsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvBodyComment.text = listComments[position].body
        holder.binding.tvNameComment.text = listComments[position].name
    }

    override fun getItemCount(): Int = listComments.size
}

class AlbumsAdapter(private val listAlbums: List<GetAlbumsResponseItem>) :
    RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemAlbumsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvAlbumDesc.text = "Album - ${listAlbums[position].id}"
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listAlbums[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listAlbums.size

    interface OnItemClickCallback {
        fun onItemClicked(data: GetAlbumsResponseItem)
    }
}

class PhotosAdapter(private val listPhotos: List<GetPhotosResponseItem>):
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemPhotosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theImage = GlideUrl(
            listPhotos[position].url, LazyHeaders.Builder()
                .addHeader("User-Agent", "5")
                .build()
        )
        Glide.with(holder.binding.root.context)
            .load(theImage)
            .apply(RequestOptions.overrideOf(300, 300))
            .into(holder.binding.imgPhotosDetail)
    }

    override fun getItemCount(): Int = listPhotos.size
}