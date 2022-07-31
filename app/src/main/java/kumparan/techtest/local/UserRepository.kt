package kumparan.techtest.local

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.noteDao()
    }

    fun getUsername(data: Int): LiveData<String> = mUserDao.getUsername(data)
    fun getCompany(data: Int): LiveData<String> = mUserDao.getCompany(data)
    fun getAllUser(): LiveData<List<User>> = mUserDao.getAllUser()
    fun insertUser(user: User) {
        executorService.execute { mUserDao.insertUser(user) }
    }
    fun update(user: User) {
        executorService.execute { mUserDao.update(user) }
    }
}