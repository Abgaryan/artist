package com.idagio.artists.schedulers

import io.reactivex.Scheduler

interface BaseSchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
}
