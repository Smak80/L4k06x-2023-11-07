package ru.smak.l4k06x_2023_11_07.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var title by mutableStateOf("Название приложения")

    val textList = SnapshotStateList<String>()

    init {
        textList.add("Текст 1")
        textList.add("Текст 2")
        textList.add("Текст 3")
        textList.add("Текст 4")
        textList.add("Текст 5")
        textList.add("Текст 6")
        textList.add("Текст 7")
    }

    fun removeText(textToRemove: String){
        textList.removeIf { it == textToRemove }
    }

    fun addText() {
        
    }


}