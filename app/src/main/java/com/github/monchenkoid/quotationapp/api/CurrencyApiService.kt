package com.github.monchenkoid.quotationapp.api

import com.github.monchenkoid.quotationapp.data.DailyExRatesModel
import io.reactivex.Observable
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface CurrencyApiService {

    @GET("XmlExRates.aspx")
    fun hitCountCheck(): Observable<DailyExRatesModel>

    companion object {
        fun create(): CurrencyApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                             Persister(AnnotationStrategy() // important part!
                                    )))
                    .baseUrl("http://www.nbrb.by/Services/")
                    .build()

            return retrofit.create(CurrencyApiService::class.java)
        }
    }

}