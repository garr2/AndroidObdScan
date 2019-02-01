package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.innowise_group.data.bt.EXCEPTION
import com.innowise_group.data.bt.NODATA
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.Response
import io.github.macfja.obd2.command.livedata.*
import io.github.macfja.obd2.elm327.response.NoDataResponse
import io.github.macfja.obd2.response.FuelSystemStatusResponse
import io.reactivex.Observable

class FuelCommands(private val commander: Commander) {

    fun getAllFuelData(): Observable<String> {
        /*return Observable.merge(
            Observable.just(
                getFuelSystemStatus(),
                getShortFuelTrimBank1(),
                getShortFuelTrimBank2(),
                getLongFuelTrimBank1(),
                getLongFuelTrimBank2(),
                getFuelTankLevelInput(),
                getFuelPressure(),
                getFuelRailGaugePressure(),
                getFuelRailPressure(),
                getFuelAirEquivalenceRatio()
            ),
            Observable.just(
                getFuel_AirCommandedRatio(),
                getFuelType(),
                getEthanolFuel(),
                getFuelRailAbsolytePressure(),
                getFuelInjectionTiming()
            )
        )*/
        return Observable.just(getFuelSystemStatus())
    }

    fun getFuelSystemStatus(): String {
        return try {
            val resp = commander.sendCommand(FuelSystemStatus())
            if (resp is FuelSystemStatusResponse){
                Crashlytics.logException(Exception(resp.formattedString))
                resp.formattedString
            }else{
                "FuelSystemStatus: $NODATA"
            }
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelSystemStatus: $NODATA"
        }catch (e: Exception){
            Crashlytics.logException(e)
            e.printStackTrace()
            "FuelSystemStatus: $EXCEPTION $e"
        }
    }

    fun getShortFuelTrimBank1(): String {
        return try {
            commander.sendCommand(FuelTrim.ShortTermBank1).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "ShortFuelTrimBank1: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "ShortFuelTrimBank1: $EXCEPTION $e"
        }
    }

    fun getShortFuelTrimBank2(): String {
        return try {
            commander.sendCommand(FuelTrim.ShortTermBank2).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "ShortFuelTrimBank2: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "ShortFuelTrimBank2: $EXCEPTION $e"
        }
    }

    fun getLongFuelTrimBank1(): String {
        return try {
            commander.sendCommand(FuelTrim.LongTermBank1).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "LongFuelTrimBank1: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "LongFuelTrimBank1: $EXCEPTION $e"
        }
    }

    fun getLongFuelTrimBank2(): String {
        return try {
            commander.sendCommand(FuelTrim.LongTermBank2).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "LongFuelTrimBank2: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "LongFuelTrimBank2: $EXCEPTION $e"
        }
    }

    fun getFuelTankLevelInput(): String {
        return try {
            commander.sendCommand(FuelLevel()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelTankLevelInput: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelTankLevelInput: $EXCEPTION $e"
        }
    }

    fun getFuelPressure(): String {
        return try {
            commander.sendCommand(FuelPressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelPressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelPressure: $EXCEPTION $e"
        }
    }

    fun getFuelRailGaugePressure(): String {
        return try {
            commander.sendCommand(FuelRailGaugePressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelRailGaugePressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelRailGaugePressure: $EXCEPTION $e"
        }
    }

    fun getFuelRailPressure(): String {
        return try {
            commander.sendCommand(FuelRailAbsolutePressure()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelRailPressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelRailPressure: $EXCEPTION $e"
        }
    }

    fun getFuelAirEquivalenceRatio(): String {
        var commandData = ""
        try {
            commandData = "${commander.sendCommand(OxygenSensor.FuelAirVoltageB1S1).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelAirEquivalenceRatioBank1Sensor1: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelAirEquivalenceRatioBank1Sensor1: $EXCEPTION $e\n"
        }
        try {
            commandData += "${commander.sendCommand(OxygenSensor.FuelAirVoltageB1S2).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelAirEquivalenceRatioBank1Sensor2: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelAirEquivalenceRatioBank1Sensor2: $EXCEPTION $e\n"
        }
        try {
            commandData += "${commander.sendCommand(OxygenSensor.FuelAirVoltageB2S1).formattedString}\n"
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelAirEquivalenceRatioBank2Sensor1: $NODATA\n"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelAirEquivalenceRatioBank2Sensor1: $EXCEPTION $e\n"
        }
        try {
            commandData += commander.sendCommand(OxygenSensor.FuelAirVoltageB2S2).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelAirEquivalenceRatioBank2Sensor2: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelAirEquivalenceRatioBank2Sensor2: $EXCEPTION $e"
        }
        return commandData
    }

    fun getFuel_AirCommandedRatio(): String {
        return try {
            commander.sendCommand(FuelAirCommandedRatio()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "Fuel_AirCommandedRatio: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "Fuel_AirCommandedRatio: $EXCEPTION $e"
        }
    }

    fun getFuelType(): String {
        return try {
            commander.sendCommand(FuelType()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelType: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelType: $EXCEPTION $e"
        }
    }

    fun getEthanolFuel(): String {
        return try {
            commander.sendCommand(EthanolPercent()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "EthanolFuel: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "EthanolFuel: $EXCEPTION $e"
        }
    }

    fun getFuelRailAbsolytePressure(): String {
        return try {
            (commander.sendCommand(FuelRailAbsolutePressure()).formattedString)
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelRailAbsolytePressure: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelRailAbsolytePressure: $EXCEPTION $e"
        }
    }

    fun getFuelInjectionTiming(): String {
        return try {
            commander.sendCommand(FuelInjectionTiming()).formattedString
        }catch (nd: NoDataResponse){
            nd.printStackTrace()
            "FuelInjectionTiming: $NODATA"
        }catch (e: Exception){
            e.printStackTrace()
            Crashlytics.logException(e)
            "FuelInjectionTiming: $EXCEPTION $e"
        }
    }
}