package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.fonico;

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
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.activity.estudiante.HomeEstudianteActivity;
import com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.CasaHomeEstudianteFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo1FonicoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1FonicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1FonicoFragment extends Fragment
        implements Response.Listener<JSONObject>, Response.ErrorListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String usuario;
    private TextView txt_letra;

    private String letra;

    private int letraIgual = 0;
    private int letraNoIgual = 0;
    private int letraIgual_correcto = 0;

    private boolean flagf1_c1 = false;
    private boolean flagf1_c2 = false;
    private boolean flagf1_c3 = false;
    private boolean flagf1_c4 = false;

    private String letraf1_c1;
    private String letraf1_c2;
    private String letraf1_c3;
    private String letraf1_c4;

    private int idejercicio1;
    private int idejercicio2;
    private int idejercicio3;
    private int idejercicio4;

    private int flagc1 = 0;
    private int flagc2 = 0;
    private int flagc3 = 0;
    private int flagc4 = 0;

    private int filejercicio1;
    private int filejercicio2;
    private int filejercicio3;
    private int filejercicio4;

    private int colejercicio1;
    private int colejercicio2;
    private int colejercicio3;
    private int colejercicio4;

    private ImageView iv_f1_c1;
    private ImageView iv_f1_c2;
    private ImageView iv_f1_c3;
    private ImageView iv_f1_c4;

    private Button btn_verificar_tipo1;

    private Button btn_selected_c1;
    private Button btn_selected_c2;
    private Button btn_selected_c3;
    private Button btn_selected_c4;

    private TextView txt_resultado;

    private Imagen imagen;
    String urlImagen;
    int f = -1;

    ArrayList<Integer> listaIdImagens;
    ArrayList<Integer> listafilImagenes;
    ArrayList<Integer> listacolImagenes;

    ArrayList<Response> responses = new ArrayList<>();

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;

    int iddeber;
    ProgressDialog progreso;
    int nota;
    int intento = 3;
    TextView txt_intento;
    LinearLayout ll_intento;
    String nameestudiante;
    int idestudiante;
    VideoView vv_video;
    String path;
    Button btn_ocultar;
    LinearLayout ll_ocultar_video;
    LinearLayout ll_ejercicio;
    int cont = 0;
    MediaPlayer mediaPlayer;
    MediaPlayer mp1;
    MediaPlayer mp2;

    private OnFragmentInteractionListener mListener;

    public Tipo1FonicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1FonicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo1FonicoFragment newInstance(String param1, String param2) {
        Tipo1FonicoFragment fragment = new Tipo1FonicoFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo1_fonico_estudiante, container, false);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.ping4);
        mp1 = MediaPlayer.create(getContext(), R.raw.exito);
        mp2 = MediaPlayer.create(getContext(), R.raw.error);

        txt_letra = view.findViewById(R.id.txt_estudiante_fonico1_letra);

        txt_resultado = view.findViewById(R.id.txt_fonico_resultado_t1);
        txt_intento = view.findViewById(R.id.txt_est_fon1_intento);
        ll_intento = view.findViewById(R.id.ll_est_fon1_intent);
        ll_ocultar_video = view.findViewById(R.id.ll_est_fon1_video);

        ll_ejercicio = view.findViewById(R.id.ll_est_fon1_ejercicio);
        ll_ejercicio.setVisibility(View.GONE);

        btn_ocultar = view.findViewById(R.id.btn_est_fon1_ocultar);
        btn_ocultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_ocultar_video.setVisibility(View.GONE);
                ll_ejercicio.setVisibility(View.VISIBLE);
                vv_video.stopPlayback();
            }
        });

        vv_video = view.findViewById(R.id.vv_est_fon1);

        verVideo();

        letra = getArguments().getString("letrainicial");
        usuario = getArguments().getString("usuario");

        iddeber = getArguments().getInt("idesthasdeber");
        System.out.println("iddeber: " + iddeber);

        nameestudiante = getArguments().getString("nameEstudiante");
        idestudiante = getArguments().getInt("idEstudiante");

        btn_verificar_tipo1 = (Button) view.findViewById(R.id.btn_estudiante_fonico_enviar_tipo1);
        btn_verificar_tipo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarEjercicio();

            }
        });

        btn_selected_c1 = (Button) view.findViewById(R.id.btn_fonico_selected_c1);
        btn_selected_c2 = (Button) view.findViewById(R.id.btn_fonico_selected_c2);
        btn_selected_c3 = (Button) view.findViewById(R.id.btn_fonico_selected_c3);
        btn_selected_c4 = (Button) view.findViewById(R.id.btn_fonico_selected_c4);


        idejercicio1 = getArguments().getInt("idejercicio1");
        idejercicio2 = getArguments().getInt("idejercicio2");
        idejercicio3 = getArguments().getInt("idejercicio3");
        idejercicio4 = getArguments().getInt("idejercicio4");

        filejercicio1 = getArguments().getInt("filejercicio1");
        filejercicio2 = getArguments().getInt("filejercicio2");
        filejercicio3 = getArguments().getInt("filejercicio3");
        filejercicio4 = getArguments().getInt("filejercicio4");

        colejercicio1 = getArguments().getInt("colejercicio1");
        colejercicio2 = getArguments().getInt("colejercicio2");
        colejercicio3 = getArguments().getInt("colejercicio3");
        colejercicio4 = getArguments().getInt("colejercicio4");

       /* System.out.println("fila ejercicio 1: " + filejercicio1);
        System.out.println("fila ejercicio 2: " + filejercicio2);
        System.out.println("fila ejercicio 3: " + filejercicio3);
        System.out.println("fila ejercicio 4: " + filejercicio4);

        System.out.println("columna ejercicio 1: " + colejercicio1);
        System.out.println("columna ejercicio 2: " + colejercicio2);
        System.out.println("columna ejercicio 3: " + colejercicio3);
        System.out.println("columna ejercicio 4: " + colejercicio4);

        System.out.println("idejercicio1: " + idejercicio1);
        System.out.println("idejercicio2: " + idejercicio2);
        System.out.println("idejercicio3: " + idejercicio3);
        System.out.println("idejercicio4: " + idejercicio4);*/

        listaIdImagens = new ArrayList<>();
        listacolImagenes = new ArrayList<>();
        listafilImagenes = new ArrayList<>();

        listaIdImagens.add(idejercicio1);
        listaIdImagens.add(idejercicio2);
        listaIdImagens.add(idejercicio3);
        listaIdImagens.add(idejercicio4);

        iv_f1_c1 = (ImageView) view.findViewById(R.id.iv_estudiante_fonico1_f1c1);
        iv_f1_c2 = (ImageView) view.findViewById(R.id.iv_estudiante_fonico1_f1c2);
        iv_f1_c3 = (ImageView) view.findViewById(R.id.iv_estudiante_fonico1_f1c3);
        iv_f1_c4 = (ImageView) view.findViewById(R.id.iv_estudiante_fonico1_f1c4);

        iv_f1_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagc1 == 0) {
                    mediaPlayer.start();
                    flagf1_c1 = true;
                    flagc1++;
                    btn_selected_c1.setVisibility(View.VISIBLE);
                    System.out.println(" flagf1_c1 : " + flagf1_c1);
                    Toast.makeText(getContext(), "iv_f1_c1", Toast.LENGTH_LONG).show();
                } else {
                    mediaPlayer.start();
                    flagf1_c1 = false;
                    flagc1--;
                    btn_selected_c1.setVisibility(View.INVISIBLE);
                    System.out.println(" flagf1_c1 : " + flagf1_c1);
                }
                //btn_selected_c1.setBackground(getResources().getDrawable(R.drawable.selec));
            }
        });
        iv_f1_c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (flagc2 == 0) {
                    flagf1_c2 = true;
                    flagc2++;
                    btn_selected_c2.setVisibility(View.VISIBLE);
                    System.out.println(" flagf1_c2 : " + flagf1_c2);
                } else {
                    mediaPlayer.start();
                    flagf1_c2 = false;
                    flagc2--;
                    btn_selected_c2.setVisibility(View.INVISIBLE);
                    System.out.println(" flagf1_c2 : " + flagf1_c2);
                }
            }
        });
        iv_f1_c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagc3 == 0) {
                    mediaPlayer.start();
                    flagf1_c3 = true;
                    flagc3++;
                    btn_selected_c3.setVisibility(View.VISIBLE);
                    System.out.println(" flagf1_c3 : " + flagf1_c3);
                } else {
                    mediaPlayer.start();
                    flagf1_c3 = false;
                    flagc3--;
                    btn_selected_c3.setVisibility(View.INVISIBLE);
                    System.out.println(" flagf1_c3 : " + flagf1_c3);
                }
            }
        });
        iv_f1_c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagc4 == 0) {
                    mediaPlayer.start();
                    flagf1_c4 = true;
                    flagc4++;
                    btn_selected_c4.setVisibility(View.VISIBLE);
                    System.out.println(" flagf1_c4 : " + flagf1_c4);
                } else {
                    mediaPlayer.start();
                    flagf1_c4 = false;
                    flagc4--;
                    btn_selected_c4.setVisibility(View.INVISIBLE);
                    System.out.println(" flagf1_c4 : " + flagf1_c4);
                }
            }
        });

        System.out.println("lista de imagenes: " + listaIdImagens.toString());

        txt_letra.setText(letra);

        llamarWebService();

        return view;
    }//create

    private void verVideo() {

        vv_video.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.vid_fon_1);
        vv_video.setVideoURI(uri);
        vv_video.start();

        CountDownTimer cTimer = null;
        cTimer = new CountDownTimer(29000, 1000) {
            public void onTick(long millisUntilFinished) {
                cont++;
                System.out.println("el cont: " + cont);
            }

            @Override
            public void onFinish() {
                ll_ocultar_video.setVisibility(View.GONE);
                ll_ejercicio.setVisibility(View.VISIBLE);
            }
        }.start();

        /*if (cont == 2) {
            ll_ocultar_video.setVisibility(View.GONE);
            cont = 0;
        }*/


        //System.out.println("vv_video.getDrawingTime()" + vv_video.getDrawingTime());
        //System.out.println("startTime"+startTime);
       /* if (vv_video.isPlaying()) {
            ll_ocultar_video.setVisibility(View.VISIBLE);
        }else {
            ll_ocultar_video.setVisibility(View.GONE);
            vv_video.getDuration();
        }*/
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
    private void enviarNota() {

        String dconfo = "dconfo";
        String dconfo_mensaje = "Tiene un nuevo mensaje";

        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        int icono = R.drawable.home;
        Intent i=new Intent(getActivity(), HomeEstudianteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, i, 0);

        mBuilder =new NotificationCompat.Builder(getContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Ejercicio Realizado")
                .setContentText("Tu nota es:"+nota)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setAutoCancel(true);



        mNotifyMgr.notify(1, mBuilder.build());
    }
    // ----------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------


    private void verificarEjercicio() {

        if (letraf1_c1.equals(letra)) {
            //Toast.makeText(getContext(), "Son iguales", Toast.LENGTH_SHORT).show();
            letraIgual++;
            System.out.println(" Son iguales c1: " + letraIgual);
            if (flagf1_c1) {
                letraIgual_correcto++;
                System.out.println(" correcto c1: " + letraIgual_correcto);
            }
        }
        if (letraf1_c2.equals(letra)) {
            //Toast.makeText(getContext(), "Son iguales", Toast.LENGTH_SHORT).show();
            letraIgual++;
            System.out.println(" Son iguales c2: " + letraIgual);
            if (flagf1_c2) {
                letraIgual_correcto++;
                System.out.println(" correcto c2: " + letraIgual_correcto);
            }
        }
        if (letraf1_c3.equals(letra)) {
            //Toast.makeText(getContext(), "Son iguales", Toast.LENGTH_SHORT).show();
            letraIgual++;
            System.out.println(" Son iguales c3: " + letraIgual);
            if (flagf1_c3) {
                letraIgual_correcto++;
                System.out.println(" correcto c3: " + letraIgual_correcto);
            }
        }
        if (letraf1_c4.equals(letra)) {
            //Toast.makeText(getContext(), "Son iguales", Toast.LENGTH_SHORT).show();
            letraIgual++;
            System.out.println(" Son iguales c4: " + letraIgual);
            if (flagf1_c4) {
                letraIgual_correcto++;
                System.out.println(" correcto c4: " + letraIgual_correcto);
            }
        }
        if (!letraf1_c1.equals(letra) && flagf1_c1 == true) {
            letraNoIgual++;
        }
        if (!letraf1_c2.equals(letra) && flagf1_c2 == true) {
            letraNoIgual++;
        }
        if (!letraf1_c3.equals(letra) && flagf1_c3 == true) {
            letraNoIgual++;
        }
        if (!letraf1_c4.equals(letra) && flagf1_c4 == true) {
            letraNoIgual++;
        }
        if (letraIgual == letraIgual_correcto && letraNoIgual == 0) {
            System.out.println(" Ejercicio aprobado. cant letras igual a letra: " + letraIgual + " = " + letraIgual_correcto);
            System.out.println(" letra igual - letra no igual: " + letraIgual + " = " + letraNoIgual);
            txt_resultado.setText("Muy Bien!!!!");
            mp1.start();
            nota = 5;
            cargarWebService_1();
            enviarNota();
            letraIgual_correcto = 0;
            letraIgual = 0;
            letraNoIgual = 0;
            flagf1_c1 = false;
            flagf1_c2 = false;
            flagf1_c3 = false;
            flagf1_c4 = false;

            flagc1 = 0;
            flagc2 = 0;
            flagc3 = 0;
            flagc4 = 0;

           /* letraf1_c1 = "";
            letraf1_c2 = "";
            letraf1_c3 = "";
            letraf1_c4 = "";*/
            btn_selected_c1.setVisibility(View.INVISIBLE);
            btn_selected_c2.setVisibility(View.INVISIBLE);
            btn_selected_c3.setVisibility(View.INVISIBLE);
            btn_selected_c4.setVisibility(View.INVISIBLE);
        } else {
            System.out.println("Error letra igual - letra no igual: " + letraIgual + " = " + letraNoIgual);
            txt_resultado.setText("Intentalo de nuevo");
            mp2.start();
            mostrarError();
            intento--;
            txt_intento.setText(String.valueOf(intento));
            if (intento == 0) {
                nota = 1;
                cargarWebService_1();
                mostrarInforme();
                enviarNota();
            }
            letraIgual_correcto = 0;
            letraIgual = 0;
            letraNoIgual = 0;

            flagf1_c1 = false;
            flagf1_c2 = false;
            flagf1_c3 = false;
            flagf1_c4 = false;

            flagc1 = 0;
            flagc2 = 0;
            flagc3 = 0;
            flagc4 = 0;

           /* letraf1_c1 = "";
            letraf1_c2 = "";
            letraf1_c3 = "";
            letraf1_c4 = "";*/
            btn_selected_c1.setVisibility(View.INVISIBLE);
            btn_selected_c2.setVisibility(View.INVISIBLE);
            btn_selected_c3.setVisibility(View.INVISIBLE);
            btn_selected_c4.setVisibility(View.INVISIBLE);
        }

    }

    private void mostrarInforme() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Informe");
        alertDialog.setMessage("Fallaste!!! ");
        Drawable drawable = ll_intento.getResources().getDrawable(R.drawable.llorando_96);
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
        }else {
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

    public void llamarWebService() {
        //for (int i = 0; i < listaIdImagens.size(); i++) {
        if (f < 3) {
            f++;
            System.out.println("valor f: " + f);
            cargarWebService(listaIdImagens.get(f));
        }


        //}
    }

    public void cargarWebService(int idejercicio) {

        String url_lh = Globals.url;
        // System.out.println("f: " + f);
        // String ip = getString(R.string.ip);

        //String url = "http://192.168.0.13/proyecto_dconfo/wsJSONConsultarListaCursos.php";
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarImagen.php?idImagen_Ejercicio=" + idejercicio;
        //String url = ip+"ejemploBDRemota/wsJSONConsultarLista.php";
        //reemplazar espacios en blanco del nombre por %20
        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getContext(), "web service", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        // Toast.makeText(getContext(), "No se puede conectar exitosamente" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());

    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(JSONObject response) {
        // public ArrayList<Curso> onResponse(JSONObject response) {
        //lectura del Json

        //Toast.makeText(getContext(), "onResponse: " + response.toString(), Toast.LENGTH_SHORT).show();
        imagen = null;
        JSONArray json = response.optJSONArray("imagen");

        ArrayList<Imagen> listaDEimagenes = new ArrayList<>();
        listaDEimagenes = new ArrayList<>();

        try {
            for (int i = 0; i < json.length(); i++) {
                imagen = new Imagen();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                imagen.setIdImagen(jsonObject.optInt("idImagen_Ejercicio"));
                imagen.setNameImagen(jsonObject.optString("name_Imagen_Ejercicio"));
                imagen.setRutaImagen(jsonObject.optString("ruta_Imagen_Ejercicio"));
                imagen.setLetraInicialImagen(jsonObject.optString("letra_inicial_Imagen"));
                imagen.setLetraFinalImagen(jsonObject.optString("letra_final_Imagen"));
                imagen.setCantSilabasImagen(jsonObject.optInt("cant_silabas_Imagen"));
                listaDEimagenes.add(imagen);

            }
           /* textOracion = ejercicioG1.getOracion();
            cantLexemas = ejercicioG1.getCantidadValida();*/

            String url_lh = Globals.url;

            final String rutaImagen = imagen.getRutaImagen();

            urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
            urlImagen = urlImagen.replace(" ", "%20");

            ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    //holder.imagen.setImageBitmap(response);


                    if (f == 0) {
                        letraf1_c1 = imagen.getLetraInicialImagen();
                        System.out.println("  letraf1_c1: " + letraf1_c1);
                        iv_f1_c1.setBackground(null);
                        iv_f1_c1.setImageBitmap(response);
                    } else if (f == 1) {
                        letraf1_c2 = imagen.getLetraInicialImagen();
                        iv_f1_c2.setBackground(null);
                        iv_f1_c2.setImageBitmap(response);
                    } else if (f == 2) {
                        letraf1_c3 = imagen.getLetraInicialImagen();
                        iv_f1_c3.setBackground(null);
                        iv_f1_c3.setImageBitmap(response);
                    } else if (f == 3) {
                        letraf1_c4 = imagen.getLetraInicialImagen();
                        iv_f1_c4.setBackground(null);
                        iv_f1_c4.setImageBitmap(response);
                    }
                    llamarWebService();

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
            //Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

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
