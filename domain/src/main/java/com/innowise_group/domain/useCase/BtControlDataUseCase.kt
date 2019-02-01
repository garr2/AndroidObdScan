package com.innowise_group.domain.useCase

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class BtControlDataUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
    , private val btDataRepository: BtDataRepository
) : BaseUseCase(postExecutorThread) {

    fun getControllCommands(): Observable<String> {
        return btDataRepository.getControlData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }
}