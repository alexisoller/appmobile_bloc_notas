package dam.android.appbariviewpager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vesprada on 16/02/16.
 */
public class VerNota extends AppCompatActivity {
    String title, content;
    TextView TITLE, CONTENT;
    ImageView IMG;
    String imgbit;
    private static final int EDITAR = Menu.FIRST;
    private static final int BORRAR = Menu.FIRST + 1;
    private static final int SALIR = Menu.FIRST + 2;
    AdapatadorBD DB;
    private SharedPreferences MyPreferences;
    private final String MYPREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysettings();
        setContentView(R.layout.ver_nota);
        setUI();
    }

    private void setUI() {
        Bundle bundle = this.getIntent().getExtras();
        title = bundle.getString("title");
        content = bundle.getString("content");
        imgbit=bundle.getString("img");



        TITLE = (TextView) findViewById(R.id.textView_Titulo);
        CONTENT = (TextView) findViewById(R.id.textView_Content);
        IMG=(ImageView)findViewById(R.id.imageViewvernota);
        TITLE.setText(title);
        CONTENT.setText(content);
        if(!imgbit.equals("n")){
            decodeBitmap(imgbit);
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bm;
        bm = BitmapFactory.decodeFile(dir);
        IMG.setImageBitmap(Bitmap.createScaledBitmap(bm, 1000, 500, false));
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
        menu.add(1, EDITAR, 0, R.string.menu_editar);
        menu.add(2, BORRAR, 0, R.string.menu_eliminar);
        menu.add(3, SALIR, 0, R.string.menu_volver);

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
            case EDITAR:
                actividad("edit");
                return true;
            case BORRAR:
                alert();
                return true;
            case SALIR:
                actividad("delete");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void actividad(String f) {
        if (f.equals("edit")) {
            String type = "edit";
            Intent intent = new Intent(VerNota.this, AgregarNota.class);

            intent.putExtra("type", type);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            startActivity(intent);
        } else if (f.equals("delete")) {

            CookieSyncManager.createInstance(getApplicationContext());
            CookieManager cookieManager = CookieManager.getInstance();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeAllCookies(null);
            } else {
                cookieManager.removeAllCookie();
            }
            Intent intent = new Intent(VerNota.this, MainActivity_inicialbloc.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            //con FLAG_ACTIVITY_CLEAR_TASK eliminamos AgregarNota
            //y con FLAG_ACTIVITY_NEW_TASK iniciamos MainActivity.class
            startActivity(intent);

        }
    }

    private void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje de confirmacion");

        builder.setMessage("¿Desea eliminar la nota?");
        builder.setPositiveButton("Delete Note",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete();

                    }
                });

        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        builder.show();

    }

    private void delete() {

        DB = new AdapatadorBD(this);
        DB.deletenota(title);
        actividad("delete");
    }

}
