package com.innowise_group.domain.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.domain.entity.BtState
import io.reactivex.Completable
import io.reactivex.Observable

interface BtDataRepository {

    fun getState(): Observable<BtState>

    fun getDeviceList(): Observable<Set<BluetoothDevice>>

    fun getAllBtData(): Observable<Map<String,String>>

    fun getControllCommands(): Observable<Map<String, String>>

    fun getEngineCommands(): Observable<Map<String, String>>

    fun getFuelCommands(): Observable<Map<String, String>>

    fun getPreashureCommands(): Observable<Map<String, String>>

    fun getProtocolCommands(): Observable<Map<String, String>>

    fun getTemperatureCommands(): Observable<Map<String, String>>

    fun resetTroubleCodes(): Observable<String>

    fun getDynamicRpm(): Observable<Map<String, String>>

    fun connect(deviceAddress: String): Completable

    fun cancel()
}