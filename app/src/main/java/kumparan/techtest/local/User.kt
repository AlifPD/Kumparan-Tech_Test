package kumparan.techtest.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name= "id")
    var id: Int = 0,

    @ColumnInfo(name= "username")
    var username: String? = null,

    @ColumnInfo(name= "company")
    var company: String? = null
): Parcelable