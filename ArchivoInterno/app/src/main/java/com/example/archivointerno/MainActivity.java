package com.example.archivointerno;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText textBox;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textBox = (EditText) findViewById(R.id.editText);
    }

    public void onClickSave(View view) {
        String str = textBox.getText().toString();
        try {
            // Almacenamiento Interno
            FileOutputStream fOut = openFileOutput("textfile.txt", Context.MODE_PRIVATE);
            try {
                fOut.write(str.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fOut.close();
            }
            // Muestra el mensaje de éxito
            Toast.makeText(getBaseContext(), "Archivo grabado satisfactoriamente!", Toast.LENGTH_SHORT).show();
            textBox.setText("");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void onClickLoad(View view) {
        try {
            FileInputStream fIn = openFileInput("textfile.txt");
            StringBuilder contenido = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    contenido.append(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fIn.close();
            }
            // Pone en el EditText el texto que ha sido leído
            textBox.setText(contenido.toString());
            Toast.makeText(getBaseContext(), "Archivo cargado satisfactoriamente!", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}