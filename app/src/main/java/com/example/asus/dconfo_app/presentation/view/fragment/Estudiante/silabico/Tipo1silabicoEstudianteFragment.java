package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo1silabicoEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1silabicoEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1silabicoEstudianteFragment extends Fragment  implements Response.Listener<JSONObject>,
        Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int idEjercicio = 0;

    private CircleImageView iv_imagen;
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
    private String usuario;

    String urlImagen;

    ArrayList<EjercicioG1> listaEjerciciosg1;
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

    private OnFragmentInteractionListener mListener;

    public Tipo1silabicoEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1silabicoEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo1silabicoEstudianteFragment newInstance(String param1, String param2) {
        Tipo1silabicoEstudianteFragment fragment = new Tipo1silabicoEstudianteFragment();
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
        View view=inflater.inflate(R.layout.fragment_tipo1silabico_estudiante, container, false);

        idEjercicio = getArguments().getInt("idejercicio");
        usuario = getArguments().getString("usuario");
        System.out.println("SIL TIPO1 idEjercicio  :" + idEjercicio);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("id Ejercicio: " + idEjercicio);

        iv_imagen = (CircleImageView) view.findViewById(R.id.iv_estudiante_silabico_t1);
        mSeekBarPitch = (SeekBar) view.findViewById(R.id.seek_bar_pitch_silabico_estudiante);
        mSeekBarSpeed = (SeekBar) view.findViewById(R.id.seek_bar_speed_silabico_estudiante);

        mButtonSpeak = (Button) view.findViewById(R.id.btn_silabico_estudiante_tipo1_textToS);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
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



        campanada = 0;

        btn_bell = (Button) view.findViewById(R.id.btn_silabico_estudiante_bell);
        btn_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campanada++;
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
        btn_b1 = (Button) view.findViewById(R.id.btn_silabico_estudiante_b1);
        btn_b2 = (Button) view.findViewById(R.id.btn_silabico_estudiante_b2);
        btn_b3 = (Button) view.findViewById(R.id.btn_silabico_estudiante_b3);
        btn_b4 = (Button) view.findViewById(R.id.btn_silabico_estudiante_b4);
        btn_b5 = (Button) view.findViewById(R.id.btn_silabico_estudiante_b5);


        txt_miRespuesta = (TextView) view.findViewById(R.id.txt_silabico_estudiante_resultado);
        btn_responder = (Button) view.findViewById(R.id.btn_silabico_estudiante_Responde);
        btn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (campanada == Integer.parseInt(cantLexemas)) {
                    txt_miRespuesta.setText("CORRECTO");
                } else {
                    txt_miRespuesta.setText("INCORRECTO");
                }
            }
        });
        cargarWebService();


        return view;
    }
    //----------------------------------------------------------------------------------------------
    private void speak() {
        // String text = edt_OrtacionEjercicio.getText().toString();
        //String text = edt_OrtacionEjercicio.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(textOracion, TextToSpeech.QUEUE_FLUSH, null);
        System.out.println("oración: " + textOracion);
    }
    //----------------------------------------------------------------------------------------------

    public void cargarWebService() {

        String url_lh = Globals.url;
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursos.php";
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarEjercicio.php?idEjercicioG1=" + idEjercicio;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/9wsJSONConsultarEjercicioEstudiante.php?idEjercicioG2="+idEjercicio;
        //String url = ip+"ejemploBDRemota/wsJSONConsultarLista.php";
        //reemplazar espacios en blanco del nombre por %20
        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(context).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getContext(), "web service", Toast.LENGTH_LONG).show();
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

    //----------------------------------------------------------------------------------------------

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
