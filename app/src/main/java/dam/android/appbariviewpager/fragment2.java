package dam.android.appbariviewpager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RatingBar;

/**
 * Created by vesprada on 20/01/16.
 */
public class fragment2 extends Fragment{
    private View rootView;


    public static Fragment newInstance() {
        fragment2 fragment = new fragment2();
        return fragment;
    }

    public fragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment2, container, false);

        AutoCompleteTextView actv;
        actv = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextView);
        String[] countries = getResources().getStringArray(R.array.ciclos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,countries);
        actv.setAdapter(adapter);
        return rootView;
    }



}
