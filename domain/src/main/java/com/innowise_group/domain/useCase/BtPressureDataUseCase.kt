package com.innowise_group.domain.useCase

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class BtPressureDataUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
,private val btDataRepository: BtDataRepository
): BaseUseCase(postExecutorThread) {

    fun getPresshureCommands(): Observable<String> {
        return btDataRepository.getPressureData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }
}