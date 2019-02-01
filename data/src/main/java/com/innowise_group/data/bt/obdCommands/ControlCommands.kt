package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.control.*
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.*
import com.innowise_group.data.bt.extLibraryCommands.LocalPendingTroubleCodesCommand
import com.innowise_group.data.bt.extLibraryCommands.LocalPermanentTroubleCodesCommand
import com.innowise_group.data.bt.extLibraryCommands.LocalTroubleCodesCommand

class ControlCommands(private val socket: BluetoothSocket) {

    fun getAllData(): Map<String, String> {
        val map = HashMap<String, String>()
        map[DISTANCE_MIL_ON] = getDistanceMilOn()
        map[DISTANCE_SINCE_CC] = getDistanceSinceCC()
        map[DTC_NUMBER] = getDtcNumber()
        map[EQUIVALENT_RATIO] = getEquivalentRatio()
        map[IGNITION_MONITOR] = getIgnitionMonitor()
        map[MODULE_VOLTAGE] = getModuleVoltage()
        map[PENDING_TROUBLE_CODES] = getPendingTroubleCodes()
        map[PERMANENT_TROUBLE_CODES] = getPermanentTroubleCodes()
        map[TIMING_ADVANCED] = getTimingAdvance()
        map[TROUBLE_CODES] = getTroubleCodes()
        map[VIN] = getVin()
        return map
    }

    private fun getDistanceMilOn(): String {
        return try {
            val command = DistanceMILOnCommand()
            command.run(socket.inputStream, socket.outputStream)
            "DistanceMilOn: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "DistanceMilOn: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "DistanceMilOn: $NODATA"
        }
    }

    private fun getDistanceSinceCC(): String {
        return try {
            val command = DistanceSinceCCCommand()
            command.run(socket.inputStream, socket.outputStream)
            "DistanceSinceCC: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "DistanceSinceCC: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "DistanceSinceCC: $NODATA"
        }
    }

    private fun getDtcNumber(): String {
        return try {
            val command = DtcNumberCommand()
            command.run(socket.inputStream, socket.outputStream)
            "DtcNumber: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "DtcNumber: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "DtcNumber: $NODATA"
        }
    }

    private fun getEquivalentRatio(): String {
        return try {
            val command = EquivalentRatioCommand()
            command.run(socket.inputStream, socket.outputStream)
            "EquivalenceRatio: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "EquivalenceRatio: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "EquivalenceRatio: $NODATA"
        }
    }

    private fun getIgnitionMonitor(): String {
        return try {
            val command = IgnitionMonitorCommand()
            command.run(socket.inputStream, socket.outputStream)
            "IgnitionMonitor: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "IgnitionMonitor: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "IgnitionMonitor: $NODATA"
        }
    }

    private fun getModuleVoltage(): String {
        return try {
            val command = ModuleVoltageCommand()
            command.run(socket.inputStream, socket.outputStream)
            "ModuleVoltage: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "ModuleVoltage: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "ModuleVoltage: $NODATA"
        }
    }

    private fun getPendingTroubleCodes(): String {
        return try {
            val command = LocalPendingTroubleCodesCommand()
            command.run(socket.inputStream, socket.outputStream)
            "PendingTroubleCodes: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "PendingTroubleCodes: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "PendingTroubleCodes: $NODATA"
        }
    }

    private fun getPermanentTroubleCodes(): String {
        return try {
            val command = LocalPermanentTroubleCodesCommand()
            command.run(socket.inputStream, socket.outputStream)
            "PermanentTroubleCodes: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "PermanentTroubleCodes: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "PermanentTroubleCodes: $NODATA"
        }
    }

    private fun getTimingAdvance(): String {
        return try {
            val command = TimingAdvanceCommand()
            command.run(socket.inputStream, socket.outputStream)
            "TimingAdvance: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "TimingAdvance: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "TimingAdvance: $NODATA"
        }
    }

    private fun getTroubleCodes(): String {
        return try {
            val command = LocalTroubleCodesCommand()
            command.run(socket.inputStream, socket.outputStream)
            "TroubleCodes: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "TroubleCodes: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "TroubleCodes: $NODATA"
        }
    }

    private fun getVin(): String {
        return try {
            val command = VinCommand()
            command.run(socket.inputStream, socket.outputStream)
            "VinCode: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "VinCode: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "VinCode: $NODATA"
        }
    }
}