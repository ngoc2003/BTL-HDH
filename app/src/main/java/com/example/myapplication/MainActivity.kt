package com.example.myapplication
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import android.content.pm.PackageManager

data class AndroidDeviceInfo(
    val androidModel: String,
    val androidVersion: String,
    val androidDevice: String,
    val androidManufacturer: String
)

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val androidInfo = getAndroidDeviceInfo()

        setContent {
            Surface(
                color = Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplayDeviceInfo(androidInfo)
                    NotificationContent()
                }
            }
        }
    }

    private fun getAndroidDeviceInfo(): AndroidDeviceInfo {
        return AndroidDeviceInfo(
            androidModel = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE,
            androidDevice = Build.DEVICE,
            androidManufacturer = Build.MANUFACTURER
        )
    }

    @Composable
    private fun DisplayDeviceInfo(androidInfo: AndroidDeviceInfo) {
        Text(
            text = "Mã định danh thiết bị: ${androidInfo.androidModel}\n" +
                    "Tên thiết bị: ${androidInfo.androidDevice}\n" +
                    "Phiên bản Hệ điều hành: ${androidInfo.androidVersion}\n" +
                    "Nhà sản xuất sản phẩm/phần cứng: ${androidInfo.androidManufacturer}\n",
            style = MaterialTheme.typography.bodyLarge
        )
    }

    private fun createNotificationChannel() {
        val builder = NotificationCompat.Builder(this, "ch")
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setContentTitle("Hello 63CNTT1!")
            .setContentText("This is a brand new notification")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }

    @Composable
    private fun NotificationContent() {
        Button(
            onClick = { createNotificationChannel() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Show notification",
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}




