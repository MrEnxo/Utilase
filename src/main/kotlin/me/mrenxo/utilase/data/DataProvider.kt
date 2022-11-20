package me.mrenxo.utilase.data

interface DataProvider<T> {


    fun saveData(data: T) ;


    fun getData(): T?;

}