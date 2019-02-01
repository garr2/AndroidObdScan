package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.livedata.AmbientAirTemperature
import io.github.macfja.obd2.command.livedata.EngineCoolantTemperature
import io.github.macfja.obd2.command.livedata.EvapSystemVaporAbsolutePressure
import io.github.macfja.obd2.command.livedata.IntakeAirTemperature
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.TemperatureResponse
import io.reactivex.Observable

class TemperatureCommands(private val commander: Commander) {

    fun getAllData(): Observable<String> {
        return Observable.just(
            getEngineCoolantTemperature()/*,
            getIntakeAirTemperature(),
            getAmbientAirTemperature()*/
        )
    }

    fun getEngineCoolantTemperature(): String {
        return try {
            val resp = commander.sendCommand(EngineCoolantTemperature())
            if (resp is TemperatureResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            }else{
                "EngineCoolantTemperature: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EngineCoolantTemperature: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()
            "EngineCoolantTemperature: $EXCEPTION $e"
        }
    }

    fun getIntakeAirTemperature(): String {
        return try {
            commander.sendCommand(IntakeAirTemperature()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "IntakeAirTemperature: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "IntakeAirTemperature: $EXCEPTION $e"
        }
    }

    fun getAmbientAirTemperature(): String {
        return try {
            commander.sendCommand(AmbientAirTemperature()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AmbientAirTemperature: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AmbientAirTemperature: $EXCEPTION $e"
        }
    }
}