package com.innowise_group.data.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.data.bt.ConnectionBtAdapter
import com.innowise_group.data.bt.ObdBtService
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class BtDataRepositoryImpl @Inject constructor(private val btAdapter: ConnectionBtAdapter) : BtDataRepository {

    private var obdBtService: ObdBtService? = null

    override fun getState(): Observable<BtState> {
        return btAdapter.getState()
    }

    override fun getDeviceList(): Observable<Set<BluetoothDevice>> {
        return btAdapter.getDeviceList()
    }

    override fun getSpeedBtData(): Observable<String> {
        return obdBtService?.getSpeedData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getControlData(): Observable<String> {
        return obdBtService?.getControlData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getEngineData(): Observable<String> {
        return obdBtService?.getEngineData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getFuelData(): Observable<String> {
        return obdBtService?.getFuelData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getPowerData(): Observable<String> {
        return obdBtService?.getPowerData() ?: throw NullPointerException("ObdService is null")
    }

    override fun getPressureData(): Observable<String> {
        return obdBtService?.getPressureData() ?: throw NullPointerException("ObdService is null")
    }

    /*override fun getProtocolData(): Observable<Map<String, String>> {
        return obdBtService?.getProtocolData() ?: throw NullPointerException("ObdService is null")
    }*/

    override fun getTemperatureData(): Observable<String> {
        return obdBtService?.getTemperatureData() ?: throw NullPointerException("ObdService is null")
    }

    override fun resetTroubleCodes(): Observable<String> {
        return obdBtService?.resetTroubleCodes() ?: throw NullPointerException("ObdService is null")
    }

    override fun getDynamicRpm(): Observable<String> {
        return obdBtService?.getDynamicRPM() ?: throw NullPointerException("ObdService is null")
    }

    override fun getTroubleCodesData(): Observable<String> {
        return obdBtService?.getTroubleCodes() ?: throw NullPointerException("ObdService is null")
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

    override fun getTestData(): Observable<HashMap<String, ArrayList<String>>> {
        val list = ArrayList<String>()
        list.add("test")
        list.add("test1")
        list.add("test2")
        list.add("test3")
        val map = HashMap<String, ArrayList<String>>()
        map["test"] = list
        return Observable.just(map)
    }
}