package id.itborneo.ugithub.core.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.itborneo.ugithub.core.local.DatabaseConstant.TABLE_NAME

@Database(
    entities = [FavoriteModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        TABLE_NAME
                    ).build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}