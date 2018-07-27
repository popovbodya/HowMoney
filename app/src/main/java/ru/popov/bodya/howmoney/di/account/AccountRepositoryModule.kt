package ru.popov.bodya.howmoney.di.account

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApi
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApi.Companion.FIXER_BASE_URL
import ru.popov.bodya.howmoney.data.network.api.CurrenciesRateApiWrapper
import ru.popov.bodya.howmoney.data.repositories.CurrencyRepository
import ru.popov.bodya.howmoney.di.common.modules.SettingsModule

/**
 *  @author popovbodya
 */
@Module(includes = [SettingsModule::class])
class AccountRepositoryModule {

    @Provides
    fun provideRepository(currenciesRateApiWrapper: CurrenciesRateApiWrapper, sharedPreferencesWrapper: SharedPreferencesWrapper): CurrencyRepository =
            CurrencyRepository(currenciesRateApiWrapper, sharedPreferencesWrapper)

    @Provides
    fun provideCurrenciesRateApiWrapper(currenciesRateApi: CurrenciesRateApi): CurrenciesRateApiWrapper =
            CurrenciesRateApiWrapper(currenciesRateApi)

    @Provides
    fun provideExchangeRateApi(okHttpClient: OkHttpClient,
                               converterFactory: Converter.Factory,
                               adapterFactory: CallAdapter.Factory): CurrenciesRateApi =
            Retrofit.Builder()
                    .baseUrl(FIXER_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(adapterFactory)
                    .client(okHttpClient)
                    .build()
                    .create(CurrenciesRateApi::class.java)


    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides
    internal fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
}