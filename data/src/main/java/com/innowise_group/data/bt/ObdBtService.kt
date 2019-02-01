package com.innowise_group.data.bt

import android.bluetooth.BluetoothSocket
import com.github.pires.obd.commands.protocol.*
import com.github.pires.obd.enums.ObdProtocols
import com.innowise_group.data.bt.obd2Commands.*
import com.innowise_group.data.bt.obdCommands.ProtocolCommands
import io.github.macfja.obd2.Commander
import io.reactivex.Observable


class ObdBtService(private val socket: BluetoothSocket) {

    private val commander = Commander()

    private val controlCommands = ControlCommands(commander)
    private val engineCommands = EngineCommands(commander)
    private val fuelCommands = FuelCommands(commander)
    private val powerCommands = PowerCommnads(commander)
    private val pressureCommands = PressureCommands(commander)
    private val temperatureCommands = TemperatureCommands(commander)
    private val troubleCodesCommand = TroubleCodesCommand(commander)

    init {
        commander.setCommunicationInterface(socket.outputStream, socket.inputStream)
        EchoOffCommand().run(socket.inputStream, socket.outputStream)
        LineFeedOffCommand().run(socket.inputStream, socket.outputStream)
        TimeoutCommand(200).run(socket.inputStream, socket.outputStream)
        SelectProtocolCommand(ObdProtocols.AUTO).run(socket.inputStream, socket.outputStream)
    }

    fun getSpeedData(): Observable<String> {
        return Observable.just(controlCommands.getVehicleSpeed())
    }

    fun getControlData(): Observable<String> {
        return controlCommands.getAllData()
    }

    fun getEngineData(): Observable<String> {
        return engineCommands.getAllData()
    }

    fun getFuelData(): Observable<String> {
        return fuelCommands.getAllFuelData()
    }

    fun getPowerData(): Observable<String> {
        return powerCommands.getAllData()
    }

    fun getPressureData(): Observable<String> {
        return pressureCommands.getAllData()
    }

    fun getProtocolData(): Observable<Map<String, String>> {
        return Observable.just(ProtocolCommands(socket).getAllDAta())
    }

    fun getTemperatureData(): Observable<String> {
        return temperatureCommands.getAllData()
    }

    fun close(): Observable<String> {
        val close = CloseCommand()
        close.run(socket.inputStream, socket.outputStream)
        return Observable.just(close.formattedResult)
    }

    fun resetTroubleCodes(): Observable<String> {
        val reset = ResetTroubleCodesCommand()
        reset.run(socket.inputStream, socket.outputStream)
        return Observable.just(reset.formattedResult)
    }

    fun getDynamicRPM(): Observable<String> {
        return Observable.just(controlCommands.getEngineRpm())
    }

    fun getTroubleCodes(): Observable<String> {
        return Observable.just(troubleCodesCommand.getTroubleCodes())
    }

}