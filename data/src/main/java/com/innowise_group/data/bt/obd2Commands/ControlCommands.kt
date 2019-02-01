package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.livedata.*
import io.github.macfja.obd2.command.vehicle_info.ECUName
import io.github.macfja.obd2.command.vehicle_info.VINMessageCount
import io.github.macfja.obd2.command.vehicle_info.VehicleIdentificationNumber
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.CalculatedResponse
import io.reactivex.Observable

class ControlCommands(private val commander: Commander){

    val arr = setOf<Any>()

    fun getAllData(): Observable<String> {
        return Observable.just(
                getDistanceWithMilOn()/*,
                getTimeWithMilOn(),
                getTimeSinceTroubleCodeCleared(),
                getObdStandarts(),
                getAuxilaryInputStatus(),
                getHybridPackBatteryRemaining(),
                getVinCount(),
                getVehicleIdentificationNumber(),
                getEcuName()*/
        )
    }

    fun getDistanceWithMilOn(): String {
        return try {
            val resp = commander.sendCommand(DistanceWithMIL())
            if (resp is CalculatedResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            }else {
                "DistanceWithMillOn: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "DistanceWithMillOn: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()
            "DistanceWithMillOn: $EXCEPTION $e"
        }

    }

    fun getTimeWithMilOn(): String {
        return try {
            commander.sendCommand(TimeWithMIL()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "TimeWithMilOn: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "TimeWithMilOn: $EXCEPTION $e"
        }
    }

    fun getTimeSinceTroubleCodeCleared(): String {
        return try {
            commander.sendCommand(TimeSinceLastClean()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "TimeSinceTroubleCodesCleared: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "TimeSinceTroubleCodesCleared: $EXCEPTION $e"
        }
    }

    fun getEngineRpm(): String {
        return try {
            commander.sendCommand(EngineRPM()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EngineRPM: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EngineRPM: $EXCEPTION $e"
        }
    }

    fun getVehicleSpeed(): String {
        return try {
            commander.sendCommand(VehicleSpeed()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "VehicleSpeed: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "VehicleSpeed: $EXCEPTION $e"
        }
    }

    fun getObdStandarts(): String {
        return try {
            commander.sendCommand(OBDStandards()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "ObdStandards: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "ObdStandards: $EXCEPTION $e"
        }
    }

    fun getAuxilaryInputStatus(): String {
        return try {
            commander.sendCommand(AuxilaryInputStatus()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AuxiliaryInputStatus: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AuxiliaryInputStatus: $EXCEPTION $e"
        }
    }

    fun getHybridPackBatteryRemaining(): String {
        return try {
            commander.sendCommand(HybridBatteryRemaining()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "HybridPackBatteryRemaining: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "HybridPackBatteryRemaining: $EXCEPTION $e"
        }
    }

    fun getVinCount(): String {
        return try {
            commander.sendCommand(VINMessageCount()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "VinCodeCount: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "VinCodeCount: $EXCEPTION $e"
        }
    }

    fun getVehicleIdentificationNumber(): String {
        return try {
            commander.sendCommand(VehicleIdentificationNumber()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "VinCode: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "VinCode: $EXCEPTION $e"
        }
    }

    fun getEcuName(): String {
        return try {
            commander.sendCommand(ECUName()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EcuName: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EcuName: $EXCEPTION $e"
        }
    }
}