package com.innowise_group.data.bt.obdCommands

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_01_20
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_21_40
import com.github.pires.obd.commands.protocol.AvailablePidsCommand_41_60
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.AVAILABLE_PIDS_01_20
import com.innowise_group.data.bt.AVAILABLE_PIDS_21_40
import com.innowise_group.data.bt.AVAILABLE_PIDS_41_60
import com.innowise_group.data.bt.NODATA

class ProtocolCommands(private val socket: BluetoothSocket) {

    fun getAllDAta(): Map<String, String> {
        val map = HashMap<String, String>()
        map[AVAILABLE_PIDS_01_20] = getAvailablePids01to20()
        map[AVAILABLE_PIDS_21_40] = getAvailablePids21to40()
        map[AVAILABLE_PIDS_41_60] = getAvailablePids41to60()
        return map
    }

    private fun getAvailablePids01to20(): String {
        return try {
            val command = AvailablePidsCommand_01_20()
            command.run(socket.inputStream, socket.outputStream)
            "AvailablePids01to20: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AvailablePids01to20: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AvailablePids01to20: $NODATA"
        }
    }

    private fun getAvailablePids21to40(): String {
        return try {
            val command = AvailablePidsCommand_21_40()
            command.run(socket.inputStream, socket.outputStream)
            "AvailablePids21to40: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AvailablePids21to40: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AvailablePids21to40: $NODATA"
        }
    }

    private fun getAvailablePids41to60(): String {
        return try {
            val command = AvailablePidsCommand_41_60()
            command.run(socket.inputStream, socket.outputStream)
            "AvailablePids41to60: ${command.formattedResult}"
        } catch (n: NoDataException) {
            n.printStackTrace()
            "AvailablePids41to60: $NODATA"
        } catch (e: Exception) {
            Crashlytics.logException(e)
            "AvailablePids41to60: $NODATA"
        }
    }
}