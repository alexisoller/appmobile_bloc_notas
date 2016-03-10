package dam.android.appbariviewpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

/**
 * Created by alexis on 22/02/2016.
 */
public class settings extends AppCompatActivity {

    private RadioButton rbnegrita, rbcursiva, rbnormal, rbnegro, rbblanco, rbdefecto;
    private Button btn;
    private SharedPreferences MyPreferences;
    private SharedPreferences.Editor editor;
    private final String MYPREFS = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mysettings();
        setContentView(R.layout.settings);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.letras, android.R.layout.simple_selectable_list_item);
        rbnegrita = (RadioButton) findViewById(R.id.rbnegrita);
        rbcursiva = (RadioButton) findViewById(R.id.rbcursiva);
        rbnormal = (RadioButton) findViewById(R.id.rbsubrayado);
        rbnegro = (RadioButton) findViewById(R.id.rbnegro);
        rbblanco = (RadioButton) findViewById(R.id.rbblanco);
        rbdefecto = (RadioButton) findViewById(R.id.rbdefecto);
    }
    private void mysettings() {
        MyPreferences = getSharedPreferences(MYPREFS, MODE_PRIVATE);
        if (MyPreferences.getBoolean("rbnegro", false)) {
            System.out.println("hola negro");
            super.setTheme(R.style.thnegro);
        } else if (MyPreferences.getBoolean("rbblanco", false)) {
            System.out.println("hola blanco");
            super.setTheme(R.style.thblanco);
        } else if (MyPreferences.getBoolean("rbdefecto", false)) {
            System.out.println("hola defecto");
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
    protected void onPause() {
        super.onPause();
        MyPreferences = getSharedPreferences(MYPREFS, MODE_PRIVATE);
        editor = MyPreferences.edit();
        editor.putBoolean("rbnegrita", rbnegrita.isChecked());
        editor.putBoolean("rbcursiva", rbcursiva.isChecked());
        editor.putBoolean("rbnormal", rbnormal.isChecked());
        editor.putBoolean("rbnegro", rbnegro.isChecked());
        editor.putBoolean("rbblanco", rbblanco.isChecked());
        editor.putBoolean("rbdefecto", rbdefecto.isChecked());
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyPreferences = getSharedPreferences(MYPREFS, MODE_PRIVATE);
        rbnegrita.setChecked((MyPreferences.getBoolean("rbnegrita", false)));
        rbcursiva.setChecked((MyPreferences.getBoolean("rbcursiva", false)));
        rbnormal.setChecked((MyPreferences.getBoolean("rbnormal", false)));
        rbnegro.setChecked((MyPreferences.getBoolean("rbnegro", false)));
        rbblanco.setChecked((MyPreferences.getBoolean("rbblanco", false)));
        rbdefecto.setChecked((MyPreferences.getBoolean("rbdefecto", false)));

    }
}
