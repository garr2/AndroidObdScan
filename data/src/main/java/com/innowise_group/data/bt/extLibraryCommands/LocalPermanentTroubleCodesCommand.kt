package com.innowise_group.data.bt.extLibraryCommands

import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.control.PermanentTroubleCodesCommand
import kotlin.experimental.and

class LocalPermanentTroubleCodesCommand: PermanentTroubleCodesCommand() {

    override fun performCalculations() {
        val result = result
        val workingData: String
        var startIndex = 0//Header size.

        val canOneFrame = result.replace("[\r\n]".toRegex(), "")
        val canOneFrameLength = canOneFrame.length
        if (canOneFrameLength <= 16 && canOneFrameLength % 4 == 0) {//CAN(ISO-15765) protocol one frame.
            workingData = canOneFrame//4Ayy{codes}
            startIndex = 4//Header is 4Ayy, yy showing the number of data items.
        } else if (result.contains(":")) {//CAN(ISO-15765) protocol two and more frames.
            workingData = result.replace("[\r\n].:".toRegex(), "")//xxx4Ayy{codes}
            startIndex =
                    7//Header is xxx4Ayy, xxx is bytes of information to follow, yy showing the number of data items.
        } else {//ISO9141-2, KWP2000 Fast and KWP2000 5Kbps (ISO15031) protocols.
            workingData = result.replace("^4A|[\r\n]4A|[\r\n]".toRegex(), "")
        }
        var begin = startIndex
        while (begin < workingData.length) {
            var dtc = ""
            val b1 = hexStringToByteArray(workingData[begin])
            val ch1 = ((b1 and 0xC0.toByte()).toInt() shr 6)
            val ch2 = ((b1 and 0x30.toByte()).toInt() shr 4)
            dtc += dtcLetters[ch1]
            dtc += hexArray[ch2]
            Crashlytics.logException(Exception("dtc = $dtc workingData = $workingData"))
            dtc += workingData.substring(begin + 1, begin + 4)
            if (dtc == "P0000") {
                return
            }
            codes.append(dtc)
            codes.append('\n')
            begin += 4
        }
    }

    private fun hexStringToByteArray(s: Char): Byte {
        return (Character.digit(s, 16) shl 4).toByte()
    }
}