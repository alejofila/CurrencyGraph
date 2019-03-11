package com.example.alejofila.currencygraphs.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class BasePresenter(val  mainDispatcher: CoroutineDispatcher, protected val backgroundDispatcher: CoroutineDispatcher) {

    private val job = Job()
    protected val uiScope = CoroutineScope(mainDispatcher + job)

    fun onStop() {
        job.cancel()
    }

}