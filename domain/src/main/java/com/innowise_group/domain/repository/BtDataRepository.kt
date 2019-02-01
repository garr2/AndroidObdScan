package com.innowise_group.domain.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.domain.entity.BtState
import io.reactivex.Completable
import io.reactivex.Observable

interface BtDataRepository {

    fun getState(): Observable<BtState>

    fun getDeviceList(): Observable<Set<BluetoothDevice>>

    fun getSpeedBtData(): Observable<String>

    fun getControlData(): Observable<String>

    fun getEngineData(): Observable<String>

    fun getFuelData(): Observable<String>

    fun getPowerData(): Observable<String>

    fun getPressureData(): Observable<String>

    //fun getProtocolCommands(): Observable<Map<String, String>>

    fun getTemperatureData(): Observable<String>

    fun resetTroubleCodes(): Observable<String>

    fun getDynamicRpm(): Observable<String>

    fun getTroubleCodesData(): Observable<String>

    fun connect(deviceAddress: String): Completable

    fun cancel()

    fun getTestData(): Observable<HashMap<String, ArrayList<String>>>
}