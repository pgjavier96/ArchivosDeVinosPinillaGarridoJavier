package com.example.archivosdevinospinillagarridojavier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.archivosdevinospinillagarridojavier.data.Vino;
import com.example.archivosdevinospinillagarridojavier.util.Csv;
import com.example.archivosdevinospinillagarridojavier.util.EditarVino;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    private static EditText etId;
    private static ArrayList<Vino> ListVinos;
    private Context context;
    private LinearLayout linearLayout;
    Button btEdit,btAdd;

    public static EditText getEtId() {
        return etId;
    }

    public static ArrayList<Vino> getListVinos() {
        return ListVinos;
    }

    private void initialize() {
        btEdit = findViewById(R.id.btEdit);
        btAdd = findViewById(R.id.btAdd);
        context = this;
        etId = findViewById(R.id.etId);
        linearLayout = findViewById(R.id.linearLayout);
        ListVinos = new ArrayList<>();
        WriteVinos();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intencion);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIdVino();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    //selccionamos el vino de atraves del id
    private void selectIdVino() {
        for (int i = 0; i < ListVinos.size(); i++) {
            if (!etId.getText().toString().isEmpty()) {
                if (ListVinos.get(i).getId() == Long.parseLong(etId.getText().toString())) {
                    EditarVino vt = new EditarVino(this, ListVinos.get(i));
                    Intent intencion = vt.createIntent(context, ThirdActivity.class);
                    context.startActivity(intencion);
                }
            }
        }
    }
    //Escribimos uan linea en el fichero una linea
    private void WriteVinos() {
        String[] vinos = TrabajandoConArchivos.getFileLines(getExternalFilesDir(null), "archivo.csv");
        if (vinos != null) {
            for (String linea : vinos) {
                Vino vino = Csv.getVino(linea);
                ListVinos.add(vino);
                EditarVino vt = new EditarVino(this, vino);
                linearLayout.addView(vt);
            }
        }
    }
}