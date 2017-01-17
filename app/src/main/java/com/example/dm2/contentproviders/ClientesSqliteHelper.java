package com.example.dm2.contentproviders;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dm2 on 17/01/2017.
 */

public class ClientesSqliteHelper extends SQLiteOpenHelper {

        //Sentencia SQL para crear la tabla de Clientes
        String sqlCreate = "CREATE TABLE Clientes " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nombre TEXT, " +
                " telefono TEXT, " +
                " email TEXT )";

        public ClientesSqliteHelper(Context contexto, String nombre,
                                    CursorFactory factory, int version) {

            super(contexto, nombre, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String nombre;
            String telefono;
            String email;

            //Se ejecuta la sentencia SQL de creación de la tabla
            db.execSQL(sqlCreate);

            //Insertamos 5 clientes de ejemplo
            for(int i=1; i<=15; i++)
            {
                if(i<10)
                {
                    //Generamos los datos de muestra
                    nombre = "Cliente0" + i;
                    telefono = "900-123-00" + i;
                    email = "email0" + i + "@mail.com";
                }
                else
                {
                    nombre = "Cliente0" + i;
                    telefono = "900-123-00" + i;
                    email = "email0" + i + "@mail.com";
                }


                //Insertamos los datos en la tabla Clientes
                db.execSQL("INSERT INTO Clientes (nombre, telefono, email) " +
                        "VALUES ('" + nombre + "', '" + telefono +"', '" + email + "')");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
            //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
            //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
            //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
            //      a la nueva, por lo que este método debería ser más elaborado.

            //Se elimina la versión anterior de la tabla
            db.execSQL("DROP TABLE IF EXISTS Clientes");

            //Se crea la nueva versión de la tabla
            db.execSQL(sqlCreate);
        }
}

