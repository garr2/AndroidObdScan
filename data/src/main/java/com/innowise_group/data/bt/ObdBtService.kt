package com.innowise_group.data.bt

import android.bluetooth.BluetoothSocket
import com.github.pires.obd.commands.protocol.EchoOffCommand
import com.github.pires.obd.commands.protocol.LineFeedOffCommand
import com.github.pires.obd.commands.protocol.SelectProtocolCommand
import com.github.pires.obd.commands.protocol.TimeoutCommand
import com.github.pires.obd.enums.ObdProtocols


class ObdBtService(private val socket: BluetoothSocket) {

    init {
        EchoOffCommand().run(socket.inputStream, socket.outputStream)
        LineFeedOffCommand().run(socket.inputStream, socket.outputStream)
        TimeoutCommand(30).run(socket.inputStream, socket.outputStream)
        SelectProtocolCommand(ObdProtocols.AUTO).run(socket.inputStream, socket.outputStream)
    }
}