package net.sgoliver.android.toolbar3;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import src.fichajes.Fichaje;


public class Fragment2 extends Fragment {
    private ListView lstListado;
    private TextView updateText;

    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();
        return fragment;
    }

    public Fragment2() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_2,container,false);
        lstListado=(ListView)view.findViewById(R.id.listView2);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void updateList(List<Fichaje> fichajesProfe){

        /*System.out.println("updateTextField Origen " + motivo + " Destino " + destino+" Dia:"+dayOfMonth+" Mes:"+month+" Año:"+year);
        datosList.add("Origen:"+origen+" Destino:"+destino+" Motivo:"+motivo+" Dia:"+dayOfMonth+" Mes:"+month+" Año:"+year
                +" Hora de llegada: "+horaS+":"+minutoS+" Hora de salida: "+horaL+":"+minutoL);*/
        ArrayAdapter arrayadap=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, fichajesProfe);
        lstListado.setAdapter(arrayadap);

    }
}
