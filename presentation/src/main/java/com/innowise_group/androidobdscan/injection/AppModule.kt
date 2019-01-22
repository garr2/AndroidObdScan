package com.innowise_group.androidobdscan.injection

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.androidobdscan.executor.UIThread
import com.innowise_group.data.repository.BtDatarepositoryImpl
import com.innowise_group.domain.repository.BtDataRepository
import com.innowise_group.domain.useCase.BtUseCase
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideBtUseCase(thread: PostExecutorThread, repository: BtDataRepository): BtUseCase
            = BtUseCase(thread, repository)

    @Provides
    fun provideBtDataRepository(): BtDataRepository = BtDatarepositoryImpl()

    @Provides
    fun providePostExecutorThread() : PostExecutorThread = UIThread()
}