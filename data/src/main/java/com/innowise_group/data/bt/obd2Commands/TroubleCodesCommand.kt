package com.innowise_group.data.bt.obd2Commands

import android.bluetooth.BluetoothSocket
import com.innowise_group.data.bt.NODATA
import com.innowise_group.data.bt.OBDII_TROUBLE_CODES
import com.innowise_group.data.bt.obd2Commands.BaseCommander
import io.github.macfja.obd2.Commander
import io.github.macfja.obd2.command.DTCsCommand
import io.github.macfja.obd2.response.MultipleDiagnosticTroubleCodeResponse

class TroubleCodesCommand(private val commander: Commander) {

    fun getTroubleCodes(): String{
        val response =  commander.sendCommand(DTCsCommand())
        return if (response is MultipleDiagnosticTroubleCodeResponse){
            "TroubleCodes: " +
                    ((response as MultipleDiagnosticTroubleCodeResponse).formattedString ?: NODATA)
        }else "TrobleCodes: 0"

    }
}