package com.jfpsolucoes.unipplus2.core.database

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jfpsolucoes.unipplus2.core.database.dao.UPCredentialsDao
import com.jfpsolucoes.unipplus2.core.database.dao.UPSettingsDao
import com.jfpsolucoes.unipplus2.core.database.entities.UPCredentialsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import net.zetetic.database.sqlcipher.SQLiteDatabase

/**
 * Encrypted Room Database using SQLCipher
 *
 * This database is encrypted using the device's Android ID as the passphrase.
 * To use this database, add your entity classes to the entities array in the @Database annotation
 * and increment the version number when making schema changes.
 *
 * Example usage:
 * ```
 * val database = EncryptedDataBase.getInstance(context)
 * val exampleDao = database.exampleDao()
 * ```
 */
@Database(
    entities = [
        UPCredentialsEntity::class,
        UPSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EncryptedDataBase : RoomDatabase() {

    // Add your DAO abstract functions here
    abstract fun credentialsDao(): UPCredentialsDao

    abstract fun settingsDao(): UPSettingsDao

    companion object {
        private const val DATABASE_NAME = "unipplus_encrypted.db"

        @Volatile
        private var INSTANCE: EncryptedDataBase? = null

        /**
         * Gets the singleton instance of the encrypted database.
         *
         * @param context Application context
         * @return EncryptedDataBase instance
         */
        fun initialize(context: Context): EncryptedDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        val shared: EncryptedDataBase
            get() = INSTANCE ?: throw IllegalStateException("Database not initialized")

        /**
         * Builds the encrypted database instance using the device ID as passphrase.
         *
         * @param context Application context
         * @return EncryptedDataBase instance
         */
        private fun buildDatabase(context: Context): EncryptedDataBase {
            System.loadLibrary("sqlcipher")
            val passphrase = getDevicePassphrase(context)
            val factory = SupportOpenHelperFactory(passphrase.toByteArray())
            return Room.databaseBuilder(
                context.applicationContext,
                EncryptedDataBase::class.java,
                DATABASE_NAME
            )
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration(false)
                .build()
        }

        /**
         * Retrieves the device's unique Android ID to use as the encryption passphrase.
         *
         * @param context Application context
         * @return Device ID as String
         */
        @SuppressLint("HardwareIds")
        private fun getDevicePassphrase(context: Context): String {
            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            ) ?: "default_passphrase_unipplus2"
        }

        /**
         * Clears the database instance. Use with caution.
         * This is typically only needed for testing purposes.
         */
        fun clearInstance() {
            INSTANCE = null
        }
    }
}
