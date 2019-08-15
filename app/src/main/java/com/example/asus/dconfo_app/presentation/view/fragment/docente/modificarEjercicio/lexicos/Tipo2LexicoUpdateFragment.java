package com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.lexicos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2LexicoUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2LexicoUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2LexicoUpdateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edt_name_ejer;
    private EditText edt_oracion;
    private EditText edt_cant_lexemas;
    private Button btn_crear_tipo2;

    private LinearLayout ll_muestra;

    private Button btn_C1;
    private Button btn_C2;
    private Button btn_C3;
    private Button btn_C4;
    private Button btn_C5;

    private TextToSpeech mTTS;
    private Button btn_escuchar_oracion;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    ProgressDialog progreso;

    private Integer cantPulsada;

    private String nameDocente;
    private int idDocente;
    private int idejercicio;

    private OnFragmentInteractionListener mListener;

    public static Tipo2LexicoUpdateFragment getInstance() {
        return new Tipo2LexicoUpdateFragment();
    }

    public Tipo2LexicoUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2LexicoUpdateFragment newInstance(String param1, String param2) {
        Tipo2LexicoUpdateFragment fragment = new Tipo2LexicoUpdateFragment();
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
        View view = inflater.inflate(R.layout.fragment_lex_tipo2_uddate, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_tipo2);

        cantPulsada = 0;

        nameDocente = getArguments().getString("namedocente");
        idDocente = getArguments().getInt("iddocente");
        idejercicio = getArguments().getInt("idejercicio");

        edt_oracion = (EditText) view.findViewById(R.id.edt_docente_update_lex_tipo2_oracion);
        edt_cant_lexemas = (EditText) view.findViewById(R.id.edt_docente_update_lex_tipo2_cant_lex_corr);
        edt_name_ejer = (EditText) view.findViewById(R.id.edt_docente_update_lex_tipo2_nombreE);

        btn_crear_tipo2 = (Button) view.findViewById(R.id.btn_docente_update_lex_tipo2_send_EjerT2);
        btn_crear_tipo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        ll_muestra = (LinearLayout) view.findViewById(R.id.ll_docente_update_lex_tipo2);

        btn_C1 = (Button) view.findViewById(R.id.btn_docente_tipo2_casilla1);
        btn_C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantPulsada++;
                System.out.println("cantidad pulsada C1: " + cantPulsada);
                btn_C1.setBackground(null);
                btn_C1.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });
        btn_C2 = (Button) view.findViewById(R.id.btn_docente_tipo2_casilla2);
        btn_C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantPulsada++;
                System.out.println("cantidad pulsada C2: " + cantPulsada);
                btn_C2.setBackground(null);
                btn_C2.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });
        btn_C3 = (Button) view.findViewById(R.id.btn_docente_tipo2_casilla3);
        btn_C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantPulsada++;
                System.out.println("cantidad pulsada C3: " + cantPulsada);
                btn_C3.setBackground(null);
                btn_C3.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });
        btn_C4 = (Button) view.findViewById(R.id.btn_docente_tipo2_casilla4);
        btn_C4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantPulsada++;
                System.out.println("cantidad pulsada C4: " + cantPulsada);
                btn_C4.setBackground(null);
                btn_C4.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });
        btn_C5 = (Button) view.findViewById(R.id.btn_docente_tipo2_casilla5);
        btn_C5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantPulsada++;
                System.out.println("cantidad pulsada C5: " + cantPulsada);
                btn_C5.setBackground(null);
                btn_C5.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });

        mSeekBarPitch = (SeekBar) view.findViewById(R.id.docente_seek_bar_pitch);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.docente_seek_bar_speed);

        btn_escuchar_oracion = (Button) view.findViewById(R.id.btn_docente_escuchar_tipo2);
        btn_escuchar_oracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        mTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //int result = mTTS.setLanguage(Locale.getDefault());
                    int result = mTTS.setLanguage(new Locale("spa", "ESP"));

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        // mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });


        return view;
    }

    private void speak() {
        String text = edt_oracion.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/23wsJSON_UpdateLexcio1.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_cant_lexemas.setText("");
                    edt_name_ejer.setText("");
                    // edt_oracion.setText("");
                    //ll_muestra.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito" + response.toString(), Toast.LENGTH_LONG).show();
                    System.out.println("error" + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String idejercicio = edt_CodigoEjercicio.getText().toString();
                //String idejercicio = "";
                String nameejercicio = edt_name_ejer.getText().toString();
                System.out.println("name ejercicio: " + nameejercicio);
                //String iddocente = String.valueOf(idDocente);
                String id_ejercicio = String.valueOf(idejercicio);
                //String idactividad = "2";
                //String idtipo = "4";
                String imagen = "";
                //System.out.println("dconfo imagen: " + imagen);
                String cantidadValida = edt_cant_lexemas.getText().toString();
                String oracion = edt_oracion.getText().toString();

                String de_galeria = "";
                String rutaImagen = "";

                Map<String, String> parametros = new HashMap<>();
                // parametros.put("idEjercicio", idejercicio);
                parametros.put("idejercicio", id_ejercicio);
                parametros.put("nameEjercicioG2", nameejercicio);
                parametros.put("imagen", imagen);
                parametros.put("cantidadValidadEjercicio", cantidadValida);
                parametros.put("oracion", oracion);
                parametros.put("de_galeria", de_galeria);
                parametros.put("rutaImagen", rutaImagen);
                // parametros.put("docente_iddocente", iddocente);

                // parametros.put("Tipo_Actividad_idActividad", idactividad);
                //parametros.put("Tipo_idTipo", idtipo);


                //parametros.put("letra_inicial_EjercicioG2", letraInicial);
                //parametros.put("letra_final_EjercicioG2", letraFinal);

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21
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
