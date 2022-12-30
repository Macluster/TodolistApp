package com.example.todolistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ItemViewModel():ViewModel() {

    var dialogFlag by  mutableStateOf(false)

    var list =  mutableStateListOf<ItemModel>()

    var currTime by mutableStateOf("")

    var currDate by mutableStateOf("")



    fun changeDialogflag(value :Boolean)
    {
        dialogFlag=value;
    }


    fun addToList(item :ItemModel)
    {
        list.add(item)
    }


    fun changeTime(time:String)
    {
        currTime=time;
    }

    fun changeDate(date:String)
    {
        currDate=date;
    }



}