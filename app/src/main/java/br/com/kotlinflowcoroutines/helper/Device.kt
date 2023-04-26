
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class Device (private val context: Context){

    fun getAppVersion(): String {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
             ""
        }
    }

    fun getDeviceToken() : String{
        return "124"
    }
    fun getType(): Int {
        return Constants.DEVICE.ANDROID
    }

    fun getIMEI(): String {
        return "0000"
    }

    fun getAppPackage(): String? {
        return context.applicationContext.packageName
    }

    fun getNameAndModel(): String {
        return try {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            val version = Build.VERSION.RELEASE

            if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                capitalize(String.format("%s ANDROID:%s", model, version))
            } else {
                String.format("%s %s ANDROID:%s ", capitalize(manufacturer), model, version)
            }
        } catch (ex: Exception) {
            ""
        }
    }

    private fun capitalize(s: String): String {
        if (s.isEmpty()) {
            return s
        }
        val first = s[0]
        if (Character.isUpperCase(first)) {
            return s
        }
        return Character.toUpperCase(first) + s.substring(1)
    }

}