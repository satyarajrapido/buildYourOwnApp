package com.rohandakua.rapidopartnerhelperapp.data.offline.roomDb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rohandakua.rapidopartnerhelperapp.data.offline.roomDb.entity.DayOfJob
import java.util.Date

@Dao
interface DayOfJobDao {
    @Query( "Select * From day_of_job")
    fun getAllDays(): List<DayOfJob?>

    @Query("Select * From day_of_job Where rapido_partner_id = :rapido_partner_id And day_of_job = :day_of_job")
    fun getDayById(rapido_partner_id : Int , day_of_job : Date ): DayOfJob?

    @Insert
    fun insertDay(dayOfJob: DayOfJob)

    @Update
    fun updateDay(dayOfJob: DayOfJob)

    

}