package com.example.archivosdevinospinillagarridojavier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.archivosdevinospinillagarridojavier.data.Vino;
import com.example.archivosdevinospinillagarridojavier.util.Csv;

public class ThirdActivity extends AppCompatActivity {

    private EditText etId, etNombre, etBodega, etColor, etOrigen, eTGraduacion, eTFecha;
    private EditText[] editText = new EditText[7];
    private Vino vino;
    Button btEdit,btDelete,btPrevious;

    //borramos un vino
    private boolean deleteLinea() {
        return TrabajandoConArchivos.deleteLine(getExternalFilesDir(null), "archivo.csv", Long.toString(vino.getId()));
    }

    //editamos la linea que contiene la infortmacoion del vino
    private void editRegistro() {
        if (editVino()) {
            if (RewriteLinea()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    //Recogemos todos los campos para despuies podemos
    private boolean editVino() {
        boolean resultado = true;
        String[] atributos = new String[editText.length];
        for (int i = 0; i < atributos.length; i++) {
            atributos[i] = editText[i].getText().toString();
        }
        try {
            vino.setId(Long.parseLong(atributos[0].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }

        vino.setNombre(atributos[1].trim());
        vino.setBodega(atributos[2].trim());
        vino.setColor(atributos[3].trim());
        vino.setOrigen(atributos[4].trim());

        try {
            vino.setGraduacion(Double.parseDouble(atributos[5].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        try {
            vino.setFecha(Integer.parseInt(atributos[6].trim()));
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }

    private boolean writeLinea() {
        return TrabajandoConArchivos.writeLine(getExternalFilesDir(null), "archivo.csv", Csv.getCsv(vino));
    }
    private void initialize() {
        Bundle bundle = getIntent().getExtras();
        vino = bundle.getParcelable("vino");

        btEdit = findViewById(R.id.btSecEdit);
        btDelete = findViewById(R.id.btDelete);
        btPrevious = findViewById(R.id.btPrevious);
        etId = findViewById(R.id.etId);
        etId.setText(String.valueOf(vino.getId()));
        etId.setEnabled(false);
        etNombre = findViewById(R.id.etNombre);
        etNombre.setText(vino.getNombre());
        etBodega = findViewById(R.id.etBodega);
        etBodega.setText(vino.getBodega());
        etColor = findViewById(R.id.etColor);
        etColor.setText(vino.getColor());
        etOrigen = findViewById(R.id.etOrigen);
        etOrigen.setText(vino.getOrigen());
        eTGraduacion = findViewById(R.id.etGraduacion);
        eTGraduacion.setText(String.valueOf(vino.getGraduacion()));
        eTFecha = findViewById(R.id.etFecha);
        eTFecha.setText(String.valueOf(vino.getFecha()));

        editText[0] = etId;
        editText[1] = etNombre;
        editText[2] = etBodega;
        editText[3] = etColor;
        editText[4] = etOrigen;
        editText[5] = eTGraduacion;
        editText[6] = eTFecha;


        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRegistro();
            }
        });


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLinea();
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btPrevious.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                 startActivity(intent);
             }
         });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initialize();
    }

    private boolean RewriteLinea() {
        boolean delete = deleteLinea();
        if (delete) {
            boolean escrito = writeLinea();
            if (escrito) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}