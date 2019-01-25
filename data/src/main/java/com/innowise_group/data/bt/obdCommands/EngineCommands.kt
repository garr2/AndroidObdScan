package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.engine.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class EngineCommands(private val socket: BluetoothSocket) {

    fun getCommands(): Map<String, String> {
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
            command.formattedResult
        } catch (n: NoDataException) {
            n.printStackTrace()
            NODATA
        } catch (e: Exception) {
            Crashlytics.logException(e)
            NODATA
        }
    }

    private fun getLoad(): String {
        return try {
            val command = LoadCommand()
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

    private fun getMassAirFlow(): String {
        return try {
            val command = MassAirFlowCommand()
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

    private fun getOilTemp(): String {
        return try {
            val command = OilTempCommand()
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

    private fun getRPM(): String {
        return try {
            val command = RPMCommand()
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

    private fun getRuntime(): String {
        return try {
            val command = RuntimeCommand()
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

    private fun getThrottlePosition(): String {
        return try {
            val command = ThrottlePositionCommand()
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