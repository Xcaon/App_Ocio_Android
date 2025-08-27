package com.example.vivemurcia.di


import android.content.Context
import androidx.room.Room
import com.example.vivemurcia.data.room.AppDatabase
import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
 @InstallIn(SingletonComponent::class)
object FirebaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-vivemurcia"
        ).build()
    }

    // Librerias
    // Firebase Firestore (Datos)
    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Firebase Storage (Imagenes)
    @Provides
    @Singleton
    fun provideStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    //////////////
    // El modelo de Firebase
    @Provides
    @Singleton
    fun provideFireStoreModel(firestore: FirebaseFirestore) : FireStoreModel {
        return FireStoreModel(
            firestore,
            storage = provideFireStorageModel(provideStorage())
        )
    }

    @Provides
    @Singleton
    fun provideFireStorageModel(storage: FirebaseStorage) : FireStorageModel {
        return FireStorageModel(storage)
    }



}