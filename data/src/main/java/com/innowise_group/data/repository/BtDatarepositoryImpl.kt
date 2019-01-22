package com.innowise_group.data.repository

import android.bluetooth.BluetoothDevice
import com.innowise_group.data.bt.BaseBtAdapter
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable

class BtDatarepositoryImpl: BtDataRepository {

    private val btAdapter = BaseBtAdapter()

    override fun getState(): Observable<BtState> {
        return btAdapter.getState()
    }

    override fun getDeviceList(): Observable<Set<BluetoothDevice>> {
     return btAdapter.getDeviceList()
    }

    override fun connect(deviceAddres: String): Completable {
     return btAdapter.connect(deviceAddres)
    }

    override fun write(): Completable {
        return btAdapter.write(ByteArray(1024))
    }

    override fun read(): Observable<ByteArray> {
        return btAdapter.read()
    }

    override fun cancel() {
        btAdapter.cancel()
    }
}