package com.innowise_group.domain.useCase

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class BtTemperatureDataUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
,private val btDataRepository: BtDataRepository
): BaseUseCase(postExecutorThread) {

    fun getTemperatureCommands(): Observable<String> {
        return btDataRepository.getTemperatureData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }
}