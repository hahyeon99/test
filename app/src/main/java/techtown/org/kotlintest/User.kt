package techtown.org.kotlintest

import android.net.Uri

data class User(
    var email: String,
    var uId: String,
    var id: String,
    var nickname: String,
    var password: String,
    var imageUri: String
){
    constructor(): this("","","","", "","")
}
