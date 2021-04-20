package com.jacup101.homework4

import kotlinx.coroutines.flow.Flow

class DreamRepository(private val dreamDAO: DreamDAO) {

    val allDreams: Flow<List<Dream>> = dreamDAO.getSortedDreams();

    suspend fun insert(dream: Dream) {
        dreamDAO.insert(dream);
    }

    suspend fun delete(id: Long) {
        dreamDAO.deleteDream(id)
    }
    suspend fun update(id: Long, title: String, content: String, reflection: String, emotion: String) {
        dreamDAO.update(id, title, content, reflection, emotion)
    }

    fun getDream(id: Long) : Flow<Dream> {
        return dreamDAO.getDreamFromId(id)
    }


}