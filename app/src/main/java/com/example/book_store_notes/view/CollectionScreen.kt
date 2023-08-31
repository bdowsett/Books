package com.example.book_store_notes.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book_store_notes.model.db.DbNote
import com.example.book_store_notes.model.db.Note
import com.example.book_store_notes.viewmodel.CollectionDbViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(cvm: CollectionDbViewModel) {

    val booksInCollection = cvm.collection.collectAsState()
    val expandedElement = remember { mutableIntStateOf(-1) }
    val notes = cvm.notes.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(booksInCollection.value) { book ->
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(4.dp)
                        .clickable {
                            Log.d("Ben", "${expandedElement.intValue}")
                            if (expandedElement.intValue == book.id) {
                                expandedElement.intValue = -1
                            } else {
                                expandedElement.intValue = book.id
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BookImage(
                        url = book.thumbnail,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight(),
                        contentScale = ContentScale.FillHeight
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = book.title ?: "No title",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            maxLines = 2
                        )
                        Text(text = book.authors ?: "", fontStyle = FontStyle.Italic)
                    }

                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.clickable { cvm.deleteBook(book) })
                        if (book.id == expandedElement.intValue)
                            Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = null)
                        else {
                            Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
                        }
                    }
                }

                if (book.id == expandedElement.intValue) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x55DDE4FF))
                    ) {

                        val filteredNotes = notes.value.filter { note -> note.bookId == book.id }
                        NotesList(filteredNotes, cvm)

                        CreateNoteForm(bookId = book.id, cvm = cvm)
                    }
                }

                Divider(
                    color = Color.LightGray,
                    modifier = Modifier.padding(
                        top = 4.dp,
                        bottom = 4.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                )
            }

        }
    }
}

@Composable
fun NotesList(notes: List<DbNote>, cvm: CollectionDbViewModel) {
    for (note in notes) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFD5E4FF))
                .padding(4.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, fontWeight = FontWeight.Bold)
                Text(text = note.text)
            }
            Icon(
                Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.clickable { cvm.deleteNote(note) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteForm(bookId: Int, cvm: CollectionDbViewModel) {
    val addNoteToElement = remember { mutableStateOf(-1) }
    val newNoteTitle = remember { mutableStateOf("") }
    val newNoteText = remember { mutableStateOf("") }

    if (addNoteToElement.value == bookId) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .background(Color(0xFFD5E4FF))
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "Add note", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = newNoteTitle.value,
                onValueChange = { newNoteTitle.value = it },
                label = { Text(text = "Note title") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newNoteText.value,
                    onValueChange = { newNoteText.value = it },
                    label = { Text(text = "Note content") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Button(onClick = {
                    val note = Note(bookId, newNoteTitle.value, newNoteText.value)
                    cvm.addNote(note)
                    newNoteTitle.value = ""
                    newNoteText.value = ""
                    addNoteToElement.value = -1
                }) {
                    Icon(Icons.Default.Check, contentDescription = null)
                }
            }
        }
    }

    Button(onClick = {
        addNoteToElement.value = bookId
    }) {
        Icon(Icons.Default.Add, contentDescription = null)
    }
}