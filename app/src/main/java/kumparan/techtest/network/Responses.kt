package kumparan.techtest.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetPostsResponse(

    @field:SerializedName("GetPostsResponse")
    val getPostsResponse: List<GetPostsResponseItem?>? = null
)

@Parcelize
data class GetPostsResponseItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null
): Parcelable

data class GetUsersResponse(

    @field:SerializedName("GetUsersResponse")
    val getUsersResponse: List<GetUsersResponseItem?>? = null
)

data class Company(

    @field:SerializedName("bs")
    val bs: String? = null,

    @field:SerializedName("catchPhrase")
    val catchPhrase: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

data class Address(

    @field:SerializedName("zipcode")
    val zipcode: String? = null,

    @field:SerializedName("geo")
    val geo: Geo? = null,

    @field:SerializedName("suite")
    val suite: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("street")
    val street: String? = null
)

data class GetUsersResponseItem(

    @field:SerializedName("website")
    val website: String? = null,

    @field:SerializedName("address")
    val address: Address? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: Company? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)

data class Geo(

    @field:SerializedName("lng")
    val lng: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null
)

data class GetCommentsResponse(

    @field:SerializedName("GetCommentsResponse")
    val getCommentsResponse: List<GetCommentsResponseItem?>? = null
)

data class GetCommentsResponseItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("postId")
    val postId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class GetAlbumsResponse(

    @field:SerializedName("GetAlbumsResponse")
    val getAlbumsResponse: List<GetAlbumsResponseItem?>? = null
)

@Parcelize
data class GetAlbumsResponseItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null
): Parcelable

data class GetPhotosResponse(

    @field:SerializedName("GetPhotosResponse")
    val getPhotosResponse: List<GetPhotosResponseItem?>? = null
)

@Parcelize
data class GetPhotosResponseItem(

    @field:SerializedName("albumId")
    val albumId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("thumbnailUrl")
    val thumbnailUrl: String? = null
): Parcelable