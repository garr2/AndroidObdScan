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
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)

    fun getDeviceList(): Observable<Set<BluetoothDevice>> = btDataRepository.getDeviceList()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)

    fun getAllBtData(): Observable<Map<String, String>> = btDataRepository.getAllBtData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)

    fun connect(deviceAddress: String): Completable = btDataRepository.connect(deviceAddress)
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)

    fun cancel() = btDataRepository.cancel()



    fun getControllCommands(): Observable<Map<String, String>>{
        return btDataRepository.getControllCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getEngineCommands(): Observable<Map<String, String>>{
        return btDataRepository.getEngineCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getFuelCommands(): Observable<Map<String, String>>{
        return btDataRepository.getFuelCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getPreashureCommands(): Observable<Map<String, String>>{
        return btDataRepository.getPreashureCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getProtocolCommands(): Observable<Map<String, String>>{
        return btDataRepository.getProtocolCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getTemperatureCommands(): Observable<Map<String, String>>{
        return btDataRepository.getTemperatureCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun resetTroubleCodes(): Observable<String>{
        return btDataRepository.resetTroubleCodes()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }

    fun getDynamicRpm(): Observable<Map<String, String>>{
        return btDataRepository.getDynamicRpm()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }
    }