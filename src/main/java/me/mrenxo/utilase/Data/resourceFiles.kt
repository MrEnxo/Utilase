package me.mrenxo.utilase.Data

import org.bukkit.plugin.java.JavaPlugin
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors


object resourceFiles {


    fun getFileOrResourceFile(file: File, resourceFile: String, plugin: JavaPlugin): File {
        if (!file.exists()) {
            try {
                file.createNewFile();
                val resourceFileStream = plugin.getResource(resourceFile)
                val resourceString = BufferedReader(InputStreamReader(resourceFileStream!!, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"))
                val writer: Writer = FileWriter(file, false)
                writer.write(resourceString)
                writer.flush()
                writer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return file;
    }

}
