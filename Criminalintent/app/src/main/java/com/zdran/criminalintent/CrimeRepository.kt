package com.zdran.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.zdran.criminalintent.database.CrimeDatabase
import com.zdran.criminalintent.database.migration_1_2
import java.io.File
import java.util.*
import java.util.concurrent.Executors

private const val DATA_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATA_NAME
    ).addMigrations(migration_1_2).build()
    private val crimeDAO = database.crimeDAO()
    private val executor = Executors.newSingleThreadExecutor()
    private val fileDir = context.applicationContext.filesDir

    fun getCrimes(): LiveData<List<Crime>> = crimeDAO.getCrimes()
    fun getCrime(uuid: UUID): LiveData<Crime?> = crimeDAO.getCrime(uuid)

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDAO.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime) {
        executor.execute {
            crimeDAO.addCrime(crime)
        }
    }

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("数据库仓库未初始化.")
        }
    }

    fun getPhoto(crime: Crime): File = File(fileDir, crime.photoName)

}