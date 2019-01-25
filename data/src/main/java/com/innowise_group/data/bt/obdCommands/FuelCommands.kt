package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.fuel.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class FuelCommands(private val socket: BluetoothSocket) {

    fun getCommands(): Map<String, String> {
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
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getConsumptionRate(): String {
        return try {
            val command = ConsumptionRateCommand()
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getFindFuelType(): String {
        return try {
            val command = FindFuelTypeCommand()
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getFuelLevel(): String {
        return try {
            val command = FuelLevelCommand()
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getFuelTrim(): String {
        return try {
            val command = FuelTrimCommand()
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getWidebandAirFuelRatio(): String {
        return try {
            val command = WidebandAirFuelRatioCommand()
            command.run(socket.inputStream, socket.outputStream)
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

}