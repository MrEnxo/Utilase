package me.mrenxo.utilase.Data

interface DataProvider<T> {


    fun saveData(data: T) ;


    fun getData(): T?;

}