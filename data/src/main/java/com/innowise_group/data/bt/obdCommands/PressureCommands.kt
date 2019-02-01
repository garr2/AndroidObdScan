package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.pressure.BarometricPressureCommand
import com.github.pires.obd.commands.pressure.FuelPressureCommand
import com.github.pires.obd.commands.pressure.IntakeManifoldPressureCommand
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class PressureCommands(private val socket: BluetoothSocket) {

    fun getAllData(): Map<String, String> {
        val map = HashMap<String, String>()
        map[BAROMETRIC_PRESSURE] = getBarometricPressure()
        map[FUEL_PRESSURE] = getFuelPressure()
        map[FUEL_RAIL_PRESSURE] = getFuelRailPressure()
        map[INTAKE_MANIFOLD_PRESSURE] = getIntakeManifoldPressure()
        return map
    }

    private fun getBarometricPressure(): String {
        return try {
            val command = BarometricPressureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "BarometricPressure: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "BarometricPressure: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "BarometricPressure: $NODATA"
        }
    }

    private fun getFuelPressure(): String {
        return try {
            val command = FuelPressureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelPressure: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelPressure: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelPressure: $NODATA"
        }
    }

    private fun getFuelRailPressure(): String {
        return try {
            val command = FuelPressureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "FuelRailPressure: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "FuelRailPressure: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "FuelRailPressure: $NODATA"
        }
    }

    private fun getIntakeManifoldPressure(): String {
        return try {
            val command = IntakeManifoldPressureCommand()
            command.run(socket.inputStream, socket.outputStream)
            "IntaceManifoldPressure: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "IntaceManifoldPressure: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "IntaceManifoldPressure: $NODATA"
        }
    }
}