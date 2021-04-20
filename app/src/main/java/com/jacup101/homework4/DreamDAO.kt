package com.jacup101.homework4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DreamDAO {
    @Query("SELECT * FROM dream_table ORDER BY id ASC")
    fun getSortedDreams() : Flow<List<Dream>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream : Dream)

    @Query("UPDATE dream_table SET title=:title, content=:content, reflection=:reflection, emotion=:emotion WHERE id=:id")
    suspend fun update(id: Long, title: String, content: String, reflection: String, emotion: String)

    @Query("DELETE FROM dream_table WHERE id=:id")
    suspend fun deleteDream(id : Long)

    @Query ("SELECT * FROM dream_table WHERE id=:id")
    fun getDreamFromId(id: Long) : Flow<Dream>


}