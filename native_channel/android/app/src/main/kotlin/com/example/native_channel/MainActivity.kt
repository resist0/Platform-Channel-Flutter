package com.example.native_channel

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    // Constante com o nome do canal
    private val CHANNEL = "exemplo.flutter.dev/geral"

    // Criar um método que acessa o recurso Nativo
    private fun getBatteryLevel(): Int {
        val batteryLevel: Int
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryLevel = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
        return batteryLevel
    }




    private fun getNetworkDetails(): String {
        var retorno = "";
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            connMgr.allNetworks.forEach { network ->
                connMgr.getNetworkInfo(network).apply {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        retorno = "Wi-Fi"
                    }
                    if (type == ConnectivityManager.TYPE_MOBILE) {
                        retorno = "Rede móvel"
                    }
                }
            }
        } else {
            retorno = "Versão não suportada"
        }

        return retorno;
    }


    // Sobrescrever o método do Channel
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "getBatteryLevel") {
                val batteryLevel = getBatteryLevel()
                if (batteryLevel != -1) {
                    result.success(batteryLevel)
                } else {
                    result.error("Erro Bateria", "Nível da bateria não disponível", null)
                }
            } else if (call.method == "getNetworkDetails") {
                val networkType = getNetworkDetails();
                if (networkType.isNotBlank() && networkType.isNotEmpty()) {
                    result.success(networkType)
                } else {
                    result.error("Erro Rede", "Não foi possível obter o tipo de rede", null)
                }
            }
        }
    }

}