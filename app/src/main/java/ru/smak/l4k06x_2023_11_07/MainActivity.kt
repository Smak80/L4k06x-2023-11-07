package ru.smak.l4k06x_2023_11_07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.smak.l4k06x_2023_11_07.ui.theme.L4k06x20231107Theme
import ru.smak.l4k06x_2023_11_07.viewmodels.MainViewModel
import ru.smak.l4k06x_2023_11_07.viewmodels.ViewType

class MainActivity : ComponentActivity() {

    val mvm: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            L4k06x20231107Theme {
                //val mvm = viewModel(MainViewModel::class.java)
                MainUI(
                    //mvm.title,
                    //mvm.textList,
                    mvm,
                    modifier = Modifier.fillMaxSize(),
                    //view = mvm.viewType,
                    //onAdd = mvm::addText,
                    //onDelete = mvm::removeText,
                    //onSave = mvm::saveText,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainUI(
    mvm: MainViewModel?,
    //title: String,
    //textList: List<String>,
    modifier: Modifier = Modifier,
    //view: ViewType = ViewType.MAIN_VIEW,
    //onAdd: ()->Unit = {},
    //onDelete: (String)->Unit = {},
    //onSave: (String)->Unit = {},
){
    var userText by remember {mutableStateOf("")}

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = mvm?.title ?: "---",
                    //modifier = Modifier.combinedClickable(onLongClick = {}, onClick = {}),
                    color = MaterialTheme.colorScheme.primary
                )},
                actions = {
                    if (mvm?.viewType == ViewType.NEW_TEXT_VIEW)
                        IconButton(onClick = {
                            mvm.saveText(userText)
                            mvm.viewType = ViewType.MAIN_VIEW
                        }){
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_check_48),
                            contentDescription = stringResource(R.string.save_text)
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            mvm?.let {
                IconButton(onClick = {
                    mvm.addText()
                    userText = ""
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_library_add_48),
                        contentDescription = stringResource(R.string.add_new_text)
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        when(mvm?.viewType) {
            ViewType.MAIN_VIEW -> MainContent(
                textList = mvm.textList,
                modifier = Modifier.padding(it),
                onDelete = mvm::removeText
            )
            ViewType.NEW_TEXT_VIEW -> {
                NewTextContent(
                    text = userText,
                    modifier = Modifier.padding(it),
                    onChange = {
                        userText = it
                    },
                    onSave = {
                        mvm.saveText(userText)
                    }
                )
            }
            else -> Unit
        }
    }
}

@Preview
@Composable
fun MainUIPreview(){
    L4k06x20231107Theme {

        MainUI(null, Modifier.fillMaxSize())
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTextContent(
    text: String,
    modifier: Modifier = Modifier,
    onChange: (String)->Unit = {},
    onSave:()->Unit={},
){
    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onSave){
            Text(stringResource(R.string.save))
        }
    }

}