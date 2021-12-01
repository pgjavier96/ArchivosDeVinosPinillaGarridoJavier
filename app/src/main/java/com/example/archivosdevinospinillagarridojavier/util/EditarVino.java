package com.example.archivosdevinospinillagarridojavier.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.archivosdevinospinillagarridojavier.MainActivity;
import com.example.archivosdevinospinillagarridojavier.SecondActivity;
import com.example.archivosdevinospinillagarridojavier.data.Vino;


public class EditarVino extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {
    Vino vino;

    public Intent createIntent(Context contexto, Class clase) {
        Intent intent = new Intent(contexto, clase);
        Bundle bundle = new Bundle();
        bundle.putParcelable("vino", this.vino);
        intent.putExtras(bundle);
        return intent;
    }

    public EditarVino(@NonNull Context context, Vino vino) {
        super(context);
        this.vino = vino;
        this.setText(vino.getId() + ", " + vino.getNombre() + ", " + vino.getBodega() + ", " + vino.getColor() + ", " + vino.getFecha());
        this.setOnClickListener(this);
    }

    private void EditarVino() {
        this.getContext().startActivity(createIntent(this.getContext(), SecondActivity.class));
    }

    @Override
    public void onClick(View v) {
        ponerIdEditTextIdEditarMain(vino.getId());
        EditarVino();
    }

    private void ponerIdEditTextIdEditarMain(long id) {
        MainActivity.getEtId().setText(String.valueOf(id));
    }
}
