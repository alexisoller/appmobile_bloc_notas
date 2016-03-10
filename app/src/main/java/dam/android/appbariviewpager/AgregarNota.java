package dam.android.appbariviewpager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
public class AgregarNota extends AppCompatActivity {
    String type, getTitle;
    EditText TITLE, CONTENT;
    ImageView IMG;
    String imagen;

    private String MEDIA_DIRECTORY = "myPictureeeeeeeeeeeeeeeeeeeee";

    Button Add, camara;
    private static final int SALIR = Menu.FIRST;
    AdapatadorBD DB;
    private SharedPreferences MyPreferences;
    private final String MYPREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysettings();
        setContentView(R.layout.agregar_nota);
        setUI();
    }

    private void setUI() {

        Add = (Button) findViewById(R.id.button_Add);
        TITLE = (EditText) findViewById(R.id.editText_titulo);
        CONTENT = (EditText) findViewById(R.id.editText_Nota);
        IMG = (ImageView) findViewById(R.id.imageViewnotas);
        Bundle bundle = this.getIntent().getExtras();
        camara = (Button) findViewById(R.id.btncamara);
        type = bundle.getString("type");
        String content;
        getTitle = bundle.getString("title");
        imagen = bundle.getString("img");

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TITLE.getText().equals("")) {
                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    String mediaCapturePath = path + File.separator + "Camera" + File.separator + TITLE.getText().toString() + ".jpg";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mediaCapturePath)));
                    startActivityForResult(intent, 1);
                } else {
                    Mensaje("Ingrese un titulo");
                }
            }
        });

        content = bundle.getString("content");
        type = bundle.getString("type");
        if (type.equals("add")) {
            Add.setText("Add nota");
        } else if (type.equals("edit")) {
            TITLE.setText(getTitle);
            CONTENT.setText(content);
            if (!imagen.equals("n")) {
                decodeBitmap(imagen);
            } else {
                imagen = "n";
            }

            Add.setText("Update nota");
        }
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUpdateNotas();
            }
        });

        imagen = "n";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String mediaCapturePath = path + File.separator + "Camera" + File.separator + TITLE.getText().toString() + ".jpg";
            imagen = mediaCapturePath;
            decodeBitmap(mediaCapturePath);
        } else {
            imagen = "n";
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bm;
        bm = BitmapFactory.decodeFile(dir);
        if(!bm.equals("")) {
            IMG.setImageBitmap(Bitmap.createScaledBitmap(bm, 1000, 500, false));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas, menu);
        super.onCreateOptionsMenu(menu);
        menu.add(1, SALIR, 0, R.string.menu_volver);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_buscar:
                Toast.makeText(getApplicationContext(),
                        "Lupa", Toast.LENGTH_SHORT).show();
                return true;
            case SALIR:
                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager cookieManager = CookieManager.getInstance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.removeAllCookies(null);
                } else {
                    cookieManager.removeAllCookie();
                }
                Intent intent = new Intent(AgregarNota.this, MainActivity_inicialbloc.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //con FLAG_ACTIVITY_CLEAR_TASK eliminamos AgregarNota
                //y con FLAG_ACTIVITY_NEW_TASK iniciamos MainActivity.class
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addUpdateNotas() {

        DB = new AdapatadorBD(this);
        String title, content, msj;
        title = TITLE.getText().toString();
        content = CONTENT.getText().toString();
        if (type.equals("add")) {
            if (title.equals("")) {
                msj = "Ingrese un titulo";
                TITLE.requestFocus();
                Mensaje(msj);
            } else {
                if (content.equals("")) {
                    msj = "Ingrese la nota";
                    CONTENT.requestFocus();
                    Mensaje(msj);
                } else {
                    Cursor c = DB.getNote(title);
                    String gettitle = "";
                    if (c.moveToFirst()) {
                        do {
                            gettitle = c.getString(1);
                        } while (c.moveToNext());
                    }
                    if (gettitle.equals(title)) {
                        TITLE.requestFocus();
                        msj = "El titulo de la nota ya existe";
                        Mensaje(msj);
                    } else {
                        DB.addNote(title, content, imagen);
                        actividad(title, content, imagen);
                    }
                }
            }


        } else if (type.equals("edit")) {
            Add.setText("Update nota");
            if (title.equals("")) {
                msj = "Ingrese un titulo";
                TITLE.requestFocus();
                Mensaje(msj);

            } else if (content.equals("")) {
                msj = "Ingrese la nota";
                CONTENT.requestFocus();
                Mensaje(msj);
            } else {
                DB.updatenota(title, content, imagen, getTitle);
                actividad(title, content, imagen);
            }
        }

    }

    public void Mensaje(String msj) {
        Toast toast = Toast.makeText(this, msj, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

    }

    public void actividad(String title, String content, String Imagen) {

        Intent intent = new Intent(AgregarNota.this, VerNota.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        if (!imagen.equals("n")) {
            intent.putExtra("img", imagen);
        }else{
            intent.putExtra("img", "n");
        }
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("titulo", String.valueOf(TITLE.getText()));
        savedInstanceState.putString("contenido", String.valueOf(CONTENT.getText()));
        if (imagen != null) {
            savedInstanceState.putString("img", imagen);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TITLE.setText(savedInstanceState.getString("titulo"));
        CONTENT.setText(savedInstanceState.getString("contenido"));
        if (savedInstanceState.getString("img") != null) {
            imagen = savedInstanceState.getString("img");
            decodeBitmap(savedInstanceState.getString("img"));
        }
    }


}

