package ru.smak.l4k06x_2023_11_07.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ru.smak.l4k06x_2023_11_07.R

class MainViewModel(app: Application) : AndroidViewModel(application = app) {

    var viewType by mutableStateOf(ViewType.MAIN_VIEW)

    var title by mutableStateOf(app.applicationContext.getString(R.string.app_title))

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
        viewType = ViewType.NEW_TEXT_VIEW
    }

    fun saveText(s: String) {
        textList.add(s)
        viewType = ViewType.MAIN_VIEW
    }


}

enum class ViewType{
    MAIN_VIEW, NEW_TEXT_VIEW
}