package com.example.notesapp.Notes.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notesapp.Notes.model.NotesResponseItem
import com.example.notesapp.Notes.viewModel.NotesViewModel
import com.example.notesapp.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {

    val notesState = viewModel.notesState.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var noteToUpdate by remember { mutableStateOf<NotesResponseItem?>(null) }
    var noteToDelete by remember { mutableStateOf<NotesResponseItem?>(null) }



    Box(modifier = Modifier.fillMaxSize()) {

        if (viewModel.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        else{
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(notesState.value.size) { index ->
                    NoteItem(notesState.value[index], onUpdateClick = { selectedNote ->
                        noteToUpdate = selectedNote
                        showUpdateDialog = true
                    }, onLongPress = { selectedNote ->
                        noteToDelete = selectedNote
                        showDeleteDialog = true
                    })
                }
            }
        }

        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(50.dp),
                contentColor = MaterialTheme.colorScheme.primary,
                content = {
                    Icon(
                        painterResource(R.drawable.baseline_add_circle_24),
                        contentDescription = "Add New Note."
                    )
                }
            )
        }

        if (showDialog) {
            showAddNoteDialog(viewModel = viewModel, onDialogClose = { showDialog = false })
        }
        if (showUpdateDialog && noteToUpdate != null) {
            showUpdateDialog(noteToUpdate!!, viewModel = viewModel, onDialogClose = {
                showUpdateDialog = false
                noteToUpdate = null
            })
        }
        if (showDeleteDialog && noteToDelete != null) {
            showDeleteNoteDialog(noteToDelete!!, viewModel, onDialogClose = {
                showDeleteDialog = false
                noteToDelete = null
            })
        }

    }


    @Composable
    fun showDeleteNoteDialog(
        noteToDelete: NotesResponseItem,
        viewModel: NotesViewModel,
        onDialogClose: () -> Unit
    ) {

        var noteId by remember {
            mutableStateOf(noteToDelete._id)
        }

        AlertDialog(onDismissRequest = { onDialogClose },
            title = { Text(text = "Delete Note") },
            text = {
                Text("Are you sure you want to delete this note?")
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteNote(noteId)
                    onDialogClose()
                }) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                     onDialogClose()
                }) {
                    Text(text = "Cancel")
                }
            })
    }

    @Composable
    fun showUpdateDialog(
        noteToUpdate: NotesResponseItem,
        viewModel: NotesViewModel,
        onDialogClose: () -> Unit
    ) {

        var noteTitle by remember {
            mutableStateOf(noteToUpdate.title)
        }
        var noteDescription by remember {
            mutableStateOf(noteToUpdate.description)
        }
        var noteId by remember {
            mutableStateOf(noteToUpdate._id)
        }

        AlertDialog(onDismissRequest = { onDialogClose },
            title = { Text(text = "Update Note") },
            text = {
                Column {
                    TextField(
                        value = noteTitle,
                        onValueChange = { noteTitle = it },
                        label = { Text(text = "Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = noteDescription,
                        onValueChange = { noteDescription = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.description)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )


                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateNote(noteId,noteTitle,noteDescription)
                    //viewModel.updateNote(noteId, noteTitle, noteDescription)
                    onDialogClose()
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDialogClose()
                }) {
                    Text(text = "Cancel")
                }
            })

    }


    @Composable
    fun showAddNoteDialog(viewModel: NotesViewModel, onDialogClose: () -> Unit) {
        var noteTitle by remember { mutableStateOf("") }
        var noteDescription by remember { mutableStateOf("") }

        AlertDialog(onDismissRequest = {
            val (newTitle, newDescription) = resetFields()
            noteTitle = newTitle
            noteDescription = newDescription
            onDialogClose
        },
            title = { Text(text = stringResource(id = R.string.add_note)) },
            text = {
                Column {
                    TextField(value = noteTitle, onValueChange = { noteTitle = it }, label = {
                        Text(
                            text = stringResource(
                                id = R.string.title
                            )
                        )
                    }, modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = noteDescription,
                        onValueChange = { noteDescription = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.description)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.sendNotesRequest(noteTitle, noteDescription)
                    val (newTitle, newDescription) = resetFields() // Reset fields after saving note
                    noteTitle = newTitle
                    noteDescription = newDescription
                    onDialogClose()
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDialogClose()
                }) {
                    Text(text = "Cancel")
                }
            })


    }


    fun resetFields(): Pair<String, String> {
        return "" to ""
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun NoteItem(
        note: NotesResponseItem,
        onUpdateClick: (NotesResponseItem) -> Unit,
        onLongPress: (NotesResponseItem) -> Unit
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onUpdateClick(note) },
                    onLongClick = { onLongPress(note) }
                )

        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = note.title, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = note.description, style = MaterialTheme.typography.bodyMedium)

            }
        }
    }





