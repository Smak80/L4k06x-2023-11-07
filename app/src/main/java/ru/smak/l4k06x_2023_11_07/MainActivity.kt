package ru.smak.l4k06x_2023_11_07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.smak.l4k06x_2023_11_07.ui.theme.L4k06x20231107Theme
import ru.smak.l4k06x_2023_11_07.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4k06x20231107Theme {
                val mvm = viewModel(MainViewModel::class.java)
                MainUI(
                    mvm.title,
                    mvm.textList,
                    modifier = Modifier.fillMaxSize(),
                    onAdd = mvm::addText,
                    onDelete = mvm::removeText
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(
    title: String,
    textList: List<String>,
    modifier: Modifier = Modifier,
    onAdd: ()->Unit = {},
    onDelete: (String)->Unit = {},
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary
                )},
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            IconButton(onClick = onAdd){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_library_add_48),
                    contentDescription = stringResource(R.string.add_new_text)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        MainContent(
            textList = textList,
            modifier = Modifier.padding(it),
            onDelete = onDelete
        )
    }
}

@Composable
fun MainContent(
    textList: List<String>,
    modifier: Modifier = Modifier,
    onDelete: (String)->Unit = {},
){

    LazyColumn(content = {
        items(textList){
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        onDelete(it)
                    }
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_48),
                        contentDescription = stringResource(R.string.delete_btn),
                        tint = Color(0x90a0280a)
                    )
                }
                Text(
                    text = it,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp
                )
            }
        }
    }, modifier = modifier)

}