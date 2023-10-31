package com.example.project7

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database


class NoteViewModel : ViewModel(){
  private var auth: FirebaseAuth
    var user: User = User()
    var verifyPassword = ""
    var noteId: String = ""
    var note = MutableLiveData<Note>()
    private val _notes: MutableLiveData<MutableList<Note>> = MutableLiveData()
    val notes: LiveData<List<Note>>
        get() = _notes as LiveData<List<Note>>

    private val _navigateToNote = MutableLiveData<String?>()
    val navigateToNote: LiveData<String?>
        get() = _navigateToNote

    private val _errorHappened = MutableLiveData<String?>()
    val errorHappened: LiveData<String?>
        get() = _errorHappened

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    private val _navigateToSignUp = MutableLiveData<Boolean>(false)
    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    private val _navigateToSignIn = MutableLiveData<Boolean>(false)
    val navigateToSignIn: LiveData<Boolean>
        get() = _navigateToSignIn

    private lateinit var notesCollection: DatabaseReference


    init {
        auth = Firebase.auth
        if (noteId.trim() == "") {
            note.value = Note()
        }
        _notes.value = mutableListOf<Note>()
    }

//intialize the database reference
    fun initializeTheDatabaseReference() {

       val database = Firebase.database
        notesCollection = database
            .getReference("notes")
            .child(auth.currentUser!!.uid)


        notesCollection.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noteList = mutableListOf<Note>()
                for (noteSnapshot in snapshot.children) {
                    //Todo

                    var note = noteSnapshot.getValue<Note>()
                    note?.noteId = noteSnapshot.key!!
                    noteList.add(note!!)
                }
                _notes.value = noteList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("NoteViewModel", "onCancelled: ${error.message}")
            }
        })

    }


//add note to the database
    fun addNote(newNote: Note){
        Log.i("NoteViewModel", "addNote: ${newNote.noteTitle} ${newNote.noteDescription}")


        val newNoteReference = notesCollection.push()
        newNote.noteId = newNoteReference.key!!

        newNoteReference.setValue(newNote).addOnCompleteListener { note ->
            if (note.isSuccessful) {
                Log.i("NoteViewModel", "Note added successfully.")
            } else {
                _errorHappened.value = note.exception?.message
            }
        }


    }
//update note
    fun updateNote() {
        if (noteId.trim().isEmpty()) {
            notesCollection.push().setValue(note.value)
        } else {
            notesCollection.child(noteId).setValue(note.value)
        }
    }
//delete note
    fun deleteNote() {
        if (noteId.trim().isNotEmpty()) {
            notesCollection.child(noteId).removeValue()
            Log.i("NoteViewModel", "Note deleted successfully.")
        } else {

            _errorHappened.value = "Failed to delete the note. Invalid note ID."
        }
    }

//get current user
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


    fun onNavigatedToList() {
        _navigateToList.value = false
    }




//creates account on firebase
    fun signUp(){
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        if (user.password != verifyPassword) {
            _errorHappened.value = "Password and verify do not match."
            return
        }
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("NoteViewModel", "signUp: success")
                _navigateToSignIn.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }

    }



//sign in to firebase
    fun signIn() {
        if (user.email.isEmpty() || user.password.isEmpty()) {
            _errorHappened.value = "Email and password cannot be empty."
            return
        }
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if (it.isSuccessful) {
                initializeTheDatabaseReference()

                _navigateToList.value = true
            } else {
                _errorHappened.value = it.exception?.message
            }
        }
    }

//sing out of firebase
    fun signOut() {
        auth.signOut()
        _navigateToSignIn.value = true
    }







}