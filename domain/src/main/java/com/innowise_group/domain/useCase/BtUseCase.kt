package com.innowise_group.domain.useCase

import android.bluetooth.BluetoothDevice
import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.entity.BtState
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class BtUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
    , private val btDataRepository: BtDataRepository
) : BaseUseCase(postExecutorThread) {

    fun getState(): Observable<BtState> = btDataRepository.getState()
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun getDeviceList(): Observable<Set<BluetoothDevice>> = btDataRepository.getDeviceList()
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun getSpeedData(): Observable<String> = btDataRepository.getSpeedBtData()
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun connect(deviceAddress: String): Completable = btDataRepository.connect(deviceAddress)
        .observeOn(postExecutorThread)
        .subscribeOn(workExecutorThread)

    fun cancel() = btDataRepository.cancel()


    fun getControlData(): Observable<String> {
        return btDataRepository.getControlData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getEngineData(): Observable<String> {
        return btDataRepository.getEngineData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getFuelData(): Observable<String> {
        return btDataRepository.getFuelData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getPowerData(): Observable<String> {
        return btDataRepository.getPowerData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getPressureData(): Observable<String> {
        return btDataRepository.getPressureData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    /*fun getProtocolCommands(): Observable<Map<String, String>>{
        return btDataRepository.getProtocolCommands()
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }*/

    fun getTemperatureData(): Observable<String> {
        return btDataRepository.getTemperatureData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getTroubleCodesData(): Observable<String> {
        return btDataRepository.getTroubleCodesData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun resetTroubleCodes(): Observable<String> {
        return btDataRepository.resetTroubleCodes()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getDynamicRpm(): Observable<String> {
        return btDataRepository.getDynamicRpm()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }

    fun getTestData(): Observable<HashMap<String ,ArrayList<String>>>{
        return btDataRepository.getTestData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }
}