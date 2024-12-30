package com.example.kailink.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [BookmarkContact::class], version = 2)
abstract class BookmarkContactDatabase: RoomDatabase() {
    abstract fun bookmarkContactDao(): BookmarkContactDao

    companion object {
        private  var instance: BookmarkContactDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BookmarkContactDatabase? {
            if (instance==null) {
                synchronized(BookmarkContactDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkContactDatabase::class.java,
                        "bookmarkContact-database"
                    )
                        .addMigrations(MIGRATION_1_2)
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
    }

}