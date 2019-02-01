package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.engine.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class EngineCommands(private val socket: BluetoothSocket) {

    fun getAllDAta(): Map<String, String> {
        val map = HashMap<String, String>()
        map[ABSOLUTE_LOAD] = getAbsoluteLoad()
        map[LOAD] = getLoad()
        map[MASS_AIR_FLOW] = getMassAirFlow()
        map[OIL_TEMP] = getOilTemp()
        map[RPM] = getRPM()
        map[RUNTIME] = getRuntime()
        map[THROTTLE_POSITION] = getThrottlePosition()
        return map
    }

    private fun getAbsoluteLoad(): String {
        return try {
            val command = AbsoluteLoadCommand()
            command.run(socket.inputStream, socket.outputStream)
            "AbsoluteLoad: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AbsoluteLoad: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AbsoluteLoad: $NODATA"
        }
    }

    private fun getLoad(): String {
        return try {
            val command = LoadCommand()
            command.run(socket.inputStream, socket.outputStream)
            "Load: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "Load: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "Load: $NODATA"
        }
    }

    private fun getMassAirFlow(): String {
        return try {
            val command = MassAirFlowCommand()
            command.run(socket.inputStream, socket.outputStream)
            "MassAirFlow: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "MassAirFlow: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "MassAirFlow: $NODATA"
        }
    }

    private fun getOilTemp(): String {
        return try {
            val command = OilTempCommand()
            command.run(socket.inputStream, socket.outputStream)
            "OilTemp: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "OilTemp: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "OilTemp: $NODATA"
        }
    }

    private fun getRPM(): String {
        return try {
            val command = RPMCommand()
            command.run(socket.inputStream, socket.outputStream)
        "RPM: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "RPM: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "RPM: $NODATA"
        }
    }

    private fun getRuntime(): String {
        return try {
            val command = RuntimeCommand()
            command.run(socket.inputStream, socket.outputStream)
            "Runtime: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "Runtime: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "Runtime: $NODATA"
        }
    }

    private fun getThrottlePosition(): String {
        return try {
            val command = ThrottlePositionCommand()
            command.run(socket.inputStream, socket.outputStream)
            "ThrottlePosition: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "ThrottlePosition: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "ThrottlePosition: $NODATA"
        }
    }
}