package com.smartqid.smartq.revamp.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

//    @Singleton
//    @Provides
//    fun provideAuthService(retrofit: Retrofit): AuthService {
//        return retrofit.create(AuthService::class.java)
//    }

}