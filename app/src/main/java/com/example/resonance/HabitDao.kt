package com.example.resonance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HabitDao {
    @Insert
    suspend fun insertHabit(habit: Habit)

    @Query ("DELETE FROM habit where secId = :secId ")
    suspend fun deleteHabit(secId: Int)

    @Query("SELECT * FROM habit where daily = :type")
    fun getTypeHabits(type: Boolean): List<Habit>

    @Query("SELECT * FROM habit where id = :id")
    fun getHabitViaId(id: Int): Habit

    @Query("SELECT * FROM habit where secId = :secId and master = :mas")
    fun getMasterHabitViaSecId(secId: Int, mas: Boolean): Habit

    @Query("SELECT * FROM habit where done = :done")
    fun getHabitViaDone(done: Boolean): List<Habit>

    @Query("UPDATE habit SET currentTimes=:currentTime WHERE id = :id")
    fun updateCurTime(currentTime: Int, id: Int)

    @Query("UPDATE habit SET updateCount=:currentTime WHERE id = :id")
    fun updateYesCurTime(currentTime: Int, id: Int)

    @Query("UPDATE habit SET done=:done WHERE secId = :secId")
    fun updateDone(done: Boolean, secId: Int)

    @Query("UPDATE habit SET currentStreak=:cs WHERE id = :id")
    fun updateCS(cs: Int, id: Int)

    @Query("UPDATE habit SET longestStreak=:ts WHERE id = :id")
    fun updateLS(ts: Int, id: Int)

    @Query("UPDATE habit SET missedDays=:md WHERE id = :id")
    fun updateMissedDays(md: Int, id: Int)

    @Query("UPDATE habit SET fiveDayStreak=:ds WHERE id = :id")
    fun updateFiveDaysStreak(ds: List<Int>, id: Int)

    @Query("UPDATE habit SET completedCount=:count WHERE id = :id")
    fun updateCompletedCount(count: Int, id: Int)

    @Query("UPDATE habit SET calendarCount=:count WHERE id = :id")
    fun updateCalendarCount(count: List<Int>, id: Int)



    @Query("SELECT * FROM habit")
    fun getAllHabits(): List<Habit>

    @Upsert
    suspend fun insertTag(tag: Tag)

    @Query("SELECT * FROM tag")
    fun getAllTags(): List<Tag>


}