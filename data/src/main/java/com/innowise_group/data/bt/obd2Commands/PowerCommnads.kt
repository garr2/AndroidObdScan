package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.livedata.ControlModuleVoltage
import io.github.macfja.obd2.command.livedata.FuelInjectionTiming
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.CalculatedResponse
import io.reactivex.Observable

class PowerCommnads(private val commander: Commander) {

    fun getAllData(): Observable<String> {
        return Observable.just(getControlModuleVoltage())
    }

    fun getControlModuleVoltage(): String {
        return try {
            val resp = commander.sendCommand(ControlModuleVoltage())
            if (resp is CalculatedResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            }else{
                "ControlModuleVoltage: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "ControlModuleVoltage: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()
            "ControlModuleVoltage: $EXCEPTION $e"
        }
    }
}