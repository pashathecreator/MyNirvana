package com.skelrath.mynirvana.presentation.activities.signInAcitivity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.skelrath.mynirvana.domain.habit.model.Habit
import com.skelrath.mynirvana.domain.habit.model.HabitForRealTimeDatabase
import com.skelrath.mynirvana.domain.habit.useCases.HabitUseCases
import com.skelrath.mynirvana.domain.meditations.model.meditation.Meditation
import com.skelrath.mynirvana.domain.meditations.model.meditation.MeditationForRealTimeDatabase
import com.skelrath.mynirvana.domain.meditations.usecases.meditationCoursesUseCases.MeditationCoursesUseCases
import com.skelrath.mynirvana.domain.meditations.usecases.userMeditationsUseCases.MeditationUseCases
import com.skelrath.mynirvana.domain.pomodoro.model.Pomodoro
import com.skelrath.mynirvana.domain.pomodoro.model.PomodoroForRealTimeDatabase
import com.skelrath.mynirvana.domain.pomodoro.useCases.PomodoroUseCases
import com.skelrath.mynirvana.domain.sharedPreferences.usecases.SharedPreferencesUseCases
import com.skelrath.mynirvana.domain.task.model.Task
import com.skelrath.mynirvana.domain.task.model.TaskForRealTimeDatabase
import com.skelrath.mynirvana.domain.task.useCases.TaskUseCases
import com.skelrath.mynirvana.presentation.fireBaseConstants.FireBaseConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val applicationContext: Context,
    private val meditationUseCases: MeditationUseCases,
    private val pomodoroUseCases: PomodoroUseCases,
    private val taskUseCases: TaskUseCases,
    private val habitUseCases: HabitUseCases,
    private val sharedPreferencesUseCases: SharedPreferencesUseCases,
    private val meditationCoursesUseCases: MeditationCoursesUseCases
) : ViewModel() {

    fun downloadDataOfUserFromRealtimeDatabase(
        firebaseUser: FirebaseUser,
        firebaseDatabase: FirebaseDatabase
    ) {
        createMeditationCourses()
        val reference = firebaseDatabase.reference
        val id = firebaseUser.uid
        getMeditationListFromRealTimeDatabase(reference, id)
        getPomodoroListFromRealTimeDatabase(reference, id)
        getTaskListFromRealTimeDatabase(reference, id)
        getHabitListFromRealTimeDatabase(reference, id)

    }

    private fun getMeditationListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ) {

        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.MEDITATIONS).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<HashMap<String, MeditationForRealTimeDatabase>?> =
                            object :
                                GenericTypeIndicator<HashMap<String, MeditationForRealTimeDatabase>?>() {}

                        val map: HashMap<String, MeditationForRealTimeDatabase>? =
                            snapshot.getValue(t)


                        val meditationFromRealTimeDatabaseList: ArrayList<MeditationForRealTimeDatabase>? =
                            map?.values?.let { ArrayList(it) }

                        meditationFromRealTimeDatabaseList?.forEach { meditationForRealTimeDatabase ->
                            val meditation =
                                MeditationForRealTimeDatabase.convertMeditationForRealTimeDatabaseIntoMeditation(
                                    meditationForRealTimeDatabase, applicationContext
                                )
                            insertMeditationInRoomDatabase(meditation)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

    }

    private fun getPomodoroListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.POMODOROS).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<HashMap<String, PomodoroForRealTimeDatabase>?> =
                            object :
                                GenericTypeIndicator<HashMap<String, PomodoroForRealTimeDatabase>?>() {}

                        val map: HashMap<String, PomodoroForRealTimeDatabase>? =
                            snapshot.getValue(t)


                        val pomodoroFromRealTimeDatabaseList: ArrayList<PomodoroForRealTimeDatabase>? =
                            map?.values?.let { ArrayList(it) }

                        pomodoroFromRealTimeDatabaseList?.forEach { pomodoroForRealTimeDatabase ->
                            val pomodoro =
                                PomodoroForRealTimeDatabase.convertPomodoroForRealTimeDatabaseIntoPomodoro(
                                    pomodoroForRealTimeDatabase,
                                    applicationContext
                                )
                            insertPomodoroInRoomDatabase(pomodoro)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
    }

    private fun getTaskListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ) {
        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.TASKS).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<HashMap<String, TaskForRealTimeDatabase>?> =
                            object :
                                GenericTypeIndicator<HashMap<String, TaskForRealTimeDatabase>?>() {}

                        val map: HashMap<String, TaskForRealTimeDatabase>? =
                            snapshot.getValue(t)


                        val taskFromRealTimeDatabaseList: ArrayList<TaskForRealTimeDatabase>? =
                            map?.values?.let { ArrayList(it) }

                        taskFromRealTimeDatabaseList?.forEach { taskForRealTimeDatabase ->
                            val task =
                                TaskForRealTimeDatabase.convertTaskForRealTimeDatabaseIntoTask(
                                    taskForRealTimeDatabase
                                )
                            insertTaskInRoomDatabase(task)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


    }

    private fun getHabitListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ) {

        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.HABITS).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<HashMap<String, HabitForRealTimeDatabase>?> =
                            object :
                                GenericTypeIndicator<HashMap<String, HabitForRealTimeDatabase>?>() {}

                        val map: HashMap<String, HabitForRealTimeDatabase>? =
                            snapshot.getValue(t)


                        val habitFromRealTimeDatabaseList: ArrayList<HabitForRealTimeDatabase>? =
                            map?.values?.let { ArrayList(it) }

                        habitFromRealTimeDatabaseList?.forEach { habitForRealTimeDatabase ->
                            val habit =
                                HabitForRealTimeDatabase.convertHabitForRealTimeDatabaseIntoHabit(
                                    habitForRealTimeDatabase
                                )
                            insertHabitInRoomDatabase(habit)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
    }

    private fun insertMeditationInRoomDatabase(meditation: Meditation) =
        viewModelScope.launch {
            meditationUseCases.addMeditationUseCase.invoke(meditation)
        }


    private fun insertPomodoroInRoomDatabase(pomodoro: Pomodoro) =
        viewModelScope.launch {
            pomodoroUseCases.addPomodoroUseCase.invoke(pomodoro)
        }


    private fun insertTaskInRoomDatabase(task: Task) =
        viewModelScope.launch {
            taskUseCases.addTaskUseCase.invoke(task)
        }


    private fun insertHabitInRoomDatabase(habit: Habit) =
        viewModelScope.launch {
            habitUseCases.addHabitUseCase.invoke(habit)
        }


    fun getUserNameFromRealTimeDatabase(
        firebaseUser: FirebaseUser,
        firebaseDatabase: FirebaseDatabase,
        functionToLaunch: () -> Unit
    ) {
        val reference = firebaseDatabase.reference
        val id = firebaseUser.uid

        reference.child(FireBaseConstants.USERS).child(id).child(FireBaseConstants.USER_NAME)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<String?> =
                            object : GenericTypeIndicator<String?>() {}

                        val userNameFromRealTimeDatabase: String? = snapshot.getValue(t)

                        userNameFromRealTimeDatabase?.let {
                            saveUserNameToSharedPreferences(it)
                            functionToLaunch()
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })


    }

    private fun saveUserNameToSharedPreferences(userName: String) =
        sharedPreferencesUseCases.changeUserNameUseCase.invoke(userName)


    private fun createMeditationCourses() {
        viewModelScope.launch { meditationCoursesUseCases.createMeditationCoursesUseCase.invoke() }
    }
}
