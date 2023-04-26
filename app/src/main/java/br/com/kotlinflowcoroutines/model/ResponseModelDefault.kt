
import com.google.gson.annotations.SerializedName

open class ResponseModelDefault {
    @SerializedName("Status")
    var status = 0

    @SerializedName("Mensagem")
    var message: String? = null

    @SerializedName("Erro")
    var error: String? = null
}