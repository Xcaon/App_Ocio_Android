package com.example.vivemurcia.di


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

    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreModel(firestore: FirebaseFirestore) : FireStoreModel {
        return FireStoreModel(firestore)
    }

    @Provides
    @Singleton
    fun provideStorage(firestore: FirebaseFirestore) : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }


}