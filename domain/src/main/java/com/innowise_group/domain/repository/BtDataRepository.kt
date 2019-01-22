package com.innowise_group.domain.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.domain.entity.BtState
import io.reactivex.Completable
import io.reactivex.Observable

interface BtDataRepository {

    fun getState(): Observable<BtState>

    fun getDeviceList(): Observable<Set<BluetoothDevice>>

    fun connect(deviceAddress: String): Completable

    fun write(): Completable

    fun read(): Observable<ByteArray>

    fun cancel()
}