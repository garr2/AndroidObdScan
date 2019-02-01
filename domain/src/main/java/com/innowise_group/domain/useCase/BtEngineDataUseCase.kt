package com.innowise_group.domain.useCase

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.domain.repository.BtDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class BtEngineDataUseCase @Inject constructor(
    postExecutorThread: PostExecutorThread
,private val btDataRepository: BtDataRepository
): BaseUseCase(postExecutorThread) {

    fun getEngineCommands(): Observable<String> {
        return btDataRepository.getEngineData()
            .observeOn(postExecutorThread)
            .subscribeOn(workExecutorThread)
    }
}