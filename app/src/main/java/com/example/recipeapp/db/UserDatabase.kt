package com.example.recipeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.model.User
import com.example.recipeapp.model.Favorite

@Database(entities = [User::class, Favorite::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // to handle schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
