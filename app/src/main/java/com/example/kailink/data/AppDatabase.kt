package com.example.kailink.data

import com.example.kailink.data.Profile
import com.example.kailink.data.ProfileDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [BookmarkContact::class, Profile::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookmarkContactDao(): BookmarkContactDao
    abstract fun profileDao(): ProfileDao

    companion object {
        private  var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance==null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "kailink_database"
                    )
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build()
                }
            }
            return instance
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // SQL query to update the schema
                database.execSQL("ALTER TABLE BookmarkContact RENAME COLUMN adress TO address")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS Profile (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        email TEXT NOT NULL,
                        profileImage TEXT
                    )
                    """
                )
            }
        }
    }

}