package com.example.dm2.contentproviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button btnInsertar;
    private Button btnConsultar;
    private Button btnEliminar;
    private TextView txtResultados;
    private String cliente;
    private String telefono;
    private String email;
    private ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias a los controles
        txtResultados = (TextView)findViewById(R.id.TxtResultados);
        btnConsultar = (Button)findViewById(R.id.BtnConsultar);
        btnInsertar = (Button)findViewById(R.id.BtnInsertar);
        btnEliminar = (Button)findViewById(R.id.BtnEliminar);



        btnConsultar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Columnas de la tabla a recuperar
                String[] projection = new String[] {
                        ClientesProvider.Clientes._ID,
                        ClientesProvider.Clientes.COL_NOMBRE,
                        ClientesProvider.Clientes.COL_TELEFONO,
                        ClientesProvider.Clientes.COL_EMAIL };

                Uri clientesUri =  ClientesProvider.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                //Hacemos la consulta
                Cursor cur = cr.query(clientesUri,
                        projection, //Columnas a devolver
                        null,       //Condición de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados
                if (cur.moveToFirst())
                {
                    String nombre;
                    String telefono;
                    String email;

                    int colNombre = cur.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);
                    int colTelefono = cur.getColumnIndex(ClientesProvider.Clientes.COL_TELEFONO);
                    int colEmail = cur.getColumnIndex(ClientesProvider.Clientes.COL_EMAIL);

                    txtResultados.setText("");

                    do
                    {

                        nombre = cur.getString(colNombre);
                        telefono = cur.getString(colTelefono);
                        email = cur.getString(colEmail);

                        txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

                    } while (cur.moveToNext());
                }
            }
        });

        btnInsertar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent intent=new Intent(MainActivity.this,Dialogo.class);
                startActivityForResult(intent,1);
            }
        });

        btnEliminar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cr = getContentResolver();


                final AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);

                alertDialog1.setTitle("Eliminar cliente");
                alertDialog1.setView(R.layout.activity_eliminar_usuario);
                //columnas de la tabla a recuperar

                String[] projection=new String[]{
                        ClientesProvider.Clientes._ID,
                        ClientesProvider.Clientes.COL_NOMBRE};
                Uri clientesUri=ClientesProvider.CONTENT_URI;

                //hacemos la consulta

                final Cursor c= cr.query(clientesUri,projection,null,null,null);

                alertDialog1.setMultiChoiceItems(c,ClientesProvider.Clientes.COL_NOMBRE,ClientesProvider.Clientes.COL_NOMBRE, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked)
                        {
                            c.moveToFirst();
                            cr.delete(ClientesProvider.CONTENT_URI,ClientesProvider.Clientes.COL_NOMBRE+"='"+
                                    c.getString(which)+"'",null);
                            Toast.makeText(MainActivity.this,c.getType(which)+" eliminado",Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    }
                });
                alertDialog1.create().show();
               /* if(c.moveToFirst())
                {
                    String nombre;


                    int colNombre=c.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);


                    do{
                        nombre=c.getString(colNombre);
                        adaptador.add(nombre);
                    }while(c.moveToNext());
                }*/

            }
        });

    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1&&resultCode==RESULT_OK)
        {
            cliente = data.getExtras().getString("cliente");
            telefono =data.getExtras().getString("telefono");
            email = data.getExtras().getString("email");
            ContentValues values = new ContentValues();
            values.put(ClientesProvider.Clientes.COL_NOMBRE, cliente);
            values.put(ClientesProvider.Clientes.COL_TELEFONO, telefono);
            values.put(ClientesProvider.Clientes.COL_EMAIL, email);

            ContentResolver cr = getContentResolver();

            cr.insert(ClientesProvider.CONTENT_URI, values);
        }
    }
}