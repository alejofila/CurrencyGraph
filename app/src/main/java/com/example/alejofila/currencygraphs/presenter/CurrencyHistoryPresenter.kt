package com.example.alejofila.currencygraphs.presenter

import com.example.alejofila.currencygraphs.base.BasePresenter
import com.example.alejofila.currencygraphs.base.BaseView
import com.example.alejofila.currencygraphs.common.mapper.CurrencyExchangeUiModelMapper
import com.example.alejofila.currencygraphs.common.model.CurrencyExchangeUiModel
import com.example.alejofila.data.networking.EUR_SYMBOL
import com.example.alejofila.domain.usecase.GetCurrencyInfoPeriodOfTimeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate


class CurrencyHistoryPresenter(
    private val useCase: GetCurrencyInfoPeriodOfTimeUseCase,
    backgroundDispatcher: CoroutineDispatcher,
    mainDispatcher: CoroutineDispatcher
) : BasePresenter(mainDispatcher, backgroundDispatcher) {

    lateinit var view: CurrencyHistoryView

    fun loadCurrencyHistory(previousDays: String, currentDate: LocalDate, symbol: String = EUR_SYMBOL) {
        uiScope.launch {
            val startDate = currentDate.minusDays(previousDays.toLong())

            view.showLoader()

            if (isInValidPreviousDays(previousDays)) {
                view.showEmptyDaysError()

            } else {
                val historyUiModel = withContext(backgroundDispatcher) {
                    CurrencyExchangeUiModelMapper.fromDomainToUiModel(useCase(startDate, currentDate, symbol))
                }
                view.setUpMinMaxHorinzotal(0f, historyUiModel.currencyInfoDetils.listOfDates.size.toFloat())
                view.showHistoryGraph(historyUiModel)
            }
        }
    }

    private fun isInValidPreviousDays(previousDays: String): Boolean {
        return previousDays.isEmpty() || previousDays.toInt() !in 1..365
    }


}

interface CurrencyHistoryView : BaseView {
    fun showHistoryGraph(currencyHistory: CurrencyExchangeUiModel)
    fun showEmptyDaysError()
    fun setUpMinMaxHorinzotal(min: Float, max: Float)
}