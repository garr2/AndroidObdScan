package com.innowise_group.domain.useCase

import android.bluetooth.BluetoothDevice
import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable

class BtUseCase(postExecutorThread: PostExecutorThread
                ,private val btDataRepository: BtDataRepository)
    : BaseUseCase(postExecutorThread) {

    fun getState(): Observable<BtState> = btDataRepository.getState()

    fun getDeviceList(): Observable<Set<BluetoothDevice>> = btDataRepository.getDeviceList()

    fun connect(deviceAddress: String): Completable = btDataRepository.connect(deviceAddress)

    fun write(): Completable = btDataRepository.write()

    fun read(): Observable<ByteArray> = btDataRepository.read()

    fun cancel() = btDataRepository.cancel()
    }