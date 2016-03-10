package dam.android.appbariviewpager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_inicialbloc extends AppCompatActivity {
    private static final int ADD = Menu.FIRST;
    private static final int DELETE = Menu.FIRST + 1;
    private static final int EXITS = Menu.FIRST + 2;
    private SharedPreferences MyPreferences;
    private final String MYPREFS = "MyPrefs";
    private ListView lista;
    private TextView textLista;
    private AdapatadorBD DB;

    List<String> item = null;
    String getTitle;
    String getimg = "n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysettings();
        setContentView(R.layout.activity_main_blocnotas);
        setUI();
        shownotes();
    }

    private void setUI() {

        textLista = (TextView) findViewById(R.id.textView_Lista);
        lista = (ListView) findViewById(R.id.listView_Lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getTitle = (String) lista.getItemAtPosition(position);
                alert("list");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas_inicial, menu);

        menu.add(1, ADD, 0, R.string.menu_crear);
        menu.add(2, DELETE, 0, R.string.menu_borrar_todas);
        menu.add(3, EXITS, 0, R.string.menu_salir);
        super.onCreateOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_buscar:
                actividad("add");
                return true;
            case ADD:
                actividad("add");
                return true;
            case DELETE:
                alert("deletes");
                return true;
            case EXITS:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void mysettings() {
        MyPreferences = getSharedPreferences(MYPREFS, MODE_PRIVATE);


        if (MyPreferences.getBoolean("rbnegro", false)) {
            super.setTheme(R.style.thnegro);
        } else if (MyPreferences.getBoolean("rbblanco", false)) {
            super.setTheme(R.style.thblanco);
        } else if (MyPreferences.getBoolean("rbdefecto", false)) {
            super.setTheme(R.style.AppTheme);

        }
        if (MyPreferences.getBoolean("rbnegrita", false)) {
            super.setTheme(R.style.negrita);
        } else if (MyPreferences.getBoolean("rbcursiva", false)) {
            super.setTheme(R.style.cursiva);
        } else if (MyPreferences.getBoolean("rbnormal", false)) {
            super.setTheme(R.style.normal);
        }

    }


    public void actividad(String act) {
        String type = "", content = "";
        if (act.equals("add")) {
            type = "add";
            Intent intent = new Intent(MainActivity_inicialbloc.this, AgregarNota.class);
            intent.putExtra("type", type);
            startActivity(intent);
        } else if (act.equals("edit")) {
            type = "edit";
            content = getNote();
            Intent intent = new Intent(MainActivity_inicialbloc.this, AgregarNota.class);
            intent.putExtra("type", type);
            intent.putExtra("title", getTitle);
            intent.putExtra("img", getimg);
            intent.putExtra("content", content);

            startActivity(intent);
        } else if (act.equals("see")) {
            content = getNote();
            Intent intent = new Intent(MainActivity_inicialbloc.this, VerNota.class);
            intent.putExtra("title", getTitle);
            intent.putExtra("content", content);
            intent.putExtra("img", getimg);
            startActivity(intent);

        }
    }


    private void shownotes() {
        DB = new AdapatadorBD(this);
        Cursor c = DB.getNotes();
        item = new ArrayList<String>();
        String title = "";
        if (c.moveToFirst() == false) {
            textLista.setText("No hay notas");
        } else {
            do {
                title = c.getString(1);
                item.add(title);

            } while (c.moveToNext());
        }
        if (getResources().getConfiguration().orientation == 1) {
            lista.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, item));
        } else {

            lista.setAdapter(new ArrayAdapter<String>(this, R.layout.fila_lista, R.id.nombre_fila_lista, item));
        }


    }

    public String getNote() {
        String type = "", content = "";
        DB = new AdapatadorBD(this);
        Cursor c = DB.getNote(getTitle);
        if (c.moveToFirst()) {
            textLista.setText("No hay notas");
            do {
                content = c.getString(2);

                if (!c.getString(3).equals("n")) {
                    getimg = c.getString(3);
                } else {
                    getimg = "n";
                }


            } while (c.moveToNext());
        }
        return content;

    }

    private void alert(String f) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (f.equals("list")) {

            builder.setTitle("Titulo de la nota:" + getTitle);
            builder.setMessage("¿Que accion desea realizar?");
            builder.setPositiveButton("Ver nota",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            actividad("see");
                        }
                    });
            builder.setNegativeButton("Borrar nota",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            delete("delete");
                            Intent intent = getIntent();
                            startActivity(intent);

                        }
                    });
            builder.setNeutralButton("Editar nota", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    actividad("edit");
                }
            });
        } else {

            if (f.equals("deletes")) {
                builder.setTitle("Mensaje de confirmacion");
                builder.setMessage("¿Que accion desea realizar?");

                builder.setPositiveButton("Delete Notes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                delete("deletes");
                                Intent intent = getIntent();
                                startActivity(intent);

                            }
                        });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
            }
        }
        builder.show();
    }


    private void delete(String f) {
        DB = new AdapatadorBD(this);
        if (f.equals("delete")) {
            DB.deletenota(getTitle);
        } else if (f.equals("deletes")) {
            DB.deletenotes();
        }

    }

}
