package me.mrenxo.utilase.data

interface PerDataProvider<Index, T>{

    fun saveData(index: Index, data: T);


    fun getData(index: Index): T?;

}