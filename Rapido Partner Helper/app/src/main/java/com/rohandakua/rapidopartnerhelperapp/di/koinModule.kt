package com.rohandakua.rapidopartnerhelperapp.di

import com.rohandakua.rapidopartnerhelperapp.data.offline.datastore.AppDataStore
import org.koin.dsl.module

val koinModule  = module {

    single { AppDataStore(get())} // for single instance of AppDataStore




}