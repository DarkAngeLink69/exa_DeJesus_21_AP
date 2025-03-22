package com.example.exa_dejesus_21_ap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText matricula, nombre, carrera, calificacion1, calificacion2, promedio;
    private Button alta, consulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        matricula=findViewById(R.id.etMatricula);
        nombre=findViewById(R.id.etNombre);
        carrera=findViewById(R.id.etCarrera);
        calificacion1=findViewById(R.id.etCalificacion1);
        calificacion2=findViewById(R.id.etCalificacion2);
        promedio=findViewById(R.id.etPromedio);

    }
    public void altaEmpleados(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); // Database object is writable

        // Capture values from form
        String Matricula = matricula.getText().toString();
        String Nombre = nombre.getText().toString();
        String Carrera = carrera.getText().toString();
        String Calificacion1 = calificacion1.getText().toString();}
        String Calificacion2 = calificacion2.getText().toString();
        String Promedio = promedio.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("matri", Matricula);
        registro.put("nombre", Nombre);
        registro.put("Carrera", Carrera);
        registro.put("Calificacion1", Calificacion1);
        registro.put("Calificacion2", Calificacion2);


        bd.insert("empleados", null, registro);

        bd.close();

        matricula.setText(null);
        nombre.setText(null);
        carrera.setText(null);
        calificacion1.setText(null);
        calificacion2.setText(null);
        promedio.setText(null);
        Toast.makeText(this, "Éxito al ingresar al estudiante\n\nMatricula: " + Matricula + "\nNombre: " + Nombre + "\nCarrera: " + Carrera + "\nCalificacion1: " + Calificacion1 + "\nCalificacion2" + Calificacion2 , Toast.LENGTH_LONG).show();
    }

    public void consultaEmpleados(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        String codigoConsulta = matricula.getText().toString();


        Cursor fila = bd.rawQuery("SELECT nombre, carrera, calificacion1, calificacion2, promedio FROM empleados WHERE matri = ?", new String[]{codigoConsulta});

        if (fila.moveToFirst()) {
            nombre.setText(fila.getString(0));
            carrera.setText(fila.getString(1));
            calificacion1.setText(fila.getString(2));
            calificacion2.setText(fila.getString(3));
            promedio.setText(fila.getString(4));

        }
    }

}