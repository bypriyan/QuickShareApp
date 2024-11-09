package com.bypriyan.togocartstore.DI.module

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import com.bypriyan.multishare.activity.homeScreen.StorageRepository
import com.bypriyan.multishare.activity.homeScreen.StorageRepositoryImpl
import com.bypriyan.multishare.activity.send.category.ImageRepository
import com.bypriyan.multishare.activity.send.category.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideStorageRepository(
        @ApplicationContext context: Context
    ): StorageRepository = StorageRepositoryImpl(context)

    @Provides
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }
}