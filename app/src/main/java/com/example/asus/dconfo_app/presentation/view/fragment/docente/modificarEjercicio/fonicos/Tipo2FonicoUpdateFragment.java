package com.example.asus.dconfo_app.presentation.view.fragment.docente.modificarEjercicio.fonicos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.dconfo_app.R;
import com.example.asus.dconfo_app.domain.model.EjercicioG1;
import com.example.asus.dconfo_app.domain.model.EjercicioG2;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasImagen;
import com.example.asus.dconfo_app.domain.model.EjercicioG2HasLetrag2;
import com.example.asus.dconfo_app.domain.model.Imagen;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
import com.example.asus.dconfo_app.presentation.view.adapter.ImagenUrlAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2FonicoUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2FonicoUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2FonicoUpdateFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView iv_c1f1;
    private ImageView iv_c1f2;
    private ImageView iv_c1f3;
    private ImageView iv_c1f4;

    private CircleImageView cv_c1f1;
    private CircleImageView cv_c1f2;
    private CircleImageView cv_c1f3;
    private CircleImageView cv_c1f4;

    private RadioButton rb_letraInicial;
    private RadioButton rb_letraFinal;

    private boolean flag_iv_c1f1;
    private boolean flag_iv_c1f2;
    private boolean flag_iv_c1f3;
    private boolean flag_iv_c1f4;
    private boolean estado_RbletraInicial;
    private boolean estado_RbletraFinal;

    boolean cargarImagen_boolen = false;

    private TextView txt_name_c1f1;
    private TextView txt_name_c1f2;
    private TextView txt_name_c1f3;
    private TextView txt_name_c1f4;


    private EditText edt_l1;
    private EditText edt_l2;
    private EditText edt_l3;
    private EditText edt_l4;
    private EditText edt_nameEjercicio;

    private LinearLayout ll_c1f1;
    private LinearLayout ll_c1f2;
    private LinearLayout ll_c1f3;
    private LinearLayout ll_c1f4;

    private LinearLayout ll_letra1;
    private LinearLayout ll_letra2;
    private LinearLayout ll_letra3;
    private LinearLayout ll_letra4;

    private LinearLayout ll_createImage;
    private LinearLayout ll_createExercice;

    private EjercicioG2HasImagen ejercicioG2HasImagen;
    private EjercicioG2HasLetrag2 ejercicioG2HasLetrag2;
    private EjercicioG2 ejercicioG2;

    private RecyclerView rv_imagenesBancoDatos;
    private RecyclerView rv_tipo1Fonico;

    private Button btn_enviar;
    private Button btn_crearImg;

    ArrayList<Imagen> listaImagenes;
    ArrayList<EjercicioG2> listaEjerciciosG2;
    ArrayList<EjercicioG1> listaEjercicios;
    ArrayList<Integer> listaidImagenes;
    ArrayList<Integer> listafilaImagen;
    ArrayList<Integer> listacolumnaImagen;

    ArrayList<String> listaLetras;
    ArrayList<Integer> listafilaLetra;
    ArrayList<Integer> listacolumnaLetra;

    private String nameDocente;
    private int idDocente;
    private int idEjercicio2;
    private int cont = 0;
    private String letra_inicial;
    private String letra_final;

    String rutaImagen;
    String nameImagen;
    int idImagen;

    private EditText edt_nameImagen;
    private EditText edt_letraInicial;
    private EditText edt_letraFinal;
    private EditText edt_cantSilabas;


    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;

    ProgressDialog progreso;
    ImageView imgFoto;
    File fileImagen;
    Bitmap bitmap;

    int idejercicio;
    int idgrupo;
    int flag = 0;

    private OnFragmentInteractionListener mListener;

    public Tipo2FonicoUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2FonicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2FonicoUpdateFragment newInstance(String param1, String param2) {
        Tipo2FonicoUpdateFragment fragment = new Tipo2FonicoUpdateFragment();
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
        final View view = inflater.inflate(R.layout.fragment_tipo2_fonico_update, container, false);

        progreso=new ProgressDialog(getActivity());

        nameDocente = getArguments().getString("namedocente");
        idDocente = getArguments().getInt("iddocente");


        idejercicio = getArguments().getInt("idejercicio");
        idgrupo = getArguments().getInt("idgrupo");

        ejercicioG2HasImagen = new EjercicioG2HasImagen();
        ejercicioG2HasLetrag2 = new EjercicioG2HasLetrag2();

        listaImagenes = new ArrayList<>();
        listaEjerciciosG2 = new ArrayList<>();
        listaEjercicios = new ArrayList<>();
        listaidImagenes = new ArrayList<>();
        listafilaImagen = new ArrayList<>();
        listacolumnaImagen = new ArrayList<>();

        listaLetras = new ArrayList<>();
        listafilaLetra = new ArrayList<>();
        listacolumnaLetra = new ArrayList<>();

        ll_createImage = (LinearLayout) view.findViewById(R.id.ll_fonico2_update_form_create_img);
        ll_createExercice = (LinearLayout) view.findViewById(R.id.ll_fonico2_update_exe_imgs);

        rv_imagenesBancoDatos = (RecyclerView) view.findViewById(R.id.rv_docente_fon2_imgs_update);
       // rv_imagenesBancoDatos.setLayoutManager(new LinearLayoutManager(getContext()));
        //rv_imagenesBancoDatos.setHasFixedSize(true);
        rv_imagenesBancoDatos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_imagenesBancoDatos.setHasFixedSize(true);

        //rv_imagenesBancoDatos.setVisibility(View.INVISIBLE);

        rb_letraInicial = (RadioButton) view.findViewById(R.id.rb_letraInicial_update);
        rb_letraFinal = (RadioButton) view.findViewById(R.id.rb_letraFinal_update);

        //verificaRadioButton();

        ll_c1f1 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_c1_f1);
        ll_c1f2 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_c1_f2);
        ll_c1f3 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_c1_f3);
        ll_c1f4 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_c1_f4);

        ll_letra1 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_l1);
        ll_letra2 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_l2);
        ll_letra3 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_l3);
        ll_letra4 = (LinearLayout) view.findViewById(R.id.ll_docente_fon2_l4);

        txt_name_c1f1 = (TextView) view.findViewById(R.id.txt_docente_fon2_nom_c1f1_update);
        txt_name_c1f2 = (TextView) view.findViewById(R.id.txt_docente_fon2_nom_c1f2_update);
        txt_name_c1f3 = (TextView) view.findViewById(R.id.txt_docente_fon2_nom_c1f3_update);
        txt_name_c1f4 = (TextView) view.findViewById(R.id.txt_docente_fon2_nom_c1f4_update);


        cv_c1f1 = (CircleImageView) view.findViewById(R.id.iv_docente_fon2_c1_f1_update);
        cv_c1f2 = (CircleImageView) view.findViewById(R.id.iv_docente_fon2_c1_f2_update);
        cv_c1f3 = (CircleImageView) view.findViewById(R.id.iv_docente_fon2_c1_f3_update);
        cv_c1f4 = (CircleImageView) view.findViewById(R.id.iv_docente_fon2_c1_f4_update);

        edt_nameImagen = (EditText) view.findViewById(R.id.edt_fonico2_update_name_image);
        edt_letraInicial = (EditText) view.findViewById(R.id.edt_fonico2_update_let_ini);
        edt_letraFinal = (EditText) view.findViewById(R.id.edt_fonico2_update_let_final);
        edt_cantSilabas = (EditText) view.findViewById(R.id.edt_fonico2_update_cant_silabas);


        edt_nameEjercicio = (EditText) view.findViewById(R.id.edt_docente_fon2_nameEjercicio_update);

        edt_l1 = (EditText) view.findViewById(R.id.edt_docente_fon2_l1_update);
        edt_l1.setFilters(new InputFilter[]
                {new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(1)}
        );

        edt_l2 = (EditText) view.findViewById(R.id.edt_docente_fon2_l2_update);
        edt_l2.setFilters(new InputFilter[]
                {new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(1)}
        );
        edt_l3 = (EditText) view.findViewById(R.id.edt_docente_fon2_l3_update);
        edt_l3.setFilters(new InputFilter[]
                {new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(1)}
        );
        edt_l4 = (EditText) view.findViewById(R.id.edt_docente_fon2_l4_update);
        edt_l4.setFilters(new InputFilter[]
                {new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(1)}
        );

//        iv_c1f1.setOnClickListener(this);
        cv_c1f1.setOnClickListener(this);
        cv_c1f2.setOnClickListener(this);
        cv_c1f3.setOnClickListener(this);
        cv_c1f4.setOnClickListener(this);

        btn_crearImg = (Button) view.findViewById(R.id.btn_fonico2_update_create_img);
        btn_crearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearImagen();
                //cargarImagen();
                //cargarImagenWebService();
                ll_createImage.setVisibility(View.GONE);
            }
        });


        btn_enviar = (Button) view.findViewById(R.id.btn_docente_fon2_enviar_update);
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaRadioButton();
                cargarEdt_letras();
                cargarWS_CrearEjerciciog2_tipo2();
                //System.out.println("id del docente: " + idDocente);
                //verificaRadioButton();
            }
        });

        imgFoto = new ImageView(getContext());
        consultarListaImagenes();


        return view;

    }//--------------------------------------------------------------------------------ONCREATE-----

    private void cargarEdt_letras() {
        listaLetras.add(edt_l1.getText().toString());
        listafilaLetra.add(1);
        listacolumnaLetra.add(1);

        listaLetras.add(edt_l2.getText().toString());
        listafilaLetra.add(2);
        listacolumnaLetra.add(1);

        listaLetras.add(edt_l3.getText().toString());
        listafilaLetra.add(3);
        listacolumnaLetra.add(1);

        listaLetras.add(edt_l4.getText().toString());
        listafilaLetra.add(4);
        listacolumnaLetra.add(1);

        System.out.println("lista letras: "+listaLetras.toString());
        System.out.println("lista fila letra: "+listafilaLetra.toString());
        System.out.println("lista col letra: "+listacolumnaLetra.toString());
    }

    public void verificaRadioButton() {//f1
        if (rb_letraInicial.isChecked() == true) {
            estado_RbletraInicial = rb_letraInicial.isChecked();
            System.out.println("Radio letra inicial estado: " + estado_RbletraInicial);
            //System.out.println("Radio letra final estado: "+estado_RbletraFinal);
            Toast.makeText(getContext(), "Radio letra inicial estado: " + estado_RbletraInicial, Toast.LENGTH_SHORT).show();
        } else if (rb_letraFinal.isChecked() == true) {
            //System.out.println("Radio letra inicial estado: "+estado_RbletraInicial);
            estado_RbletraFinal = rb_letraFinal.isChecked();
            System.out.println("Radio letra final estado: " + estado_RbletraFinal);
            Toast.makeText(getContext(), "Radio letra final estado: " + estado_RbletraFinal, Toast.LENGTH_SHORT).show();
        }
    }
    //**********************************************************************************************

 /*   Button button = (Button)findViewById(R.id.corky);
        button.setOnClickListener(this);
}*/

    // Implement the OnClickListener callback
    public void onClick(View v) {//f2
        switch (v.getId()) {
            case R.id.iv_docente_fon2_c1_f1_update:
                mostrarDialogOpciones();
                flag_iv_c1f1 = true;
                break;
            case R.id.iv_docente_fon2_c1_f2_update:
                mostrarDialogOpciones();
                flag_iv_c1f2 = true;
                break;
            case R.id.iv_docente_fon2_c1_f3_update:
                mostrarDialogOpciones();
                flag_iv_c1f3 = true;
                break;
            case R.id.iv_docente_fon2_c1_f4_update:
                mostrarDialogOpciones();
                flag_iv_c1f4 = true;
                break;
        }
    }

    //----------------------------------------------------------------------------------------------
    private void crearImagen() {

        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = Globals.url;

        String url = "http://" + ip + "/proyecto_dconfo_v1/24wsJSONCrearImagen.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    edt_nameImagen.setText("");
                    edt_letraInicial.setText("");
                    edt_letraFinal.setText("");
                    edt_cantSilabas.setText("");
                    progreso.hide();
                    ll_createImage.setVisibility(View.GONE);

                    cargarImagen_boolen = true;

                    consultarListaImagenes();

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    System.out.println("error: " + response);
                    progreso.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                //progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //String idEjercicio = "7";
                String nameimagen = edt_nameImagen.getText().toString();
                String letra_inicial = edt_letraInicial.getText().toString();
                String letra_final = edt_letraFinal.getText().toString();
                String cant_silabas = edt_cantSilabas.getText().toString();
                String imagen = convertirImgString(bitmap);

                System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);
                parametros.put("name_Imagen", nameimagen);
                parametros.put("letra_inicial", letra_inicial);
                parametros.put("letra_final", letra_final);
                parametros.put("cant_silabas", cant_silabas);
                parametros.put("imagen", imagen);

                //System.out.println("parametros: " + parametros);
                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21
    }

    //----------------------------------------------------------------------------------------------
    private String convertirImgString(Bitmap bitmap) {
        //recibe un bitmap
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imagenByte = array.toByteArray();
        //codifica a base64
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    // ----------------------------------------------------------------------------------------------
    //******************************WEB SERVICE
    //para iniciar el proceso de llamado al webservice
    private void cargarWS_CrearEjerciciog2_tipo2() {//f3
       /* progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/
        String ip = Globals.url;
        //String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONRegistroTipo1Fonico.php";//p12.buena
        String url = "http://" + ip + "/proyecto_dconfo_v1/25wsJSON_Update_Fonico1.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    //edt_letra.setText("");
                    edt_nameEjercicio.setText("");
                    listarEjerciciosG2Docente();

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito ---" + response.toString(), Toast.LENGTH_LONG).show();
                    System.out.println("response: " + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                //progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String id_ejercicio = String.valueOf(idejercicio);
                String nameEjercicio = edt_nameEjercicio.getText().toString();

                if (estado_RbletraInicial) {

                    letra_inicial = "s";
                    letra_final = "n";

                    System.out.println("estado_RbletraInicial :" + estado_RbletraInicial);
                    System.out.println("letra_inicial 1 :" + letra_inicial);
                    System.out.println("letra_final 1 :" + letra_final);
                }
                if (estado_RbletraFinal) {

                    letra_inicial = "n";
                    letra_final = "s";

                    System.out.println("estado_RbletraFinal :" + estado_RbletraFinal);
                    System.out.println("letra_inicial 2:" + letra_inicial);
                    System.out.println("letra_final 2:" + letra_final);
                }

                String imagen = "";
                String cantidad = "";
                String oracion = "";

                System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                parametros.put("idejercicio", id_ejercicio);
                parametros.put("nameEjercicioG2", nameEjercicio);

                parametros.put("letra_inicial_EjercicioG2", letra_inicial);
                parametros.put("letra_final_EjercicioG2", letra_final);

                System.out.println("parametros update fon 1: " + parametros.toString());
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

    //----------------------------------------------------------------------------------------------

    private void cargarImagen() {
        Drawable drawable = imgFoto.getDrawable();
        idImagen = listaImagenes.get(listaImagenes.size() - 1).getIdImagen();
       /* btn_img_4.setBackground(null);
        btn_img_4.setImageBitmap(response);
        btn_4Activo = false;
        rv_tipo1Fonico.setVisibility(View.GONE);
        txt_id_img4.setText(nameImagen);*/
        System.out.println("Lista imagenes size CI: " + listaImagenes.size());
        System.out.println("Lista imagenes: " + listaImagenes.get(listaImagenes.size() - 1).getIdImagen());
        if (flag_iv_c1f1) {
            cv_c1f1.setBackground(null);
            cv_c1f1.setImageBitmap(bitmap);
            flag_iv_c1f1 = false;

            int fila = 1;
            int columna = 1;
            ejercicioG2HasImagen.setIdImagen(idImagen);
            ejercicioG2HasImagen.setFilaImagen(fila);
            ejercicioG2HasImagen.setColumnaImagen(columna);

            listaidImagenes.add(idImagen);
            listafilaImagen.add(fila);
            listacolumnaImagen.add(columna);

            cargarImagen_boolen = false;
            ll_createExercice.setVisibility(View.VISIBLE);
            //*************
            txt_name_c1f1.setText(nameImagen);

        } else if (flag_iv_c1f2) {
            cv_c1f2.setBackground(null);
            cv_c1f2.setImageBitmap(bitmap);
            flag_iv_c1f2 = false;

            int fila = 1;
            int columna = 2;
            ejercicioG2HasImagen.setIdImagen(idImagen);
            ejercicioG2HasImagen.setFilaImagen(fila);
            ejercicioG2HasImagen.setColumnaImagen(columna);
            listaidImagenes.add(idImagen);
            listafilaImagen.add(fila);
            listacolumnaImagen.add(columna);

            cargarImagen_boolen = false;
            ll_createExercice.setVisibility(View.VISIBLE);
            txt_name_c1f2.setText(nameImagen);

        } else if (flag_iv_c1f3) {
            cv_c1f3.setBackground(null);
            cv_c1f3.setImageBitmap(bitmap);
            flag_iv_c1f3 = false;
            int fila = 1;
            int columna = 3;
            ejercicioG2HasImagen.setIdImagen(idImagen);
            ejercicioG2HasImagen.setFilaImagen(fila);
            ejercicioG2HasImagen.setColumnaImagen(columna);
            listaidImagenes.add(idImagen);
            listafilaImagen.add(fila);
            listacolumnaImagen.add(columna);

            cargarImagen_boolen = false;
            ll_createExercice.setVisibility(View.VISIBLE);
            txt_name_c1f3.setText(nameImagen);

        } else if (flag_iv_c1f4) {
            cv_c1f4.setBackground(null);
            cv_c1f4.setImageBitmap(bitmap);
            flag_iv_c1f4 = false;

            int fila = 1;
            int columna = 4;
            ejercicioG2HasImagen.setIdImagen(idImagen);
            ejercicioG2HasImagen.setFilaImagen(fila);
            ejercicioG2HasImagen.setColumnaImagen(columna);
            listaidImagenes.add(idImagen);
            listafilaImagen.add(fila);
            listacolumnaImagen.add(columna);

            cargarImagen_boolen = false;
            ll_createExercice.setVisibility(View.VISIBLE);
            txt_name_c1f4.setText(nameImagen);
        }
        // btn_Tipo1_pic_Ejercicio.setBackground(drawable);
        //imageView_muestra.setBackground(drawable);
    }

    //**********************************************************************************************

    private void cargarImagenWebService() {//f4

        // String ip = context.getString(R.string.ip);

        String url_lh = Globals.url;

        String urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (flag_iv_c1f1) {
                    cv_c1f1.setBackground(null);
                    cv_c1f1.setImageBitmap(response);
                    //iv_c1f1.setBackground(null);
                    //iv_c1f1.setImageBitmap(response);
                    flag_iv_c1f1 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f1.setText(nameImagen);

                    int fila = 1;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f2) {
                    cv_c1f2.setBackground(null);
                    cv_c1f2.setImageBitmap(response);
                    flag_iv_c1f2 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f2.setText(nameImagen);

                    int fila = 2;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f3) {
                    cv_c1f3.setBackground(null);
                    cv_c1f3.setImageBitmap(response);
                    flag_iv_c1f3 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f3.setText(nameImagen);

                    int fila = 3;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }
                if (flag_iv_c1f4) {
                    cv_c1f4.setBackground(null);
                    cv_c1f4.setImageBitmap(response);
                    flag_iv_c1f4 = false;
                    rv_imagenesBancoDatos.setVisibility(View.GONE);
                    txt_name_c1f4.setText(nameImagen);

                    int fila = 4;
                    int columna = 1;

                    ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);

                }

                // iv_bank_prueba.setBackground(null);
                // iv_bank_prueba.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        });
        //request.add(imageRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(imageRequest);
    }


    //----------------------------------------------------------------------------------------------

    private void consultarListaImagenes() {//f5


        String iddoc = "20181";
        String url_lh = Globals.url;

        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSON1ConsultarListaImagenes.php";

        url = url.replace(" ", "%20");
        //hace el llamado a la url
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21
        //Toast.makeText(getApplicationContext(), "web service 1111", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //progreso.hide();
        Toast.makeText(getContext(), "No se puede cone , grupo doc" + error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR", error.toString());
        //progreso.hide();
    }

    // si esta bien el llamado a la url entonces entra a este metodo
    @Override
    public void onResponse(final JSONObject response) {
        //progreso.hide();
        //Toast.makeText(getApplicationContext(), "Mensaje: " + response.toString(), Toast.LENGTH_SHORT).show();
        Imagen imagen = null;
        JSONArray json = response.optJSONArray("imagen");

        try {
            for (int i = 0; i < json.length(); i++) {
                imagen = new Imagen();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                // jsonObject = new JSONObject(response);
                imagen.setIdImagen(jsonObject.optInt("idImagen_Ejercicio"));
                imagen.setNameImagen(jsonObject.optString("name_Imagen_Ejercicio"));
                imagen.setRutaImagen(jsonObject.optString("ruta_Imagen_Ejercicio"));
                imagen.setLetraInicialImagen(jsonObject.optString("letra_inicial_Imagen"));
                imagen.setLetraFinalImagen(jsonObject.optString("letra_final_Imagen"));
                imagen.setCantSilabasImagen(jsonObject.optInt("cant_silabas_Imagen"));

                listaImagenes.add(imagen);

//idgrupo,namegrupo,curso_idcurso,curso_Instituto_idInstituto
            }

            ImagenUrlAdapter imagenUrlAdapter = new ImagenUrlAdapter(listaImagenes, getContext());
            imagenUrlAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    rutaImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getRutaImagen();

                    nameImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getNameImagen();

                    idImagen = listaImagenes.get(rv_imagenesBancoDatos.
                            getChildAdapterPosition(v)).getIdImagen();

                    cargarImagenWebService();

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    System.out.println("on click: " + rutaImagen);
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_imagenesBancoDatos.setAdapter(imagenUrlAdapter);

            //Toast.makeText(getApplicationContext(), "listagrupos: " + listaGrupos.size(), Toast.LENGTH_LONG).show();
            //Log.i("size", "lista Imágenes: " + listaImagenes.get(0).getNameImagen());

            //rv_bankimages.setAdapter(gruposDocenteAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("error", response.toString());

            Toast.makeText(getContext(), "No se ha podido establecer conexión: " + response.toString(), Toast.LENGTH_LONG).show();

            //progreso.hide();
        }


        if (cargarImagen_boolen) {
            cargarImagen();
        }

    }


    //**********************************************************************************************

    private void mostrarDialogOpciones() {//part 9 //f6
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Banco de Imágenes", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Imágenes")) {

                    rv_imagenesBancoDatos.setVisibility(View.VISIBLE);
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
                        //directamente de galeria
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
                if (opciones[i].equals("Tomar Foto")) {
                    abriCamara();//part 10 tomar foto
                    Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.show();

    }//*********************************************************************************************

    private void abriCamara() {//part 10
        Toast.makeText(getContext(), "abricamara", Toast.LENGTH_LONG).show();
        File miFile = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if (isCreada == false) {
            isCreada = miFile.mkdirs();
        }

        if (isCreada == true) {
            Toast.makeText(getContext(), "abricamara, istrue", Toast.LENGTH_LONG).show();
            Long consecutivo = System.currentTimeMillis() / 1000;
            String nombre = consecutivo.toString() + ".jpg";

            path = Environment.getExternalStorageDirectory() + File.separator + DIRECTORIO_IMAGEN
                    + File.separator + nombre;//indicamos la ruta de almacenamiento

            fileImagen = new File(path);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));//necesario para activar la cámara,como minimo

            ////

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Toast.makeText(getContext(), "abricamara, N", Toast.LENGTH_LONG).show();
                String authorities = getContext().getPackageName() + ".provider";
                Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
                Toast.makeText(getContext(), "abricamara, Not N", Toast.LENGTH_LONG).show();
            }

            startActivityForResult(intent, COD_FOTO);

            ////

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//p9 de startActivityForResult
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case COD_SELECCIONA:
                Uri miPath = data.getData();
                imgFoto.setImageURI(miPath);
                try {//p12
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), miPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  cargarImagen();
                ll_createImage.setVisibility(View.VISIBLE);
                ll_createExercice.setVisibility(View.GONE);
                break;

            case COD_FOTO://p10
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path", "" + path);
                            }
                        });

                bitmap = BitmapFactory.decodeFile(path);
                imgFoto.setImageBitmap(bitmap);
                ll_createImage.setVisibility(View.VISIBLE);
                ll_createExercice.setVisibility(View.GONE);
                //cargarImagen();
                break;
        }
        bitmap = redimensionarImagen(bitmap, 600, 800);//part 14 redimencionar imágen,guarde en carpeta y BD
    }


    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {//part 14

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();//manipular datos internos de la imagen
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);

        } else {
            return bitmap;
        }
    }

    // ----------------------------------------------------------------------------------------------

    //******************************WEB SERVICE
    public void listarEjerciciosG2Docente() {//f7


        String url_lh = Globals.url;

        //String url = "http://" + url_lh + "/proyecto_dconfo/wsJSONConsultarListaEjerciciosDocente.php?docente_iddocente=" + iddocente;
        //String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarListaEjercicios_Fonico1_Docente.php?iddocente=" + idDocente;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/2wsJSONConsultarListaEjercicios.php?iddocente=" + idDocente;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with response

                        EjercicioG2 ejercicioG2 = null;
                        Toast.makeText(getContext(), "listar ejercicios g2: ", Toast.LENGTH_LONG).show();

                        try {
                            JSONArray json = response.optJSONArray("ejerciciog2");
                            //JSONARRAY array=new JSONARRAY(response);
                            for (int i = 0; i < json.length(); i++) {
                                ejercicioG2 = new EjercicioG2();
                                JSONObject jsonObject = null;
                                jsonObject = json.getJSONObject(i);
                                ejercicioG2.setIdEjercicioG2(jsonObject.optInt("idEjercicioG2"));
                                ejercicioG2.setNameEjercicioG2(jsonObject.optString("nameEjercicioG2"));
                                ejercicioG2.setIdDocente(jsonObject.optInt("docente_iddocente"));
                                ejercicioG2.setIdTipo(jsonObject.optInt("Tipo_idTipo"));
                                ejercicioG2.setIdActividad(jsonObject.optInt("Tipo_Actividad_idActividad"));
                                ejercicioG2.setLetra_inicial_EjercicioG2(jsonObject.optString("letra_inicial_EjercicioG2"));
                                ejercicioG2.setLetra_final_EjercicioG2(jsonObject.optString("letra_final_EjercicioG2"));

                                listaEjerciciosG2.add(ejercicioG2);
                            }
                            idEjercicio2 = listaEjerciciosG2.get(listaEjerciciosG2.size() - 1).getIdEjercicioG2();
                            System.out.println("Último de lista EG2: " + idEjercicio2);

                            if (idEjercicio2 > 0) {

                                ejerciciog2HI_adjuntarImagenes();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.println();
                        Log.d("ERROR Ejercicios G2: ", error.toString());
                    }
                }
        );
        final int MY_DEFAULT_TIMEOUT = 15000;
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(stringRequest);//p21
    }

    private void ejerciciog2HI_adjuntarImagenes() {//f9

        System.out.println("lista id imagenes: " + listaidImagenes.toString());
        System.out.println("lista id imagenes size: " + listaidImagenes.size());

        if (flag < listaidImagenes.size()) {
            System.out.println("flag has_img_adjuntar: " + flag);
            webService_CrearEjercicioG2_Has_Imagen(listaidImagenes.get(flag), listafilaImagen.get(flag), listacolumnaImagen.get(flag));
            webService_CrearEjercicioG2_Has_Letra(listaLetras.get(flag), listafilaLetra.get(flag), listacolumnaLetra.get(flag));
        }

        edt_nameEjercicio.setText("");
        edt_l1.setText("");
        edt_l2.setText("");
        edt_l3.setText("");
        edt_l4.setText("");
        imgFoto.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        Drawable drawable = imgFoto.getDrawable();
        cv_c1f1.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f2.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f3.setImageResource(R.drawable.ic_no_foto_80dp);
        cv_c1f4.setImageResource(R.drawable.ic_no_foto_80dp);

        txt_name_c1f1.setText("imagen 1");
        txt_name_c1f2.setText("imagen 2");
        txt_name_c1f3.setText("imagen 3");
        txt_name_c1f4.setText("imagen 4");

        /*btn_img_1.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_2.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_3.setBackgroundResource(R.drawable.ic_no_foto_80dp);
        btn_img_4.setBackgroundResource(R.drawable.ic_no_foto_80dp);*/


    }

    // ----------------------------------------------------------------------------------------------
    private void webService_CrearEjercicioG2_Has_Imagen(final int id_Imagen, final int fila_imagen, final int columna_imagen) {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...E_H_I");
        //progreso.show();

        System.out.println("flag has_img: " + flag);


        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/26wsJSON_Update_Ejercicio2HasImagen.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    flag++;
                    //edt_letra.setText("");
                    // edt_nameEjercicio.setText("");
                    progreso.hide();
                    ejerciciog2HI_adjuntarImagenes();

                    Toast.makeText(getContext(), "Se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                    System.out.println("error no cargado con exito crearEHI: " + response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar EHI", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String EjercicioG2_idEjercicioG2 = String.valueOf(idejercicio);
                //String EjercicioG2_idEjercicioG2 = String.valueOf(10);
                String Imagen_idImagen_Ejercicio = String.valueOf(id_Imagen);
                String fila_E_h_I = String.valueOf(fila_imagen);
                String columna_E_H_I = String.valueOf(columna_imagen);

                //System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);
                parametros.put("idejercicio", EjercicioG2_idEjercicioG2);
                parametros.put("idimagen", Imagen_idImagen_Ejercicio);
                parametros.put("fila", fila_E_h_I);
                parametros.put("columna", columna_E_H_I);

                System.out.println("Parametros EJERCICIO HAS IMG: " + parametros.toString());


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
    // ----------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------


    //para iniciar el proceso de llamado al webservice

    private void webService_CrearEjercicioG2_Has_Imagen_original(final int id_Imagen, final int fila_imagen, final int columna_imagen) {//f8
       /* progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();*/
        System.out.println(" Entrando a CREAR EG2_HAS_IMG");
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/wsJSONCrearEjercicio2HasImagen.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    //edt_letra.setText("");
                    // edt_nameEjercicio.setText("");
                    // System.out.println("CREAR EG2_HAS_IMG" + response.toString());


                    Toast.makeText(getContext(), "Se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito EHI", Toast.LENGTH_LONG).show();
                    System.out.println("error no cargado con exito crearEHI");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar EHI", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String EjercicioG2_idEjercicioG2 = String.valueOf(idEjercicio2);
                //String EjercicioG2_idEjercicioG2 = String.valueOf(10);
                String Imagen_idImagen_Ejercicio = String.valueOf(id_Imagen);
                String fila_E_h_I = String.valueOf(fila_imagen);
                String columna_E_H_I = String.valueOf(columna_imagen);

                //System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);
                parametros.put("EjercicioG2_idEjercicioG2", EjercicioG2_idEjercicioG2);
                parametros.put("Imagen_idImagen_Ejercicio", Imagen_idImagen_Ejercicio);
                parametros.put("fila_E_h_I", fila_E_h_I);
                parametros.put("columna_E_H_I", columna_E_H_I);

                System.out.println("Parametros: " + parametros.toString());


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
    // ----------------------------------------------------------------------------------------------


    private void webService_CrearEjercicioG2_Has_Letra(final String letra, final int fila_letra, final int columna_letra) {//f10
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...letra");
        progreso.show();
        System.out.println(" Entrando a CREAR EG2_HAS_LETRA");
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/27wsJSON_Update_Ejercicio2HasLetra.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
//                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    //edt_letra.setText("");
                    // edt_nameEjercicio.setText("");
                    //cont++;
                    System.out.println("CREAR EG2_HAS_LETRA ,cont: " + flag);
                    if (flag == 4) {
                        // Toast.makeText(getContext(), "limpia listas: " + i, Toast.LENGTH_LONG).show();
                        listaidImagenes.clear();
                        listafilaImagen.clear();
                        listacolumnaImagen.clear();
                        listacolumnaLetra.clear();
                        listafilaLetra.clear();
                        listaLetras.clear();
                        System.out.println(" listaidImagenes lista col letra :" + listacolumnaLetra.toString());

                    }


                    //Toast.makeText(getContext(), "Se ha cargado con éxito EHL", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "No se ha cargado con éxito EHL", Toast.LENGTH_LONG).show();
                    System.out.println("error no cargado con exito crearEHL" + response.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se ha podido conectar EHL", Toast.LENGTH_LONG).show();
                String ERROR = "error";
                Log.d(ERROR, error.toString());
                System.out.println("error" + error.toString());
                progreso.hide();
            }
        }) {//enviar para metros a webservice, mediante post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                String EjercicioG2_idEjercicioG2 = String.valueOf(idejercicio);
                //String EjercicioG2_idEjercicioG2 = String.valueOf(10);
                String Letra = String.valueOf(letra);
                String fila_Eg2H_Lg2 = String.valueOf(fila_letra);
                String col_Eg2H_Lge = String.valueOf(columna_letra);

                //System.out.println("letra inicial" + letra_inicial);

                Map<String, String> parametros = new HashMap<>();

                //parametros.put("idEjercicio", idEjercicio);

                parametros.put("idejercicio", EjercicioG2_idEjercicioG2);
                parametros.put("letra", Letra);
                parametros.put("fila", fila_Eg2H_Lg2);
                parametros.put("columna", col_Eg2H_Lge);

                System.out.println("Parametros letra: " + parametros.toString());


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
    // ----------------------------------------------------------------------------------------------


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
