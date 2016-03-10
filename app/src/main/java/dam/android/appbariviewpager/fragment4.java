package dam.android.appbariviewpager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by vesprada on 20/01/16.
 */
public class fragment4 extends Fragment{
    View rootView;
    String[] personalvideos = new String[] {"personalvideo 1","personalvideo 2","personalvideo 3"};

    public static Fragment newInstance() {
        fragment4 fragment = new fragment4();
        return fragment;
    }

    public fragment4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment4, container, false);
        Button btn=(Button)rootView.findViewById(R.id.buttonjasper);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inet=new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.223.1:8085/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FCherry_Table_Based_AlexisOller&standAlone=true"));
                startActivity(inet);

            }
        });

        return rootView;
    }




}
