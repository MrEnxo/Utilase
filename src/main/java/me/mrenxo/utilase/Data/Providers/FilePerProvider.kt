package me.mrenxo.utilase.Data.Providers

import me.mrenxo.utilase.Data.PerDataProvider
import me.mrenxo.utilase.Data.Serializer
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.Writer

class FilePerProvider<Index, T>(private val Folder: File, private val indexer: (index: Index) -> String, private val serializer: Serializer<T>) : PerDataProvider<Index, T> {

    init {
        if (!Folder.exists()) Folder.mkdir()
    }


    override fun saveData(index: Index, data: T) {
        val saveFile = File(Folder.absolutePath + "/" + indexer(index))
        if (!saveFile.exists()) saveFile.createNewFile();

        val writer: Writer = FileWriter(saveFile, false)

        writer.write(serializer.serialize(data))
        writer.flush()
        writer.close()

    }

    override fun getData(index: Index): T? {
        val saveFile = File(Folder.absolutePath + "/" + indexer(index))
        if (!saveFile.exists()) return null

        val reader = FileReader(saveFile)
        val data = serializer.deserialize(reader.readText());
        reader.close();

        return data

    }
}