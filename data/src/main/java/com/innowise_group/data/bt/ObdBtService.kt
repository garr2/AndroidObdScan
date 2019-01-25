package com.innowise_group.data.bt

import android.bluetooth.BluetoothSocket
import com.crashlytics.android.Crashlytics
import com.github.pires.obd.commands.SpeedCommand
import com.github.pires.obd.commands.engine.RPMCommand
import com.github.pires.obd.commands.protocol.*
import com.github.pires.obd.enums.ObdProtocols
import com.github.pires.obd.exceptions.NoDataException
import com.innowise_group.data.bt.obdCommands.*
import io.reactivex.Observable


class ObdBtService(private val socket: BluetoothSocket) {

    private val map = HashMap<String, String>()

    init {
        EchoOffCommand().run(socket.inputStream, socket.outputStream)
        LineFeedOffCommand().run(socket.inputStream, socket.outputStream)
        TimeoutCommand(255).run(socket.inputStream, socket.outputStream)
        SelectProtocolCommand(ObdProtocols.AUTO).run(socket.inputStream, socket.outputStream)
    }

    fun getAllData(): Observable<Map<String, String>> {
        val engineRpmCommand = RPMCommand()
        val speedCommand = SpeedCommand()
        while (!Thread.currentThread().isInterrupted) {
            try {
                engineRpmCommand.run(socket.inputStream, socket.outputStream)
                map.put(ENGINE_RPM_KEY, engineRpmCommand.formattedResult)
            }catch (n: NoDataException) {
                n.printStackTrace()
                map.put(ENGINE_RPM_KEY, NODATA)
            } catch (e: Exception) {
                Crashlytics.logException(e)
                map.put(ENGINE_RPM_KEY, NODATA)
            }

            try {
                speedCommand.run(socket.inputStream, socket.outputStream)
                map.put(SPEED_KEY, speedCommand.formattedResult)
            }catch (n: NoDataException) {
                n.printStackTrace()
                map.put(SPEED_KEY, NODATA)
            } catch (e: Exception) {
                Crashlytics.logException(e)
                map.put(SPEED_KEY, NODATA)
            }
            return Observable.just(map)
        }
        return Observable.just(map)
    }

    fun getControlCommands(): Observable<Map<String, String>> {
        return Observable.just(ControlCommands(socket).getCommands())
    }

    fun getEngineCommands(): Observable<Map<String, String>> {
        return Observable.just(EngineCommands(socket).getCommands())
    }

    fun getFuelCommands(): Observable<Map<String, String>> {
        return Observable.just(FuelCommands(socket).getCommands())
    }

    fun getPressureCommands(): Observable<Map<String, String>> {
        return Observable.just(PressureCommands(socket).getCommands())
    }

    fun getProtocolCommands(): Observable<Map<String, String>> {
        return Observable.just(ProtocolCommands(socket).getCommands())
    }

    fun getTemperatureCommands(): Observable<Map<String, String>> {
        return Observable.just(TemperatureCommands(socket).getCommands())
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

    fun getDynamicRPM(): Observable<Map<String, String>> {
        val command = RPMCommand()
        command.run(socket.inputStream, socket.outputStream)
        map[DYNAMIC_RPM] = command.formattedResult
        return Observable.just(map)
    }
}