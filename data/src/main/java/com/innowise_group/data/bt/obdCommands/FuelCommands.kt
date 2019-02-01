package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.fuel.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class FuelCommands(private val socket: BluetoothSocket) {

    fun getAllData(): Map<String, String> {
        val map = HashMap<String, String>()
        map[AIR_FUEL_RATIO] = getAirFuelRatio()
        map[CONSUMPTION_RATE] = getConsumptionRate()
        map[FIND_FUEL_TYPE] = getFindFuelType()
        map[FUEL_LEVEL] = getFuelLevel()
        map[FUEL_TRIM] = getFuelTrim()
        map[WIDEBAND_AIR_FUEL_RATIO] = getWidebandAirFuelRatio()
        return map
    }

    private fun getAirFuelRatio(): String {
        return try {
            val command = AirFuelRatioCommand()
            command.run(socket.inputStream, socket.outputStream)
            "AirFuelRatio: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AirFuelRatio: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AirFuelRatio: $NODATA"
        }
    }

    private fun getConsumptionRate(): String {
        return try {
            val command = ConsumptionRateCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelConsumption: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelConsumption: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelConsumption: $NODATA"
        }
    }

    private fun getFindFuelType(): String {
        return try {
            val command = FindFuelTypeCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelType: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelType: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelType: $NODATA"
        }
    }

    private fun getFuelLevel(): String {
        return try {
            val command = FuelLevelCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelLevel: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelLevel: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelLevel: $NODATA"
        }
    }

    private fun getFuelTrim(): String {
        return try {
            val command = FuelTrimCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelTrim: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelTrim: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelTrim: $NODATA"
        }
    }

    private fun getWidebandAirFuelRatio(): String {
        return try {
            val command = WidebandAirFuelRatioCommand()
            command.run(socket.inputStream, socket.outputStream)
            "WidebandAirFuelRatio: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "WidebandAirFuelRatio: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "WidebandAirFuelRatio: $NODATA"
        }
    }

}