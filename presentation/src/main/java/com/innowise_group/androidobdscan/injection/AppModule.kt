package com.innowise_group.androidobdscan.injection

import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.innowise_group.androidobdscan.executor.UIThread
import com.innowise_group.data.bt.ConnectionBtAdapter
import com.innowise_group.data.repository.BtDataRepositoryImpl
import com.innowise_group.domain.repository.BtDataRepository
import com.innowise_group.domain.useCase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideBtUseCase(thread: PostExecutorThread, repository: BtDataRepository): BtUseCase
            = BtUseCase(thread, repository)

    @Provides
    @Singleton
    fun provideBtConnectionUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtConnectionUseCase = BtConnectionUseCase(thread,repository)

    @Provides
    fun provideBtControlDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtControlDataUseCase = BtControlDataUseCase(thread, repository)

    @Provides
    fun provideBtEngineDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtEngineDataUseCase = BtEngineDataUseCase(thread, repository)

    @Provides
    fun provideBtFuelDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtFuelDataUseCase = BtFuelDataUseCase(thread, repository)

    @Provides
    fun providePressureDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtPressureDataUseCase = BtPressureDataUseCase(thread, repository)

    @Provides
    fun provideProtocolDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtProtocolDataUseCase = BtProtocolDataUseCase(thread, repository)

    @Provides
    fun provideTemperatureDataUseCase(thread: PostExecutorThread, repository: BtDataRepository)
            : BtTemperatureDataUseCase = BtTemperatureDataUseCase(thread, repository)

    @Provides
    @Singleton
    fun provideBtDataRepository(connectionAdapter: ConnectionBtAdapter): BtDataRepository
            = BtDataRepositoryImpl(connectionAdapter)

    @Provides
    fun provideBtConnectionAdapter(): ConnectionBtAdapter
    = ConnectionBtAdapter()

    @Provides
    fun providePostExecutorThread() : PostExecutorThread = UIThread()
}