package com.example.asus.dconfo_app.presentation.view.fragment.docente.silabico;

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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * {@link Tipo1SilabicoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo1SilabicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo1SilabicoFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>,
        Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String ruta_Imagen;

    private EditText edt_oracion;
    private EditText edt_cant_silabas;
    private EditText edt_nombre_ejercicio;
    private CircleImageView civ_imagen;
    private Button btn_crear_silabico_t1_doc;
    private RecyclerView rv_silabico_doc_img;

    ArrayList<Integer> listaidImagenes;
    ArrayList<Imagen> listaImagenes;

    private boolean isGalleryChoise = false;

    private Bitmap bitmap;
    private ImageView imgFoto;

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
    String nameDocente = "";
    int idDocente = 0;

    //***************************
    private LinearLayout ll_createImage;
    private LinearLayout ll_createExercice;

    private EditText edt_nameImagen;
    private EditText edt_letraInicial;
    private EditText edt_letraFinal;
    private EditText edt_cantSilabas;

    boolean cargarImagen_boolen = false;
    private Button btn_crearImg;

    int idImagen;
    int flag = 0;

    int id_ejercicio = 0;
    File fileImagen;
    //***************************

    private OnFragmentInteractionListener mListener;

    public Tipo1SilabicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo1SilabicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo1SilabicoFragment newInstance(String param1, String param2) {
        Tipo1SilabicoFragment fragment = new Tipo1SilabicoFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo1_silabico, container, false);

        //******************************************************************************************
        edt_nameImagen = (EditText) view.findViewById(R.id.edt_silabico_name_image);
        edt_letraInicial = (EditText) view.findViewById(R.id.edt_silabico_let_ini);
        edt_letraFinal = (EditText) view.findViewById(R.id.edt_silabico_let_final);
        edt_cantSilabas = (EditText) view.findViewById(R.id.edt_silabico_cant_silabas);

        ll_createImage = (LinearLayout) view.findViewById(R.id.ll_silabico_form_create_img);
        ll_createExercice = (LinearLayout) view.findViewById(R.id.ll_silabico_exe_imgs);

        btn_crearImg = (Button) view.findViewById(R.id.btn_silabico_create_img);
        btn_crearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                crearImagen();
            }
        });
        id_ejercicio = getArguments().getInt("idejercicio");
        //******************************************************************************************

        nameDocente = getArguments().getString("namedocente");

        idDocente = getArguments().getInt("iddocente");

        civ_imagen = (CircleImageView) view.findViewById(R.id.civ_silabico_doc_t1);
        civ_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogOpciones();
            }
        });

        edt_cant_silabas = (EditText) view.findViewById(R.id.edt_silabica_doc_t1_cant_silabas);
        edt_oracion = (EditText) view.findViewById(R.id.edt_silabica_doc_t1_oracion);
        edt_nombre_ejercicio = (EditText) view.findViewById(R.id.edt_silabica_doc_t1_nameejercicio);

        rv_silabico_doc_img = (RecyclerView) view.findViewById(R.id.rv_silabico_doc_img);
        //rv_silabico_doc_img.setLayoutManager(new LinearLayoutManager(getContext()));
        //rv_silabico_doc_img.setHasFixedSize(true);
        rv_silabico_doc_img.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv_silabico_doc_img.setHasFixedSize(true);
        rv_silabico_doc_img.setVisibility(View.GONE);

        listaidImagenes = new ArrayList<>();
        listaImagenes = new ArrayList<>();

        imgFoto = new ImageView(getContext());

        btn_crear_silabico_t1_doc = (Button) view.findViewById(R.id.btn_silabico_doc_t1_crear);
        btn_crear_silabico_t1_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        consultarListaImagenes();

        return view;
    }

    // ----------------------------------------------------------------------------------------------

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String ip = Globals.url;
        String url = "http://" + ip + "/proyecto_dconfo_v1/7wsJSONRegistroTipo1Sil.php";//p12.buena

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {
                    edt_oracion.setText("");
                    edt_cant_silabas.setText("");
                    edt_nombre_ejercicio.setText("");
                    civ_imagen.setImageDrawable(null);
                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
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
                //String idejercicio = edt_CodigoEjercicio.getText().toString();
                //String idejercicio = "";
                String nameejercicio = edt_nombre_ejercicio.getText().toString();
                String iddocente = String.valueOf(idDocente);
                String idactividad = "3";
                String idtipo = "5";
                String imagen = null;
                String de_galeria;
                String rutaImagen = "";
                String letra_inicial = "";
                String letra_final = "";

                if (isGalleryChoise == true) {
                    de_galeria = "si";
                    imagen = convertirImgString(bitmap);
                    System.out.println("dconfo imagen: " + imagen);
                } else {
                    de_galeria = "no";
                }
                String cantidadValida = edt_cant_silabas.getText().toString();
                String oracion = edt_oracion.getText().toString();
                //System.out.println("cantidadvalida"+cantidadValida);
                //System.out.println("oracion"+oracion);

                Map<String, String> parametros = new HashMap<>();
                // parametros.put("idEjercicio", idejercicio);
                parametros.put("nameEjercicio", nameejercicio);
                parametros.put("docente_iddocente", iddocente);
                parametros.put("Actividad_idActividad", idactividad);
                parametros.put("Tipo_idTipo", idtipo);

                parametros.put("letra_inicial_EjercicioG2", letra_inicial);
                parametros.put("letra_final_EjercicioG2", letra_final);

                if (isGalleryChoise == true) {
                    parametros.put("imagen", imagen);
                    parametros.put("de_galeria", de_galeria);
                    parametros.put("rutaImagen", rutaImagen);
                    isGalleryChoise = false;
                } else {
                    parametros.put("imagen", "");
                    parametros.put("de_galeria", de_galeria);
                    parametros.put("rutaImagen", ruta_Imagen);
                }

                parametros.put("cantidadValidaEG1", cantidadValida);
                parametros.put("oracion", oracion);
                // parametros.put("imagen", imagen);

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

    private void consultarListaImagenes() {

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

                    String rutaImagen = listaImagenes.get(rv_silabico_doc_img.
                            getChildAdapterPosition(v)).getRutaImagen();

                    ruta_Imagen = rutaImagen;
                    System.out.println("ruta imagen escogida: " + ruta_Imagen);

                    String nameImagen = listaImagenes.get(rv_silabico_doc_img.
                            getChildAdapterPosition(v)).getNameImagen();

                    int idImagen = listaImagenes.get(rv_silabico_doc_img.
                            getChildAdapterPosition(v)).getIdImagen();

                    cargarImagenWebService(rutaImagen, nameImagen, idImagen);

                    //Toast.makeText(getApplicationContext(), "on click: " + rutaImagen, Toast.LENGTH_LONG).show();
                    System.out.println("on click: " + rutaImagen);
                    //Toast.makeText(getApplicationContext(), "on click: " , Toast.LENGTH_LONG).show();

                }
            });

            rv_silabico_doc_img.setAdapter(imagenUrlAdapter);

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

    private void cargarImagenWebService(String rutaImagen, final String nameImagen, final int idImagen) {

        // String ip = context.getString(R.string.ip);

        String url_lh = Globals.url;

        String urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
        urlImagen = urlImagen.replace(" ", "%20");

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //  if (btn_1Activo) {
                civ_imagen.setBackground(null);
                civ_imagen.setImageBitmap(response);
                rv_silabico_doc_img.setVisibility(View.GONE);
                   /* btn_1Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img1.setText(nameImagen);*/

                int fila = 1;
                int columna = 1;

                   /* ejercicioG2HasImagen.setIdImagen(idImagen);
                    ejercicioG2HasImagen.setFilaImagen(fila);
                    ejercicioG2HasImagen.setColumnaImagen(columna);

                    listaidImagenes.add(idImagen);
                    listafilaImagen.add(fila);
                    listacolumnaImagen.add(columna);*/

                //  }
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

    //**********************************************************************************************

    private void mostrarDialogOpciones() {//part 9
        final CharSequence[] opciones = {"Tomar Foto", "Elegir de Banco de Imágenes", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Elegir de Banco de Imágenes")) {
                    rv_silabico_doc_img.setVisibility(View.VISIBLE);
                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
                        //directamente de galeria
                        isGalleryChoise = true;
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                    if (opciones[i].equals("Tomar Foto")) {
                        abriCamara();//part 10 tomar foto
                        Toast.makeText(getContext(), "Cargar Cámara", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        builder.show();

    }

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
                // cargarImagen();
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

                break;
        }
        bitmap = redimensionarImagen(bitmap, 600, 800);//part 14 redimencionar imágen,guarde en carpeta y BD
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

    private void cargarImagen_() {
        Drawable drawable = imgFoto.getDrawable();
        civ_imagen.setImageDrawable(drawable);

    }

    //**********************************************************************************************
    private void cargarImagen() {
        Drawable drawable = imgFoto.getDrawable();
        // idImagen = listaImagenes.get(listaImagenes.size() - 1).getIdImagen();

        System.out.println("Lista imagenes size CI: " + listaImagenes.size());
        System.out.println("Lista imagenes: " + listaImagenes.get(listaImagenes.size() - 1).getIdImagen());
        //********
        civ_imagen.setBackground(null);
        civ_imagen.setImageBitmap(bitmap);
        rv_silabico_doc_img.setVisibility(View.GONE);
                   /* btn_1Activo = false;
                    rv_tipo1Fonico.setVisibility(View.GONE);
                    txt_id_img1.setText(nameImagen);*/

        int fila = 1;
        int columna = 1;
        cargarImagen_boolen = false;
        ll_createExercice.setVisibility(View.VISIBLE);

        // btn_Tipo1_pic_Ejercicio.setBackground(drawable);
        //imageView_muestra.setBackground(drawable);
    }
    //**********************************************************************************************


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


    //**********************************************************************************************

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

    @Override
    public void onClick(View v) {

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
