package com.example.loginauth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText txtTema;
    Spinner spinAreas, spinSecciones;
    Button btnRegistrar;
    private DatabaseReference Clases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtTema = (EditText) findViewById(R.id.txttema);
        spinAreas = (Spinner) findViewById(R.id.spinarea);
        spinSecciones = (Spinner) findViewById(R.id.spinseccion);
        btnRegistrar = (Button) findViewById(R.id.btnregistrar);

        Clases = FirebaseDatabase.getInstance().getReference("Clases");
        btnRegistrar = (Button)findViewById(R.id.btnregistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                registrarClase();
            }
        });
    }

    public void registrarClase() {
        String seccion = spinSecciones.getSelectedItem().toString();
        String area = spinAreas.getSelectedItem().toString();
        String tema = txtTema.getText().toString();

        if (!TextUtils.isEmpty(tema)) {
            String id = Clases.push().getKey();
            Clases leccion = new Clases(id, seccion, area, tema);
            Clases.child("Lecciones").child(id).setValue(leccion);

            Toast.makeText(this, "Clase Registrada",
                    Toast.LENGTH_LONG).show();

        }
    }
}