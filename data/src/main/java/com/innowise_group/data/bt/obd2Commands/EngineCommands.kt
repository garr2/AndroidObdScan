package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.livedata.*
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.PercentResponse
import io.reactivex.Observable
import kotlin.Exception

class EngineCommands(private val commander: Commander) {

    fun getAllData(): Observable<String> {
        /*return Observable.merge(
            Observable.just(
                getCalculatedEngineLoad(),
                getEngineRpm(),
                getTimingAdvance(),
                getAirFlowRate(),
                getThrottlePosition(),
                getCommandedSecodaryAirStatus(),
                getRunTimePerEngineStart(),
                getEnginePecentTroque(),
                getEngineReferenceTroque(),
                getCommandedEGR()
            ), Observable.just(
                getEgrError(),
                getCommandedEvaporativePruge(),
                getCatalystTemperature(),
                getAbsoluteLoadValue(),
                getRelativeThrottlePosition(),
                getMaximumAirFlowRate(),
                getRelativePedalPosition(),
                getOilTemperature(),
                getEngineFuelRate()
            )
        )*/
        return Observable.just(getCalculatedEngineLoad())
    }

    fun getCalculatedEngineLoad(): String {
        return try {
            val resp = commander.sendCommand(CalculatedEngineLoad())
            if (resp is PercentResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            } else{
                "CalculatedEngineLoad: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CalculatedEngineLoad: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()
            "CalculatedEngineLoad: $EXCEPTION $e"
        }
    }

    fun getEngineRpm(): String {
        return try {
            commander.sendCommand(EngineRPM()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EngineRpm: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EngineRpm: $EXCEPTION $e"
        }
    }

    fun getTimingAdvance(): String {
        return try {
            commander.sendCommand(TimingAdvance()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "TimingAdvance: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "TimingAdvance: $EXCEPTION $e"
        }
    }

    fun getAirFlowRate(): String {
        return try {
            commander.sendCommand(AirFlowRate()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AirFlowRate: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AirFlowRate: $EXCEPTION $e"
        }
    }

    fun getThrottlePosition(): String {
        return try {
            commander.sendCommand(ThrottlePosition()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "ThrottlePosition: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "ThrottlePosition: $EXCEPTION $e"
        }
    }

    fun getCommandedSecodaryAirStatus(): String {
        return try {
            commander.sendCommand(CommandedSecondaryAirStatus()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CommandedSecondaryAirStatus: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CommandedSecondaryAirStatus: $EXCEPTION $e"
        }
    }

    fun getRunTimePerEngineStart(): String {
        return try {
            commander.sendCommand(RuntimeSinceStart()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "RunTimePerEngineStart: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "RunTimePerEngineStart: $EXCEPTION $e"
        }
    }

    fun getCommandedEGR(): String {
        return try {
            commander.sendCommand(CommandedExhaustGasRecirculation()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CommandedEGR: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CommandedEGR: $EXCEPTION $e"
        }
    }

    fun getEgrError(): String {
        return try {
            commander.sendCommand(ExhaustGasRecirculationError()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EgrError: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EgrError: $EXCEPTION $e"
        }
    }

    fun getCommandedEvaporativePruge(): String {
        return try {
            commander.sendCommand(CommandedEvaporativePurge()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CommandedEvaporativePruge: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CommandedEvaporativePruge: $EXCEPTION $e"
        }
    }

    fun getCatalystTemperature(): String {
        var commandData = ""
        try {
            commandData = "${commander.sendCommand(CatalystTemperature.Bank1Sensor1).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CatalystTemperatureBank1Sensor1: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CatalystTemperatureBank1Sensor1: $EXCEPTION $e\n"
        }
        try {
            commandData += "${commander.sendCommand(CatalystTemperature.Bank1Sensor2).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CatalystTemperatureBank1Sensor2: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CatalystTemperatureBank1Sensor2: $EXCEPTION $e\n"
        }
        try {
            commandData += "${commander.sendCommand(CatalystTemperature.Bank2Sensor1).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CatalystTemperatureBank2Sensor1: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CatalystTemperatureBank2Sensor1: $EXCEPTION $e\n"
        }
        try {
            commandData += "${commander.sendCommand(CatalystTemperature.Bank2Sensor2).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "CatalystTemperatureBank2Sensor2: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "CatalystTemperatureBank2Sensor2: $EXCEPTION $e\n"
        }
        return commandData
    }

    fun getAbsoluteLoadValue(): String{
        return try {
            commander.sendCommand(AbsoluteLoadValue()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "AbsoluteLoadValue: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "AbsoluteLoadValue: $EXCEPTION $e"
        }
    }

    fun getRelativeThrottlePosition(): String{
        return try {
            commander.sendCommand(RelativeThrottlePosition()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "RelativeThrottlePosition: $NODATA"
        }catch (e: java.lang.Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "RelativeThrottlePosition: $EXCEPTION $e"
        }
    }

    fun getMaximumAirFlowRate(): String{
        return try {
            commander.sendCommand(MaximumAirFlowRate()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "MaximumAirFlowRate: $NODATA"
        }catch (e:Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "MaximumAirFlowRate: $EXCEPTION $e"
        }
    }

    fun getRelativePedalPosition(): String{
        return try {
            commander.sendCommand(RelativeAcceleratorPosition()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "RelativePedalPosition: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "RelativePedalPosition: $EXCEPTION $e"
        }
    }

    fun getOilTemperature(): String{
        return try {
            commander.sendCommand(EngineOilTemperature()).formattedString
        }catch (nd: NoDataResponse) {
            nd.printStackTrace()
            "OilTemperature: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "OilTemperature: $EXCEPTION $e"
        }
    }

    fun getEngineFuelRate(): String{
        return try {
            commander.sendCommand(EngineFuelRate()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EngineFuelRate: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EngineFuelRate: $EXCEPTION $e"
        }
    }

    fun getEnginePecentTroque(): String{
        return try {
            commander.sendCommand(ActualEngineTorque()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EnginePecentTroque: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EnginePecentTroque: $EXCEPTION $e"
        }
    }

    fun getEngineReferenceTroque(): String{
        return try {
            commander.sendCommand(EngineReferenceTorque()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EngineReferenceTroque: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EngineReferenceTroque: $EXCEPTION $e"
        }
    }
}