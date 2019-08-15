package com.example.asus.dconfo_app.presentation.view.fragment.administrador;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewCursoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewCursoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCursoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edt_codigocurso;
    private EditText edt_nombrecurso;
    private EditText edt_codIntcurso;
    private EditText edt_periodocurso;
    private Button btn_Newcurso;
    ProgressDialog progreso;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    public static NewCursoFragment getInstance() {
        return new NewCursoFragment();
    }

    private OnFragmentInteractionListener mListener;

    public NewCursoFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewCursoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewCursoFragment newInstance(String param1, String param2) {
        NewCursoFragment fragment = new NewCursoFragment();
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

        View view = inflater.inflate(R.layout.fragment_new_curso, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.new_curso);
        edt_codigocurso = (EditText) view.findViewById(R.id.edt_codigo_curso);
        edt_nombrecurso = (EditText) view.findViewById(R.id.edt_nombre_Curso);
        edt_codIntcurso = (EditText) view.findViewById(R.id.edt_codigoInst_curso);
        edt_periodocurso = (EditText) view.findViewById(R.id.edt_periodo_curso);
        btn_Newcurso = (Button) view.findViewById(R.id.btn_new_curso);
        btn_Newcurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        return view;
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url_lh=Globals.url;
        String url =
                //"http://192.168.0.13/proyecto_dconfo/wsJSONCrearCurso.php?";
                "http://"+url_lh+"/proyecto_dconfo_v1/wsJSONCrearCurso.php?";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_codigocurso.setText("");
                    edt_codIntcurso.setText("");
                    edt_nombrecurso.setText("");
                    edt_periodocurso.setText("");
                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    Log.i("ERROR","RESPONSE"+response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String idcurso = edt_codigocurso.getText().toString();
                String idInstituto = edt_codIntcurso.getText().toString();
                String namecurso = edt_nombrecurso.getText().toString();
                String periodocurso = edt_periodocurso.getText().toString();

                Map<String, String> parametros = new HashMap<>();
                parametros.put("idcurso", idcurso);
                parametros.put("idInstituto", idInstituto);
                parametros.put("namecurso", namecurso);
                parametros.put("periodocurso", periodocurso);

                return parametros;
            }
        };
        //request.add(stringRequest);
        //p25 duplicar tiempo x defecto
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21

        //reemplazar espacios en blanco del nombre por %20
        // url = url.replace(" ", "%20");

        //hace el llamado a la url,no usa en p12
        /*jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);*/
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
