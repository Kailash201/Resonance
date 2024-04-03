package com.example.resonance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Habit::class, Tag::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AlphaDatabase : RoomDatabase() {
    abstract fun HabitDao(): HabitDao

    companion object {
        private var INSTANCE: AlphaDatabase? = null
        fun getDatabase(context: Context): AlphaDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,AlphaDatabase::class.java, "habits")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}