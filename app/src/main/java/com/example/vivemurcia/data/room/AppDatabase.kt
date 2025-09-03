package com.example.vivemurcia.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Para cada clase DAO que se asoció con la base de datos, esta base de datos debe definir un
// métoodo abstracto que tenga cero argumentos y muestre una instancia de la clase DAO.

@Database(entities = [ActividadDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actividadDao(): ActividadDAO
}

