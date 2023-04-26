
import com.google.gson.annotations.SerializedName

class UserModel : ResponseModelDefault() {

    @SerializedName("IDUsuario")
    val userId = 0

    @SerializedName("Token")
    val token: String? = null

    @SerializedName("Nome")
    val name: String? = null
}
