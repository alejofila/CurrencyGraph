package com.example.alejofila.currencygraphs.di

import com.example.alejofila.currencygraphs.presenter.CurrencyHistoryPresenter
import com.example.alejofila.data.networking.CurrencyExchangeApi
import com.example.alejofila.data.networking.CurrencyService
import com.example.alejofila.data.repository.CurrencyRepositoryImpl
import com.example.alejofila.domain.repository.CurrencyHistoryRepository
import com.example.alejofila.domain.usecase.GetCurrencyInfoPeriodOfTimeUseCase
import com.example.alejofila.domain.usecase.GetCurrencyInfoPeriodOfTimeUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import org.koin.dsl.module.module


val MAIN_DISPATCHER = "MAIN_DISPATCHER"
val BACKGROUND_DISPATCHER = "BACKGROUND_DISPATCHER"
val appModule = module {


    single<CurrencyExchangeApi> { CurrencyService.getCurrencyService() }
    single<CurrencyHistoryRepository> { CurrencyRepositoryImpl(get()) }
    single<GetCurrencyInfoPeriodOfTimeUseCase> { GetCurrencyInfoPeriodOfTimeUseCaseImpl(get()) }
    single<CoroutineDispatcher>(MAIN_DISPATCHER) { Dispatchers.Main }
    single<CoroutineDispatcher>(BACKGROUND_DISPATCHER) { Dispatchers.IO }
    single<CurrencyHistoryPresenter> {
        CurrencyHistoryPresenter(
            get(),
            get(BACKGROUND_DISPATCHER),
            get(MAIN_DISPATCHER)
        )
    }

}

