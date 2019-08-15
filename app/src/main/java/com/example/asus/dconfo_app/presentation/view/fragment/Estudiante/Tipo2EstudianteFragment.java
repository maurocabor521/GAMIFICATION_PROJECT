package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
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
import com.example.asus.dconfo_app.presentation.view.activity.estudiante.HomeEstudianteActivity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2EstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2EstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2EstudianteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextToSpeech mTTS;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    private Button mButtonSpeak;
    private Button btn_b1;
    private Button btn_b2;
    private Button btn_b3;
    private Button btn_b4;
    private Button btn_b5;
    private Button btn_responder;

    private int idEjercicio;
    private int cantLexemas;
    private String cantLexemas_bundle;
    private String oracion;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;

    String usuario;//250619
    ProgressDialog progreso;

    int nota;
    int intento = 3;
    String nameestudiante;
    int idestudiante;
    TextView txt_intento;
    TextView txt_resultado;
    LinearLayout ll_intento;
    int iddeber;
    MediaPlayer mediaPlayer;
    MediaPlayer mp1;
    MediaPlayer mp2;

    private OnFragmentInteractionListener mListener;

    public Tipo2EstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2EstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2EstudianteFragment newInstance(String param1, String param2) {
        Tipo2EstudianteFragment fragment = new Tipo2EstudianteFragment();
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
        View view;
        view = inflater.inflate(R.layout.fragment_tipo2_estudiante, container, false);

        idEjercicio = getArguments().getInt("idejercicio");
        usuario = getArguments().getString("usuario");
        cantLexemas_bundle = getArguments().getString("cantLexemas");
        System.out.println("cantLexemas_bundle: " + cantLexemas_bundle);
        oracion = getArguments().getString("oracion");
        //*******************************************************************************************
        nameestudiante = getArguments().getString("nameEstudiante");
        idestudiante = getArguments().getInt("idEstudiante");
        iddeber = getArguments().getInt("idesthasdeber");

        ll_intento = view.findViewById(R.id.ll_est_lex2_intent);
        ll_intento.setVisibility(View.VISIBLE);
        txt_intento = view.findViewById(R.id.txt_est_lex2_intento);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.ping4);
        mp1 = MediaPlayer.create(getContext(), R.raw.exito);
        mp2 = MediaPlayer.create(getContext(), R.raw.error);
        txt_resultado = view.findViewById(R.id.txt_estudiante_resultado_lex2);
        //*******************************************************************************************

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("id Ejercicio: " + idEjercicio);

        mSeekBarPitch = (SeekBar) view.findViewById(R.id.estudiante_seek_bar_pitch);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.estudiante_seek_bar_speed);

        mButtonSpeak = (Button) view.findViewById(R.id.btn_estudiante_escuchar_tipo2);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        btn_b1 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla1);
        btn_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btn_b1.setBackground(null);
                btn_b1.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c1: " + cantLexemas);
            }
        });

        btn_b2 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla2);
        btn_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btn_b2.setBackground(null);
                btn_b2.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c2: " + cantLexemas);
            }
        });

        btn_b3 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla3);
        btn_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btn_b3.setBackground(null);
                btn_b3.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c3: " + cantLexemas);
            }
        });

        btn_b4 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla4);
        btn_b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btn_b4.setBackground(null);
                btn_b4.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c4: " + cantLexemas);
            }
        });

        btn_b5 = (Button) view.findViewById(R.id.btn_estudiante_tipo2_casilla5);
        btn_b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btn_b5.setBackground(null);
                btn_b5.setBackground(getResources().getDrawable(R.drawable.selec));
                cantLexemas++;
                System.out.println("cant lex c5: " + cantLexemas);
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

        btn_responder = (Button) view.findViewById(R.id.btn_estudiante_tipo2_enviar_respuesta);
        btn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantLexemas == Integer.parseInt(cantLexemas_bundle)) {
                    //Toast.makeText(getContext(), "CORRECTO", Toast.LENGTH_LONG).show();
                    txt_resultado.setText("CORRECTO");
                    mp1.start();
                    nota = 5;
                    mostrarInforme();
                    cargarWebService_1();
                    enviarNota();
                } else {
                    //Toast.makeText(getContext(), "INCORRECTO", Toast.LENGTH_LONG).show();
                    intento--;
                    cantLexemas = 0;
                    txt_resultado.setText("INCORRECTO");
                    txt_intento.setText(String.valueOf(intento));
                    mp2.start();
                    mostrarError();
                    if (intento == 0) {
                        nota = 1;
                        cargarWebService_1();
                        enviarNota();
                    }
                    btn_b1.setBackground(getResources().getDrawable(R.drawable.ic_square_30dp));
                    btn_b2.setBackground(getResources().getDrawable(R.drawable.ic_square_30dp));
                    btn_b3.setBackground(getResources().getDrawable(R.drawable.ic_square_30dp));
                    btn_b4.setBackground(getResources().getDrawable(R.drawable.ic_square_30dp));
                    btn_b5.setBackground(getResources().getDrawable(R.drawable.ic_square_30dp));
                }
            }
        });

        return view;
    }

    // ----------------------------------------------------------------------------------------------

    private void cargarWebService_1() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/28wsJSONAsignarCalificacionDeberEstudiante.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    Toast.makeText(getContext(), "Se ha cargado la nota con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    System.out.println("el error: " + response.toString());
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
                String idesthasdeber = String.valueOf(iddeber);
                String notadeber = String.valueOf(nota);
                //String idejercicio = "";


                Map<String, String> parametros = new HashMap<>();
                parametros.put("idEstudiantehasDeber", idesthasdeber);
                parametros.put("calificacionestudiante", notadeber);
                System.out.println("Los parametros: " + parametros.toString());

                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21


    }

    // ----------------------------------------------------------------------------------------------

    private void mostrarInforme() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Muy Bien !!!");
        alertDialog.setMessage("Acertaste!!! ");
        Drawable drawable = ll_intento.getResources().getDrawable(R.drawable.premio);
        alertDialog.setIcon(drawable);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        crearTranstition();
                    }
                });
        alertDialog.show();
    }

    private void mostrarError() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Fallaste!!!");
        if (intento != 0) {
            alertDialog.setMessage("Intentalo de nuevo ");
        } else {
            alertDialog.setMessage("Lo siento !!! ");
        }

        Drawable drawable = ll_intento.getResources().getDrawable(R.drawable.rana_gif);

        alertDialog.setIcon(drawable);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (intento == 0) {
                            crearTranstition();
                        }
                    }
                });
        alertDialog.show();
    }

    public void crearTranstition() {
        Bundle bundle = new Bundle();
        bundle.putInt("idEstudiante", idestudiante);
        bundle.putString("nameEstudiante", nameestudiante);

        System.out.println("idEstudiante: " + idestudiante);
        System.out.println("nameEstudiante: " + nameestudiante);

        CasaHomeEstudianteFragment homeEstudianteFragment = new CasaHomeEstudianteFragment();
        homeEstudianteFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.container_HomeEstudiante, homeEstudianteFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    private void enviarNota() {

        String dconfo = "dconfo";
        String dconfo_mensaje = "Tiene un nuevo mensaje";

        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int icono = R.drawable.home;
        Intent i = new Intent(getActivity(), HomeEstudianteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, i, 0);

        mBuilder = new NotificationCompat.Builder(getContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Ejercicio Realizado")
                .setContentText("Tu nota es:" + nota)
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true);


        mNotifyMgr.notify(1, mBuilder.build());
    }
    //************************************************************************************************

    private void speak() {
        // String text = edt_OrtacionEjercicio.getText().toString();
        //String text = edt_OrtacionEjercicio.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(oracion, TextToSpeech.QUEUE_FLUSH, null);
        System.out.println("oración: " + oracion);
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
