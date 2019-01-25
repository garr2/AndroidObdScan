package com.innowise_group.data.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.data.bt.ConnectionBtAdapter
import com.innowise_group.data.bt.ObdBtService
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable

class BtDataRepositoryImpl : BtDataRepository {

    private val btAdapter = ConnectionBtAdapter()
    private var obdBtService: ObdBtService? = null

    override fun getState(): Observable<BtState> {
        return btAdapter.getState()
    }

    override fun getDeviceList(): Observable<Set<BluetoothDevice>> {
        return btAdapter.getDeviceList()
    }

    override fun getAllBtData(): Observable<Map<String, String>> {
        return obdBtService?.getAllData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getControllCommands(): Observable<Map<String, String>> {
        return obdBtService?.getControlCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun getEngineCommands(): Observable<Map<String, String>> {
        return obdBtService?.getEngineCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun getFuelCommands(): Observable<Map<String, String>> {
        return obdBtService?.getFuelCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun getPreashureCommands(): Observable<Map<String, String>> {
        return obdBtService?.getPressureCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun getProtocolCommands(): Observable<Map<String, String>> {
        return obdBtService?.getProtocolCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun getTemperatureCommands(): Observable<Map<String, String>> {
        return obdBtService?.getTemperatureCommands() ?: throw NullPointerException("ObdService is null")
    }

    override fun resetTroubleCodes(): Observable<String> {
        return obdBtService?.resetTroubleCodes() ?: throw NullPointerException("ObdService is null")
    }

    override fun getDynamicRpm(): Observable<Map<String, String>> {
        return obdBtService?.getDynamicRPM() ?: throw NullPointerException("ObdService is null")
    }

    override fun connect(deviceAddress: String): Completable {
        return btAdapter.connect(deviceAddress).doOnComplete {
            obdBtService = ObdBtService(btAdapter.socket!!)
        }
    }

    override fun cancel() {
        obdBtService?.close()
        btAdapter.cancel()
    }
}