package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.beans.property.SimpleIntegerProperty
import javafx.concurrent.Task
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.StageStyle
import tornadofx.*
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

class MyView : View() {
    // TornadoFX delegates
    override val root: BorderPane by fxml()
    val controller: MyController by inject()
    // Observable
    val textArea: TextArea by fxid()
    val noufitxerButton: Button by fxid()
    val saveButton: Button by fxid()
    val searchButton: Button by fxid()
    val llegirButton: Button by fxid()

    var savedText: String  = ""

    val filepath: TextField by fxid()

    var test: String = ""

    init {
        saveButton.action {
            savedText = textArea.text
            guardarText(savedText, filepath.text)
        }

        searchButton.action{
            filepath.text = openFile().toString()
            test = filepath.text
        }

        llegirButton.action{
            llegirFitxer(filepath.text)
        }

        noufitxerButton.action {
            escriureNouFitxer(textArea.text, filepath.text)
        }
    }

    fun openFile() : File? {
        val fileChooser = FileChooser()
        fileChooser.title = "Obre Estada"

        fileChooser.initialDirectory = File("C:/Users/jbarb/Desktop/M06/Fitxers")
        fileChooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Texto", "*.txt")
        )
        val selectedFile: File? = fileChooser.showOpenDialog(currentWindow)
        return selectedFile
    }

    fun llegirFitxer(fileName: String){
        var text: String = ""
        try{
            val fileIn = FileReader(fileName)
            var i: Int

            do{
                i = fileIn.read()
                text += i.toChar()
            } while(i != -1)

            textArea.text = text
        } catch (ex: Exception){
        }
    }

    fun guardarText(texto: String, fileName: String){
        try {
            val fileOut = FileWriter(fileName)
            fileOut.write(texto)
            fileOut.close()
        }catch(ex: Exception){
            print(ex.message)
        }
    }

    private fun escriureNouFitxer(fileText: String, fileName: String): Boolean {

        try {
            val fileOut = FileWriter(fileName)
            fileOut.write(fileText)
            fileOut.close()
        } catch (ex: Exception) {
            print(ex.message)
            return true
        }
        return false
    }

}
