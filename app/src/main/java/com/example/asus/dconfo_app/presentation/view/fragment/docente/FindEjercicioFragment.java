package com.example.asus.dconfo_app.presentation.view.fragment.docente;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.presentation.view.adapter.SpinnerEjerciciosAdapter;
import com.example.asus.dconfo_app.repository.ImpEjercicio;
import com.example.asus.dconfo_app.repository.ImpListEjercicios;
import com.example.asus.dconfo_app.repository.ListaEjercicios;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FindEjercicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FindEjercicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindEjercicioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Spinner sp_lista_ejercicios;
    private EditText edt_lista_ejercicios;
    private Button btn_find;
    private ProgressBar pb_find;
    List<String> lstEjercicios_frag = new ArrayList<>();
    List<String> lstNombreEjercicios;
    ImpEjercicio impEjercicio;
    ImpListEjercicios impListEjercicios;
    SpinnerEjerciciosAdapter spinnerEjerciciosAdapter;
    Integer idDocente;
    View view;
    MediaPlayer mp;
    List<String> lst_prueba = new ArrayList<>();

     /*   lst_prueba.add("1 22 5");
        lst_prueba.add("2 ddd");
        lst_prueba.add("3");
        lst_prueba.add("4");
        lst_prueba.add("5");
        lst_prueba.add("3");
        lst_prueba.add("1");
        lst_prueba.add("2");
        lst_prueba.add("3");
        lst_prueba.add("10");
        lst_prueba.add("2");
        lst_prueba.add("3");
        lst_prueba.add("1");
        lst_prueba.add("2");
        lst_prueba.add("15");
        lst_prueba.add("1");
        lst_prueba.add("2");
        lst_prueba.add("3");
        lst_prueba.add("1");
        lst_prueba.add("20");
        lst_prueba.add("3");
        lst_prueba.add("1");
        lst_prueba.add("2");
        lst_prueba.add("24");*/

    ListaEjercicios listaEjercicios;

    private OnFragmentInteractionListener mListener;

    public FindEjercicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindEjercicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FindEjercicioFragment newInstance(String param1, String param2) {
        FindEjercicioFragment fragment = new FindEjercicioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_find_ejercicio, container, false);
        mp = MediaPlayer.create(getContext(), R.raw.sound_button);

        idDocente = getArguments().getInt("iddocente");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Docente id: " + idDocente);
        impListEjercicios = new ImpListEjercicios(getContext(), view, idDocente);


        pb_find = (ProgressBar) view.findViewById(R.id.pb_docente_FE);
        edt_lista_ejercicios = (EditText) view.findViewById(R.id.edt_docente_FE);

        listaEjercicios = new ListaEjercicios(getContext());
        listaEjercicios.execute();
        Task1 task1 = new Task1();
        task1.execute();
        //System.out.println("la lista LE: " + listaEjercicios.getListaStringEjercicios().size());

        // lstEjercicios_frag = listaEjercicios.getListaStringEjercicios();
        System.out.println("la lista lst: " + lstEjercicios_frag.size());

        sp_lista_ejercicios = (Spinner) view.findViewById(R.id.sp_docente_FE);
        //impListEjercicios = new ImpListEjercicios(getContext(), view, idDocente);
       /* if (lstEjercicios_frag.size() == 0) {
            //cargarLista();
            System.out.println("la lista SIZE: " + lstEjercicios_frag.size());
            // cargarSpinner1();

        } else {
            //cargarSpinner1();
            System.out.println("la lista IF: " + lstEjercicios_frag.size());
            cargarSpinner1();
        }*/
       /* do {
            System.out.println("do while : " + lstEjercicios_frag.size());
            cargarSpinner1();
        }
        while (lstEjercicios_frag.size() > 0);*/

        if (lstEjercicios_frag.size() != 0) {
            cargarSpinner1();
        } else {

        }


        btn_find = (Button) view.findViewById(R.id.btn_docente_FE);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  lstEjercicios_frag = impListEjercicios.getListaEjercicios();
                cargarSpinner();
                Log.i("Mis ejercicios:", lstEjercicios_frag.toString());*/
                //carga();
                mp.start();

                //new Task1().execute();

            }
        });
        return view;
    }

    class Task1 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_find.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            lstEjercicios_frag = listaEjercicios.getListaStringEjercicios();
            dormirLista();
            System.out.println("Cargando...");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



            if (lstEjercicios_frag.size() == 0) {
                pb_find.setVisibility(View.VISIBLE);
                //dormirLista();
            } else {

                cargarSpinner1();
                pb_find.setVisibility(View.INVISIBLE);

            }


        }
    }

    public void dormirLista() {
        // lstEjercicios_frag = impListEjercicios.getListaEjercicios();
        try {
            Thread.sleep(100);
            System.out.println("Dormir...");
        } catch (InterruptedException e) {

        }
        // System.out.println("lista llenando");
        // cargarSpinner();
    }


    public void carga() {
        lstEjercicios_frag = impListEjercicios.getListaEjercicios();
        cargarSpinner();
        Log.i("Mis ejercicios:", lstEjercicios_frag.toString());
    }

    public void cargarSpinner() {
        spinnerEjerciciosAdapter = new SpinnerEjerciciosAdapter(getContext(), lstEjercicios_frag, getView(), sp_lista_ejercicios);
        //spinnerEjerciciosAdapter.set
        sp_lista_ejercicios = spinnerEjerciciosAdapter.getSpinner();


    }

    public void cargarSpinner1() {
        System.out.println("cargarspinner1 : " + lstEjercicios_frag.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstEjercicios_frag);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lst_prueba);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        ;
        sp_lista_ejercicios.setAdapter(adapter);
        sp_lista_ejercicios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    edt_lista_ejercicios.setText(lstEjercicios_frag.get(position));
                    System.out.println("position sel: " + lstEjercicios_frag.get(position));
                    Toast.makeText(getContext(), "position sel: " + lstEjercicios_frag.get(position), Toast.LENGTH_LONG).show();

                   /* edt_lista_ejercicios.setText(lst_prueba.get(position));
                    System.out.println("position sel: " + lst_prueba.get(position));
                    Toast.makeText(getContext(), "position sel: " + lst_prueba.get(position), Toast.LENGTH_LONG).show();*/
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void cargarLista1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lstEjercicios_frag = impListEjercicios.getListaEjercicios();
                dormirLista();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //cargarSpinner();
                        lstEjercicios_frag = impListEjercicios.getListaEjercicios();
                        Toast.makeText(getContext(), "cargando", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }


    @Override
    public void onStart() {
        super.onStart();
        //cargarSpinner1();
    }

    {
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //carga();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

   /* @Override
    public void success(Object result) {
        lstEjercicios_frag = impListEjercicios.getListaEjercicios();
    }

    @Override
    public void error(Exception error) {

    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
