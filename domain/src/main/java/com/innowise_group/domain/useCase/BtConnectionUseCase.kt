package com.innowise_group.domain.useCase

import android.bluetooth.BluetoothDevice
import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class BtConnectionUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
    ,private val btDataRepository: BtDataRepository
) : BaseUseCase(postExecutorThread) {

    fun getState(): Observable<BtState> = btDataRepository.getState()
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun getDeviceList(): Observable<Set<BluetoothDevice>> = btDataRepository.getDeviceList()
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun connect(deviceAddress: String): Completable = btDataRepository.connect(deviceAddress)
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun cancel() = btDataRepository.cancel()
}