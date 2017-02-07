package com.example.dm2.contentproviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Dialogo extends AppCompatActivity {

    private EditText cliente;
    private EditText telefono;
    private EditText email;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogo);

        Button but= (Button) findViewById(R.id.butConfirmar);

        /*cliente = (EditText)v.findViewById(R.id.cliente);
        telefono = (EditText)v.findViewById(R.id.telefono);
        email = (EditText)v.findViewById(R.id.email);*/
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente = (EditText)findViewById(R.id.cliente);
                telefono = (EditText)findViewById(R.id.telefono);
                email = (EditText)findViewById(R.id.email);

                String cli=cliente.getText().toString();
                String tel=telefono.getText().toString();
                String mail=email.getText().toString();

                Intent intent=new Intent(Dialogo.this,MainActivity.class);
                intent.putExtra("cliente",cli);
                intent.putExtra("telefono",tel);
                intent.putExtra("email",mail);
                setResult(RESULT_OK,intent);
                finish();


            }
        });
    }
}
