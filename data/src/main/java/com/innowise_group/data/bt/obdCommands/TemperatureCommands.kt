package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand
import com.github.pires.obd.commands.temperature.AmbientAirTemperatureCommand
import com.github.pires.obd.commands.temperature.EngineCoolantTemperatureCommand
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.AIR_INTAKE_TEMPERATURE
import com.innowise_group.data.bt.AMBIENT_AIR_TEMPERATURE
import com.innowise_group.data.bt.ENGINE_COOLANT_TEMPERATURE
import com.innowise_group.data.bt.NODATA

class TemperatureCommands(private val socket: BluetoothSocket) {

    fun getAllDAta(): Map<String, String> {
        val map = HashMap<String, String>()
        map[AIR_INTAKE_TEMPERATURE] = getAirIntakeTemperature()
        map[AMBIENT_AIR_TEMPERATURE] = getAmbientAirTemperature()
        map[ENGINE_COOLANT_TEMPERATURE] = getEngineCoolantTemperature()
        return map
    }

    private fun getAirIntakeTemperature(): String {
        return try {
            val command = AirIntakeTemperatureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "AirIntakeTemperature: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AirIntakeTemperature: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AirIntakeTemperature: $NODATA"
        }
    }

    private fun getAmbientAirTemperature(): String {
        return try {
            val command = AmbientAirTemperatureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "AmbientAirTemperature: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AmbientAirTemperature: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AmbientAirTemperature: $NODATA"
        }
    }

    private fun getEngineCoolantTemperature(): String {
        return try {
            val command = EngineCoolantTemperatureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "EngineCoolantTemperature: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "EngineCoolantTemperature: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "EngineCoolantTemperature: $NODATA"
        }
    }
}