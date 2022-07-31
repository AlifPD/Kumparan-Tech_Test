package kumparan.techtest.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kumparan.techtest.local.User
import kumparan.techtest.local.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application): ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listPosts = MutableLiveData<List<GetPostsResponseItem>>()
    val listPosts: LiveData<List<GetPostsResponseItem>> = _listPosts

    private val _users = MutableLiveData<List<GetUsersResponseItem>>()
    val users: LiveData<List<GetUsersResponseItem>> = _users

    private val _comments = MutableLiveData<List<GetCommentsResponseItem>>()
    val comments: LiveData<List<GetCommentsResponseItem>> = _comments

    private val _albums = MutableLiveData<List<GetAlbumsResponseItem>>()
    val albums: LiveData<List<GetAlbumsResponseItem>> = _albums

    private val _photos = MutableLiveData<List<GetPhotosResponseItem>>()
    val photos: LiveData<List<GetPhotosResponseItem>> = _photos

    private val mUserRepository: UserRepository = UserRepository(application)

    fun insertUser(user: User){
        mUserRepository.insertUser(user)
    }

    fun getAllUser(): LiveData<List<User>> {
        return mUserRepository.getAllUser()
    }

    fun getUsername(data: Int): LiveData<String> {
        return mUserRepository.getUsername(data)
    }

    fun getCompany(data: Int): LiveData<String> {
        return mUserRepository.getCompany(data)
    }

    fun getPosts(){
        _isLoading.value = true
        val client = ApiConfig().getApiService().getPosts()
        client.enqueue(object : Callback<List<GetPostsResponseItem>> {
            override fun onResponse(
                call: Call<List<GetPostsResponseItem>>,
                response: Response<List<GetPostsResponseItem>>,
            ) {
                _isLoading.value = false
                _listPosts.value = response.body()
            }

            override fun onFailure(call: Call<List<GetPostsResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun getUsers(id: Int){
        _isLoading.value = true
        val client2 = ApiConfig().getApiService().getUsers(id)
        client2.enqueue(object : Callback<List<GetUsersResponseItem>> {
            override fun onResponse(
                call: Call<List<GetUsersResponseItem>>,
                response: Response<List<GetUsersResponseItem>>,
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _users.value = response.body()

                    val user = User(id, response.body()?.get(0)?.username,
                        response.body()?.get(0)?.company?.name)
                    insertUser(user)
                }
            }

            override fun onFailure(call: Call<List<GetUsersResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun getComments(id: Int){
        _isLoading.value = true
        val client = ApiConfig().getApiService().getComments(id)
        client.enqueue(object : Callback<List<GetCommentsResponseItem>> {
            override fun onResponse(
                call: Call<List<GetCommentsResponseItem>>,
                response: Response<List<GetCommentsResponseItem>>,
            ) {
                _isLoading.value = false
                _comments.value = response.body()
            }

            override fun onFailure(call: Call<List<GetCommentsResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getAlbums(id: Int){
        _isLoading.value = true
        val client = ApiConfig().getApiService().getAlbums(id)
        client.enqueue(object : Callback<List<GetAlbumsResponseItem>> {
            override fun onResponse(
                call: Call<List<GetAlbumsResponseItem>>,
                response: Response<List<GetAlbumsResponseItem>>,
            ) {
                _isLoading.value = false
                _albums.value = response.body()
            }

            override fun onFailure(call: Call<List<GetAlbumsResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e("MainViewModel", "onFailure: ${t.message}" )
            }

        })
    }

    fun getPhotos(id: Int){
        _isLoading.value = true
        val client = ApiConfig().getApiService().getPhotos(id)
        client.enqueue(object : Callback<List<GetPhotosResponseItem>> {
            override fun onResponse(
                call: Call<List<GetPhotosResponseItem>>,
                response: Response<List<GetPhotosResponseItem>>,
            ) {
                _isLoading.value = false
                _photos.value = response.body()
            }

            override fun onFailure(call: Call<List<GetPhotosResponseItem>>, t: Throwable) {
                _isLoading.value = true
                Log.e("MainViewModel", "onFailure: ${t.message}" )
            }

        })
    }
}

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}