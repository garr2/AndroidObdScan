package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.control.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*

class ControlCommands(private val socket: BluetoothSocket) {

    fun getCommands(): Map<String, String> {
        val map = HashMap<String, String>()
        map[DISTANCE_MIL_ON] = getDistanceMilOn()
        map[DISTANCE_SINCE_CC] = getDistanceSinceCC()
        map[DTC_NUMBER] = getDtcNumber()
        map[EQUIVALENT_RATIO] = getEquivalentRatio()
        map[IGNITION_MONITOR] = getIgnitionMonitor()
        map[MODULE_VOLTAGE] = getModuleVoltage()
        //map[PENDING_TROUBLE_CODES] = getPendingTroubleCodes()
            //map[PERMANENT_TROUBLE_CODES] = getPermanentTroubleCodes()
        map[TIMING_ADVANCED] = getTimingAdvance()
        map[TROUBLE_CODES] = getTroubleCodes()
        map[VIN] = getVin()
        return map
    }

    private fun getDistanceMilOn(): String {
        return try {
            val command = DistanceMILOnCommand()
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

    private fun getDistanceSinceCC(): String {
        return try {
            val command = DistanceSinceCCCommand()
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

    private fun getDtcNumber(): String {
        return try {
            val command = DtcNumberCommand()
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

    private fun getEquivalentRatio(): String {
        return try {
            val command = EquivalentRatioCommand()
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

    private fun getIgnitionMonitor(): String {
        return try {
            val command = IgnitionMonitorCommand()
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

    private fun getModuleVoltage(): String {
        return try {
            val command = ModuleVoltageCommand()
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

    private fun getPendingTroubleCodes(): String {
        return try {
            val command = PendingTroubleCodesCommand()
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

    private fun getPermanentTroubleCodes(): String {
        return try {
            val command = PermanentTroubleCodesCommand()
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

    private fun getTimingAdvance(): String {
        return try {
            val command = TimingAdvanceCommand()
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

    private fun getTroubleCodes(): String {
        return try {
            val command = TroubleCodesCommand()
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

    private fun getVin(): String {
        return try {
            val command = VinCommand()
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