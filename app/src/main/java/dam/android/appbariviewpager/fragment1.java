package dam.android.appbariviewpager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class fragment1 extends Fragment  {

    View rootView;


    public static Fragment newInstance() {
        fragment1 fragment = new fragment1();
        return fragment;
    }

    public fragment1() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment1, container, false);
        Button button=(Button)rootView.findViewById(R.id.btnnotas);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(rootView.getContext(), MainActivity_inicialbloc.class);
                startActivity(intent);

            }
        });

        return rootView;
    }


}


