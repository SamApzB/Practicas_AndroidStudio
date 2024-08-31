package com.example.almacenamientofichero;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextContent;
    private ListView listViewFiles;
    private File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextContent = findViewById(R.id.editTextContent);
        listViewFiles = findViewById(R.id.listViewFiles);
        dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyFiles");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        listViewFiles.setOnItemClickListener((parent, view, position, id) -> {
            String fileName = (String) parent.getItemAtPosition(position);
            showFileOptions(fileName);
        });
    }

    // Método para guardar el archivo en la tarjeta SD
    public void saveFile(View view) {
        if (isExternalStorageWritable()) {
            String content = editTextContent.getText().toString();
            if (content.isEmpty()) {
                Toast.makeText(this, "El contenido está vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            String fileName = "file_" + System.currentTimeMillis() + ".txt";
            File file = new File(dir, fileName);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(content.getBytes());
                Toast.makeText(this, "Archivo guardado en " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                editTextContent.setText("");
            } catch (IOException e) {
                Toast.makeText(this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "El almacenamiento externo no está disponible", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para ver los archivos creados
    public void viewFiles(View view) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            ArrayList<String> fileNames = new ArrayList<>();
            for (File file : files) {
                fileNames.add(file.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileNames);
            listViewFiles.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay archivos disponibles", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar opciones para el archivo seleccionado
    private void showFileOptions(String fileName) {
        File file = new File(dir, fileName);

        // Aquí puedes agregar un cuadro de diálogo para elegir entre editar o eliminar
        // Por simplicidad, mostraré solo el código para editar y eliminar
        // Implementar diálogo para más opciones es un ejercicio que se puede hacer aparte

        // Editar archivo
        editTextContent.setText("");
        editTextContent.append("Editando: " + fileName + "\n\n");
        editTextContent.append("Aquí puedes implementar la lógica para leer el archivo y mostrar su contenido.");

        // Eliminar archivo
        if (file.exists() && file.delete()) {
            Toast.makeText(this, "Archivo eliminado", Toast.LENGTH_SHORT).show();
            viewFiles(null);
        } else {
            Toast.makeText(this, "Error al eliminar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para verificar si el almacenamiento externo es escribible
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
