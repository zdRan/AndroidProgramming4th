package com.zdran.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zdran.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDAO {

    @Query("select * from crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("select * from crime where id = (:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

    @Insert
    fun addCrime(crime: Crime)
}