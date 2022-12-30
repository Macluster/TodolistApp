package com.example.todolistapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todolistapp.ui.theme.TodolistAppTheme
import java.util.*

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodolistAppTheme {

                val view = viewModels<ItemViewModel>()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)) {
                        Column {

                            NavBar()
                            LazyColumn(modifier = Modifier.padding(10.dp)) {
                                items(view.value.list.size) { item ->
                                    ItemCard(
                                        view.value.list[item]
                                    )
                                }
                            }


                        }
                        FloatingActionButton(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            onClick = { view.value.dialogFlag = true },
                        ) {

                        }

                        if (view.value.dialogFlag == true) DialogBox(view)
                    }

                }
            }
        }
    }
}

@Composable
fun NavBar() {


    Card(
        modifier = Modifier
            .height(95.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(20.dp)) {

            Text(text = "My To Do List App", fontSize = 30.sp, color = Color.Black)
        }
    }
}

@Composable
fun DialogBox(view: Lazy<ItemViewModel>) {

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    var mTime by remember {
        mutableStateOf("")
    }
    var timePicker = TimePickerDialog(
        LocalContext.current,
        { _, mHour: Int, mMinute: Int ->
            mTime = "$mHour:$mMinute"
        }, mHour, mMinute, false

    )


    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    var mDate by remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val DatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )



    Dialog(


        onDismissRequest = { /*TODO*/ }) {

        Card(
            modifier = Modifier
                .height(250.dp)
                .width(300.dp)
        ) {

            var taskName = remember { mutableStateOf("") }
            var taskDesc = remember { mutableStateOf("") }



            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Add Details", fontSize = 30.sp)
                TextField(value = taskName.value, onValueChange = { taskName.value = it })
                TextField(value = taskDesc.value, onValueChange = { taskDesc.value = it })

                Row() {
                    Button(onClick = { timePicker.show() }) {
                        Text(text = "Set Time")
                    }
                    Button(onClick = { }) {
                        Button(onClick = { DatePickerDialog.show() }) {
                            Text(text = "Set Date")
                        }
                    }



                    Button(onClick = {
                        view.value.addToList(
                            ItemModel(
                                taskName.value,
                                taskDesc.value,
                                mTime,
                                mDate
                            )
                        );view.value.dialogFlag = false
                    }) {
                        Text(text = "Submit")

                    }
                    LocalContext


                }
            }


        }
    }
}


@Composable
fun ItemCard(model :ItemModel) {

    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.Gray, modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()


    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = model.taskName, fontSize = 20.sp, fontWeight = FontWeight(900), color = Color.White)

            Text(text = model.taskDesc)
            Row(){

                Text(text = model.taskTime)
                Text(text = model.taskDate)
            }

        }

    }
}

