package com.example.vivemurcia.di


import com.example.vivemurcia.model.firebase.FireStorageModel
import com.example.vivemurcia.model.firebase.FireStoreModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
 @InstallIn(SingletonComponent::class)
object FirebaseModule {

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