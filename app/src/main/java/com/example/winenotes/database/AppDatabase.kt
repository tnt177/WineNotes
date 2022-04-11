package com.example.winenotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NOTE::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NoteDao(): NoteDao

    companion object {
        private val NAME_OF_DATABASE = "wines"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        NAME_OF_DATABASE
                    ).build()
                }
            }
            return INSTANCE!!

        }
    }
}