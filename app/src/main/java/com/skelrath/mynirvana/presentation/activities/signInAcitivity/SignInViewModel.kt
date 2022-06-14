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
        insertMeditationListInRoomDatabase(getMeditationListFromRealTimeDatabase(reference, id))
        insertPomodoroListInRoomDatabase(getPomodoroListFromRealTimeDatabase(reference, id))
        insertTaskListInRoomDatabase(getTaskListFromRealTimeDatabase(reference, id))
        insertHabitListInRoomDatabase(getHabitListFromRealTimeDatabase(reference, id))

    }

    private fun getMeditationListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ): List<Meditation> {
        val meditationList = mutableListOf<Meditation>()

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
                            val meditation = meditationForRealTimeDatabase.let { it1 ->
                                MeditationForRealTimeDatabase.convertMeditationForRealTimeDatabaseIntoMeditation(
                                    it1, applicationContext
                                )
                            }
                            meditation.let { meditationList.add(it) }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        return meditationList
    }

    private fun getPomodoroListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ): List<Pomodoro> {
        val pomodoroList = mutableListOf<Pomodoro>()

        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.POMODOROS).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<List<PomodoroForRealTimeDatabase?>> =
                            object : GenericTypeIndicator<List<PomodoroForRealTimeDatabase?>>() {}

                        val pomodoroListFromRealTimeDatabase: List<PomodoroForRealTimeDatabase?>? =
                            snapshot.getValue(t)

                        pomodoroListFromRealTimeDatabase?.forEach { meditationForRealTimeDatabase ->
                            val pomodoro = meditationForRealTimeDatabase?.let { it1 ->
                                PomodoroForRealTimeDatabase.convertPomodoroForRealTimeDatabaseIntoPomodoro(
                                    it1,
                                    applicationContext
                                )
                            }
                            pomodoro?.let { pomodoroList.add(it) }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        return pomodoroList
    }

    private fun getTaskListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ): List<Task> {
        val taskList = mutableListOf<Task>()

        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.TASKS).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<List<TaskForRealTimeDatabase?>> =
                            object : GenericTypeIndicator<List<TaskForRealTimeDatabase?>>() {}

                        val taskFromRealTimeDatabaseList: List<TaskForRealTimeDatabase?>? =
                            snapshot.getValue(t)

                        taskFromRealTimeDatabaseList?.forEach { taskForRealTimeDatabase ->
                            val task =
                                taskForRealTimeDatabase?.let {
                                    TaskForRealTimeDatabase.convertTaskForRealTimeDatabaseIntoTask(
                                        it
                                    )
                                }

                            task?.let { taskList.add(it) }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        return taskList

    }

    private fun getHabitListFromRealTimeDatabase(
        databaseReference: DatabaseReference,
        userId: String
    ): List<Habit> {
        val habitList = mutableListOf<Habit>()

        databaseReference.child(FireBaseConstants.USERS).child(userId)
            .child(FireBaseConstants.HABITS).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val t: GenericTypeIndicator<List<HabitForRealTimeDatabase?>> =
                            object : GenericTypeIndicator<List<HabitForRealTimeDatabase?>>() {}

                        val habitFromRealTimeDatabaseList: List<HabitForRealTimeDatabase?>? =
                            snapshot.getValue(t)

                        habitFromRealTimeDatabaseList?.forEach { habitForRealTimeDatabase ->
                            val habit =
                                habitForRealTimeDatabase?.let {
                                    HabitForRealTimeDatabase.convertHabitForRealTimeDatabaseIntoHabit(
                                        it
                                    )
                                }
                            habit?.let { habitList.add(it) }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

        return habitList
    }

    private fun insertMeditationListInRoomDatabase(meditationList: List<Meditation>) {
        meditationList.forEach { meditation ->
            viewModelScope.launch {
                meditationUseCases.addMeditationUseCase.invoke(meditation)
            }
        }
    }

    private fun insertPomodoroListInRoomDatabase(pomodoroList: List<Pomodoro>) {
        pomodoroList.forEach { pomodoro ->
            viewModelScope.launch {
                pomodoroUseCases.addPomodoroUseCase.invoke(pomodoro)
            }
        }
    }

    private fun insertTaskListInRoomDatabase(taskList: List<Task>) {
        taskList.forEach { task ->
            viewModelScope.launch {
                taskUseCases.addTaskUseCase.invoke(task)
            }
        }
    }

    private fun insertHabitListInRoomDatabase(habitList: List<Habit>) {
        habitList.forEach { habit ->
            viewModelScope.launch {
                habitUseCases.addHabitUseCase.invoke(habit)
            }
        }
    }

    fun getUserNameFromRealTimeDatabase(
        firebaseUser: FirebaseUser,
        firebaseDatabase: FirebaseDatabase
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
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })


    }

    private fun saveUserNameToSharedPreferences(userName: String) {
        viewModelScope.launch {
            sharedPreferencesUseCases.changeUserNameUseCase.invoke(userName)
        }
    }

    private fun createMeditationCourses() {
        viewModelScope.launch { meditationCoursesUseCases.createMeditationCoursesUseCase.invoke() }
    }

}
