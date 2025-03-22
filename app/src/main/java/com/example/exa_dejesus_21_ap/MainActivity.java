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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        matricula = findViewById(R.id.etMatricula);
        nombre = findViewById(R.id.etNombre);
        carrera = findViewById(R.id.etCarrera);
        calificacion1 = findViewById(R.id.etCalificacion1);
        calificacion2 = findViewById(R.id.etCalificacion2);
        promedio = findViewById(R.id.etPromedio);
    }

    public void altaEstudiantes(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String Matricula = matricula.getText().toString();
        String Nombre = nombre.getText().toString();
        String Carrera = carrera.getText().toString();
        String Calificacion1 = calificacion1.getText().toString();
        String Calificacion2 = calificacion2.getText().toString();

        // Verificar que las calificaciones no estén vacías
        if (Calificacion1.isEmpty() || Calificacion2.isEmpty()) {
            Toast.makeText(this, "Las calificaciones no pueden estar vacías.", Toast.LENGTH_SHORT).show();
            return;
        }

        float cal1 = Float.parseFloat(Calificacion1);
        float cal2 = Float.parseFloat(Calificacion2);
        float promedioCalculado = (cal1 + cal2) / 2;

        ContentValues registro = new ContentValues();
        registro.put("matri", Matricula);
        registro.put("nombre", Nombre);
        registro.put("Carrera", Carrera);
        registro.put("Calificacion1", cal1);
        registro.put("Calificacion2", cal2);
        registro.put("Promedio", promedioCalculado);

        bd.insert("estudiantes", null, registro); // Ahora usa la tabla correcta
        bd.close();

        matricula.setText(null);
        nombre.setText(null);
        carrera.setText(null);
        calificacion1.setText(null);
        calificacion2.setText(null);
        promedio.setText(String.valueOf(promedioCalculado));

        Toast.makeText(this, "Estudiante registrado correctamente.", Toast.LENGTH_LONG).show();
    }

    public void consultaEstudiantes(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String codigoConsulta = matricula.getText().toString();

        if (codigoConsulta.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una matrícula para consultar.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor fila = bd.rawQuery("SELECT nombre, carrera, calificacion1, calificacion2, promedio FROM estudiantes WHERE matri = ?", new String[]{codigoConsulta});

        try {
            if (fila.moveToFirst()) {
                nombre.setText(fila.getString(0));
                carrera.setText(fila.getString(1));
                calificacion1.setText(String.valueOf(fila.getFloat(2)));
                calificacion2.setText(String.valueOf(fila.getFloat(3)));
                promedio.setText(String.valueOf(fila.getFloat(4)));
            } else {
                Toast.makeText(this, "No se encontró un estudiante con esa matrícula.", Toast.LENGTH_LONG).show();
                nombre.setText(null);
                carrera.setText(null);
                calificacion1.setText(null);
                calificacion2.setText(null);
                promedio.setText(null);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al consultar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (fila != null) fila.close();
            bd.close();
        }
    }
}
