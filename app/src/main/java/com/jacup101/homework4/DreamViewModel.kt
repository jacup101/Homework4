package com.jacup101.homework4

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DreamViewModel(private val repository: DreamRepository) : ViewModel() {
    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()


    fun insert(dream: Dream) = viewModelScope.launch {
        repository.insert(dream)
    }

    fun deleteDreamByID(id: Long) = viewModelScope.launch {
        repository.delete(id)
    }

    fun updateDream(id: Long, title: String, content: String, reflection: String, emotion: String) = viewModelScope.launch {
        repository.update(id, title, content, reflection, emotion)
    }

    fun getDreamById(id: Long): LiveData<Dream> {
        return repository.getDream(id).asLiveData()
    }
}

class DreamViewModelFactory(private val repository : DreamRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DreamViewModel::class.java)) {
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}