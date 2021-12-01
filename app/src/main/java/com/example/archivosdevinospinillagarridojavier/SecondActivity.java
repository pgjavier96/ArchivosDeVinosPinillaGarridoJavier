package com.example.archivosdevinospinillagarridojavier;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.archivosdevinospinillagarridojavier.data.Vino;
import com.example.archivosdevinospinillagarridojavier.util.Csv;


public class SecondActivity extends AppCompatActivity {
    private EditText etId, etNombre, etBodega, etColor, etOrigen, etGraduacion, etFecha;
    private TextView tvText;
    private EditText[] editText = new EditText[7];
    Button btAdd,btPrevious;

    //cramos el vino y lo escribimoes en el archivo.csv
    private void crearVino() {
        if (editTextsRellenos()) {
            Vino vino = getVino();
            if (!existeVino(vino)) {
                String lineaCSV = Csv.getCsv(vino);
                TrabajandoConArchivos.writeLine(getExternalFilesDir(null), "archivo.csv", lineaCSV);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean editTextsRellenos() {
        boolean relleno = true;
        for (EditText editText : editText) {
            if (editText.getText().toString().isEmpty()) {
                relleno = false;
            }
        }
        return relleno;
    }

    //Metodo que atraves del id comprobamos si dicho vino existe
    private boolean existeVino(Vino vino) {
        boolean existe = false;
        for (Vino v : MainActivity.getListVinos()) {
            if (v.getId() == vino.getId()) {
                existe = true;
            }
        }
        return existe;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initialize();
    }

    private void initialize() {
        btAdd = findViewById(R.id.btSecAdd);
        btPrevious = findViewById(R.id.btPrevious);
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etBodega = findViewById(R.id.etBodega);
        etColor = findViewById(R.id.etColor);
        etOrigen = findViewById(R.id.etOrigen);
        etGraduacion = findViewById(R.id.etGraduacion);
        etFecha = findViewById(R.id.etFecha);
        tvText = findViewById(R.id.tvScroll);


        editText[0] = etId;
        editText[1] = etNombre;
        editText[2] = etBodega;
        editText[3] = etColor;
        editText[4] = etOrigen;
        editText[5] = etGraduacion;
        editText[6] = etFecha;


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearVino();
            }
        });

        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Obtemos todos los campos que hemos escrito por pantalla para poder crear el vino a partir de ellos
    private Vino getVino() {
        Vino v = new Vino();
        try {
            v.setId(Long.parseLong(editText[0].getText().toString().trim()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }

        v.setNombre(editText[1].getText().toString().trim());
        v.setBodega(editText[2].getText().toString().trim());
        v.setColor(editText[3].getText().toString().trim());
        v.setOrigen(editText[4].getText().toString().trim());

        try {
            v.setGraduacion(Double.parseDouble(editText[5].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        try {
            v.setFecha(Integer.parseInt(editText[6].getText().toString()));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return v;
    }

}
