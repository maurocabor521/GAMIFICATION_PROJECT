package com.example.asus.dconfo_app.presentation.view.fragment.administrador;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultEstudianteFragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PROTOCOL_CHARSET = "protol";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edt_codigo;
    private TextView txt_nombreEstudiante;
    private TextView txt_codigoEstudiante;
    private TextView txt_acudienteEstudiante;
    private Button btn_consultar;
    ProgressDialog progreso;
    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public static ConsultEstudianteFragment getInstance() {
        return new ConsultEstudianteFragment();
    }

    public ConsultEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultEstudianteFragment newInstance(String param1, String param2) {
        ConsultEstudianteFragment fragment = new ConsultEstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_consult_estudiante, container, false);
        edt_codigo = (EditText) view.findViewById(R.id.edt_numero_codigo_CU);
        txt_nombreEstudiante = (TextView) view.findViewById(R.id.txv_nombre_estudiante_CU);
        txt_codigoEstudiante = (TextView) view.findViewById(R.id.txv_codigo_estudiante_CU);
        txt_acudienteEstudiante = (TextView) view.findViewById(R.id.txv_acudiente_estudiante_CU);
        btn_consultar = (Button) view.findViewById(R.id.btn_consultarEstudiante_CU);
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        return view;
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();

        int cod=Integer.parseInt(edt_codigo.getText().toString());

        String url_lh=Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/" +
        String url = "http://"+url_lh+"/" +
                //"ejemploBDRemota/wsJSONConsultarUsuario.php?documento=" + campoDocumento.getText().toString();
                "proyecto_dconfo_v1/wsJSONConsultarEstudiante.php?documento="+cod;
        Toast.makeText(getContext(), "Mensaje: " + cod, Toast.LENGTH_SHORT).show();
        // String url = ip+"ejemploBDRemota/wsJSONConsultarUsuarioImagen.php?documento=" + campoDocumento.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se ha realizado la consulta de usuario" + error.toString(), Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
       // Log.i("tagconvertstr", "["+error+"]");
    }



    @Override
    public void onResponse(JSONObject response) {
        //lectura del Json
        progreso.hide();
        Toast.makeText(getContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        System.out.println("mes"+response.toString());
        Estudiante estudiante = new Estudiante();
        JSONArray json = response.optJSONArray("estudiante");
        JSONObject jsonObject = null;
        try {
            //objeto como tal en posicion 0
            jsonObject = json.getJSONObject(0);
            estudiante.setNameestudiante(jsonObject.optString("nameestudiante"));
            estudiante.setDniestudiante(jsonObject.optInt("dniestudiante"));
            estudiante.setIdestudiante(jsonObject.optInt("idestudiante"));
            estudiante.setAcudienteestudiante(jsonObject.optString("acudienteestudiante"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txt_nombreEstudiante.setText("Nombre: " + estudiante.getNameestudiante());
        txt_codigoEstudiante.setText("Id Est: " + estudiante.getIdestudiante());
        txt_acudienteEstudiante.setText("Acudiente: " + estudiante.getAcudienteestudiante());
      /*  if (miUsuario.getImagen()!=null) {
            campoImagen.setImageBitmap(miUsuario.getImagen());
        }else {
            campoImagen.setImageResource(R.drawable.imagen_no_disponible);
        }*/
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
