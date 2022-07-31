package kumparan.techtest.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT username from User WHERE id = :data")
    fun getUsername(data: Int): LiveData<String>

    @Query("SELECT company from User WHERE id = :data")
    fun getCompany(data: Int): LiveData<String>

    @Query("SELECT * from User ORDER BY id ASC")
    fun getAllUser(): LiveData<List<User>>
}