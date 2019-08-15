package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.TTSManager;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.docente.AsignarEstudianteDeberActivity;
import com.example.asus.dconfo_app.presentation.view.activity.estudiante.HomeEstudianteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo1EstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1EstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1EstudianteFragment extends Fragment
        implements Response.Listener<JSONObject>,
        Response.ErrorListener, TextToSpeech.OnInitListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idEjercicio = 0;

    private ImageView iv_imagen;
    private Button btn_bell;
    private Button mButtonSpeak;
    private Button btn_b1;
    private Button btn_b2;
    private Button btn_b3;
    private Button btn_b4;
    private Button btn_b5;
    private Button btn_responder;

    private TextToSpeech mTTS;
    private TextView txt_miRespuesta;


    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;

    private String textOracion;

    String urlImagen;

    String usuario;//250619

    ArrayList<EjercicioG1> listaEjerciciosg1;
    ArrayList<EjercicioG2> listaEjerciciosg2;
    Context context;
    View view;
    Integer idejercicio;
    List<String> listaNombreEjerciciog1;
    List<Integer> listaidEjerciciog1;
    EjercicioG1 ejercicioG1;
    EjercicioG2 ejercicioG2;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;

    int campanada;
    String cantLexemas;
    ProgressDialog progreso;

    int nota;
    int intento = 3;
    String nameestudiante;
    int idestudiante;
    TextView txt_intento;
    LinearLayout ll_intento;
    int iddeber;
    MediaPlayer mediaPlayer;
    MediaPlayer mp1;
    MediaPlayer mp2;

    pl.droidsonroids.gif.GifImageButton gifImageButton;

    private static final int TTS_ENGINE_REQUEST = 101;

    //TTSManager mTTS=null;


    private OnFragmentInteractionListener mListener;

    public Tipo1EstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1EstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo1EstudianteFragment newInstance(String param1, String param2) {
        Tipo1EstudianteFragment fragment = new Tipo1EstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo1_estudiante, container, false);


        idEjercicio = getArguments().getInt("idejercicio");

        usuario = getArguments().getString("usuario");
        nameestudiante = getArguments().getString("nameEstudiante");
        idestudiante = getArguments().getInt("idEstudiante");
        iddeber = getArguments().getInt("idesthasdeber");

        ll_intento = view.findViewById(R.id.ll_est_lex1_intent);
        ll_intento.setVisibility(View.VISIBLE);
        txt_intento = view.findViewById(R.id.txt_est_lex1_intento);

        //gifImageButton = view.findViewById(R.id.pl_gif_lex1);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.ping4);
        mp1 = MediaPlayer.create(getContext(), R.raw.exito);
        mp2 = MediaPlayer.create(getContext(), R.raw.error);


        System.out.println("************************idEjercicio: " + idEjercicio);
        System.out.println("************************idEstudiante: " + idestudiante);
        System.out.println("************************usuario: " + usuario);
        System.out.println("************************iddeber: " + iddeber);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("id Ejercicio: " + idEjercicio);

        iv_imagen = (ImageView) view.findViewById(R.id.iv_estudiante_tipo1);
        mSeekBarPitch = (SeekBar) view.findViewById(R.id.seek_bar_pitch_estudiante);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.seek_bar_speed_estudiante);

        mButtonSpeak = (Button) view.findViewById(R.id.btn_estudiante_tipo1_textToS);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "boton", Toast.LENGTH_SHORT).show();
                //speak1(getView());
                speak();
            }
        });

        //mTTS = new TextToSpeech(getActivity(), this);

        //mTTS=new TTSManager();
        //mTTS.init(getActivity());

       mTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //int result = mTTS.setLanguage(Locale.getDefault());
                    int result = mTTS.setLanguage(new Locale("spa", "ESP"));

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                        Intent installIntent = new Intent();
                        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                        startActivity(installIntent);
                    } else {
                        // mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });



        iv_imagen = (ImageView) view.findViewById(R.id.iv_estudiante_tipo1);

        campanada = 0;

        btn_bell = (Button) view.findViewById(R.id.btn_estudiante_bell);
        btn_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campanada++;
                mediaPlayer.start();
                if (campanada == 1) {
                    System.out.println("campanada :" + campanada);
                    btn_b1.setBackground(getResources().getDrawable(R.drawable.selec));
                }
                if (campanada == 2) {
                    System.out.println("campanada :" + campanada);
                    btn_b2.setBackground(getResources().getDrawable(R.drawable.selec));
                }
                if (campanada == 3) {
                    System.out.println("campanada :" + campanada);
                    btn_b3.setBackground(getResources().getDrawable(R.drawable.selec));
                }
                if (campanada == 4) {
                    System.out.println("campanada :" + campanada);
                    btn_b4.setBackground(getResources().getDrawable(R.drawable.selec));
                }
            }
        });
        btn_b1 = (Button) view.findViewById(R.id.btn_estudiante_b1);
        btn_b2 = (Button) view.findViewById(R.id.btn_estudiante_b2);
        btn_b3 = (Button) view.findViewById(R.id.btn_estudiante_b3);
        btn_b4 = (Button) view.findViewById(R.id.btn_estudiante_b4);
        btn_b5 = (Button) view.findViewById(R.id.btn_estudiante_b5);


        txt_miRespuesta = (TextView) view.findViewById(R.id.txt_estudiante_resultado);
        btn_responder = (Button) view.findViewById(R.id.btn_estudiante_Responde);
        btn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campanada == Integer.parseInt(cantLexemas)) {
                    txt_miRespuesta.setText("CORRECTO");
                    mp1.start();
                    nota = 5;
                    cargarWebService_1();
                    enviarNota();
                    mostrarInforme();
                } else {
                    txt_miRespuesta.setText("INCORRECTO");
                    mp2.start();
                    mostrarError();
                    intento--;
                    txt_intento.setText(String.valueOf(intento));
                    if (intento == 0) {
                        nota = 1;

                        cargarWebService_1();
                        enviarNota();
                    }
                }
            }
        });

        cargarWebService();

        return view;
    }

    public boolean textToSpeechIsInitialized = false;  // <--- add this line

    private void speak1(View view) {
        Intent intent = new Intent();
        intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(intent, TTS_ENGINE_REQUEST);
    }

    private void speak() {
        // String text = edt_OrtacionEjercicio.getText().toString();
        //String text = edt_OrtacionEjercicio.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        int speechStatus = mTTS.speak(textOracion, TextToSpeech.QUEUE_FLUSH, null);
        if (speechStatus == TextToSpeech.ERROR) {
            Toast.makeText(getActivity(), "Error mientras habla", Toast.LENGTH_SHORT).show();
        }

        // mTTS.initQueue(textOracion);

        System.out.println("oración: " + textOracion);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_ENGINE_REQUEST && resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
            mTTS = new TextToSpeech(getActivity(), this);
        } else {
            Intent installintent = new Intent();
            installintent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installintent);
        }
    }

    // setup TTS
    public void onInit(int initStatus) {

        if (initStatus == TextToSpeech.SUCCESS) {

            int result = mTTS.setLanguage(new Locale("spa", "ESP"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getActivity(), "This Language is not supported", Toast.LENGTH_SHORT).show();
                Log.e("TTS", "This Language is not supported");
            } else {
                Toast.makeText(getActivity(), "is good", Toast.LENGTH_SHORT).show();
               // speak();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
            Toast.makeText(getActivity(), "Initilization Failed!", Toast.LENGTH_SHORT).show();
        }
        System.out.println("initStatus////////////////" + initStatus);
      /*  if (initStatus == TextToSpeech.SUCCESS) {
            //Setting speech Language
            mTTS.setLanguage(new Locale("spa", "ESP"));
            mTTS.setPitch(1);

        }*/


        //mTTS=new TTSManager();
        //mTTS.init(getActivity());
    }

    // setup TTS
    public void onInit_original(int initStatus) {

        if (initStatus == TextToSpeech.SUCCESS) {

            textToSpeechIsInitialized = true;  // <--- add this line

            int result = mTTS.setLanguage(new Locale("spa", "ESP"));

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // speakOut("badr","dfd");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

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
    // ----------------------------------------------------------------------------------------------


    public void cargarWebService() {

        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/9wsJSONConsultarEjercicioEstudiante.php?idEjercicioG2=" + idEjercicio;

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(jsonObjectRequest);//p21

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        // Toast.makeText(getContext(), "No se puede conectar exitosamente" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("**********ERROR:  ", error.toString());

    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        // public ArrayList<Curso> onResponse(JSONObject response) {
        //lectura del Json

        //Toast.makeText(getContext(), "onResponse: " + response.toString(), Toast.LENGTH_SHORT).show();
        ejercicioG2 = null;
        JSONArray json = response.optJSONArray("ejerciciog2");

        ArrayList<EjercicioG2> listaDEjerciciosg2 = new ArrayList<>();
        listaDEjerciciosg2 = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                ejercicioG2 = new EjercicioG2();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                ejercicioG2.setRutaImagen(jsonObject.optString("rutaImagen_Ejercicio"));
                ejercicioG2.setCantidadLexemas(jsonObject.optString("cantidadValidadEjercicio"));
                ejercicioG2.setOracion(jsonObject.optString("oracionEjercicio"));
                listaDEjerciciosg2.add(ejercicioG2);

            }
            textOracion = ejercicioG2.getOracion();
            cantLexemas = ejercicioG2.getCantidadLexemas();

            String url_lh = Globals.url;

            final String rutaImagen = ejercicioG2.getRutaImagen();

            urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
            urlImagen = urlImagen.replace(" ", "%20");

            ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    //holder.imagen.setImageBitmap(response);
                    iv_imagen.setBackground(null);
                    iv_imagen.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    System.out.println("ruta imagen: " + urlImagen);
                }
            });
            VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

        }
    }//onResponse


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
