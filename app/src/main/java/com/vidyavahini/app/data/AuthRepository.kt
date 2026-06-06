package com.vidyavahini.app.data

import com.google.firebase.auth.FirebaseAuth
import com.vidyavahini.app.model.Student
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AuthRepository(private val cache: LocalCache) {
    private val firebaseAuth by lazy { runCatching { FirebaseAuth.getInstance() }.getOrNull() }

    suspend fun login(email: String, password: String): Student {
        val firebaseUser = runCatching { firebaseAuth?.signInWithEmailAndPassword(email, password)?.await()?.user }.getOrNull()
        val student = Student(firebaseUser?.uid ?: UUID.nameUUIDFromBytes(email.toByteArray()).toString(), email.substringBefore("@").replaceFirstChar { it.uppercase() }, email)
        cache.saveStudent(student)
        return student
    }

    suspend fun signup(name: String, email: String, password: String): Student {
        val firebaseUser = runCatching { firebaseAuth?.createUserWithEmailAndPassword(email, password)?.await()?.user }.getOrNull()
        val student = Student(firebaseUser?.uid ?: UUID.randomUUID().toString(), name.ifBlank { "Student" }, email)
        cache.saveStudent(student)
        return student
    }

    fun currentStudent(): Student? = firebaseAuth?.currentUser?.let { Student(it.uid, it.email?.substringBefore("@") ?: "Student", it.email.orEmpty()) } ?: cache.getStudent()

    fun logout() {
        runCatching { firebaseAuth?.signOut() }
        cache.clearStudent()
    }
}
