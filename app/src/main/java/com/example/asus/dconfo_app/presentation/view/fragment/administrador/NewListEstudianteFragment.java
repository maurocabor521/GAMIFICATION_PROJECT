package com.example.asus.dconfo_app.presentation.view.fragment.administrador;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
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
import com.example.asus.dconfo_app.domain.model.Estudiante;
import com.example.asus.dconfo_app.domain.model.VolleySingleton;
import com.example.asus.dconfo_app.helpers.Globals;
//import com.example.asus.dconfo_app.helpers.POIFSFileSystem;
import com.opencsv.CSVReader;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewListEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewListEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewListEstudianteFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private static final int COD_SELECCIONA = 10;
    private static final String Downloads = "Downloads";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_New_L_Estudiantes;
    private Button btn_Write_New_L_Estudiantes;
    private ListView lv_lista_est;
    private TextView txt_mensaje;
    private Context context;
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;
    File ruta_sd;
    File f;
    File fileEscogido;

    ProgressDialog progreso;

    //******** CONEXIÓN CON WEBSERVICE
    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    StringRequest stringRequest;

    public static NewListEstudianteFragment getInstance() {
        return new NewListEstudianteFragment();
    }

    private OnFragmentInteractionListener mListener;

    public NewListEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewListEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewListEstudianteFragment newInstance(String param1, String param2) {
        NewListEstudianteFragment fragment = new NewListEstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_list_estudiante, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.new_list_estudiante);

        btn_New_L_Estudiantes = (Button) view.findViewById(R.id.btn_New_listEstudiantes);
        btn_Write_New_L_Estudiantes = (Button) view.findViewById(R.id.btn_New_Write_listEstudiantes);

        btn_New_L_Estudiantes.setOnClickListener(this);
        btn_Write_New_L_Estudiantes.setOnClickListener(this);

        lv_lista_est = (ListView) view.findViewById(R.id.lv_NewListEstudiantes);
        txt_mensaje = (TextView) view.findViewById(R.id.txt_mensaje_list_est);
        context = getContext();

        progreso = new ProgressDialog(this.getContext());

        // Código que me comprueba si existe SD y si puedo escribir o no
        String estado = Environment.getExternalStorageState();

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }

        //permison en tiempo de ejecución
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0
            );

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_New_listEstudiantes):

                if (sdDisponible) {

                    try {
                        ruta_sd = Environment.getExternalStorageDirectory();

                        //File f = new File(ruta_sd.getAbsolutePath(), "ficherosd.txt");
                        // File f = new File(ruta_sd.getAbsolutePath(), "archivo_csv.csv");
                      /*  f = new File(Environment.getExternalStorageDirectory().getPath()
                                + "/Download/", "archivo_csv_1.csv");   */

                        f = new File(ruta_sd.getPath()
                                + "/Download/", "archivo_csv_1.csv");
                        //File f1 = fileEscogido;
                        mostrarDialogOpciones();
                        File f1 = fileEscogido;
                        BufferedReader fin =
                                new BufferedReader(
                                        new InputStreamReader(
                                                new FileInputStream(f)));

                        String texto;
                        int cont = 0;
                        if ((texto = fin.readLine()) != null) {
                            cont++;
                            System.out.println("f: " + f.getAbsolutePath());
                            System.out.println("fin.readline: " + texto);
                            // txt_mensaje.setText(texto);
                            Toast.makeText(getContext(), "f: " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
                        }
                        while ((texto = fin.readLine()) == ";") {
                            cont++;
                        }

                        System.out.println("cont: " + cont);
                        String nextLine;
                        while ((nextLine = fin.readLine()) != null) {
                            // nextLine[] is an array of values from the line
                            //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                            // nextLines.add(nextLine);
                            System.out.println("next lines: " + nextLine);
                        }
                        fin.close();
                        // mostrarDialogOpciones();

                    } catch (Exception ex) {
                        Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");
                        txt_mensaje.setText("No hay fichero, sdDisponible: " + sdDisponible + " ," +
                                "sdAccesoEscritura: " + sdAccesoEscritura + " ,RUTA: " + ruta_sd.getAbsolutePath());
                    }

                }
                break;
            case (R.id.btn_New_Write_listEstudiantes):
                if (sdAccesoEscritura && sdDisponible) {
                    try {
                        File ruta_sd = Environment.getExternalStorageDirectory();

                        //f = new File(ruta_sd.getAbsolutePath(), "archivo_csv.csv");
                        f = new File(ruta_sd.getPath()
                                + "/Download/", "archivo_csv_1.csv");

                     /*   f = new File(Environment.getExternalStorageDirectory().getPath()
                                + "/Download/", "archivo_csv_1.csv");*/

                        //+ "/Download/", "archivo_csv.csv");

                        Toast.makeText(getContext(), "f_write: " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();

                        OutputStreamWriter fout =
                                new OutputStreamWriter(
                                        new FileOutputStream(f));
                        OutputStreamWriter fout1 =
                                new OutputStreamWriter(
                                        new FileOutputStream(f));

                        fout.write("juan;");
                        fout.write("caballo;");
                        fout.write("4445555");
                        fout.write("\n");
                        fout.write("laura;");
                        fout.write("lopeza;");
                        fout.write("990000");
                        fout.write("\n");
                        fout.write("andres;");
                        fout.write("cabal;");
                        fout.write("7788888");
                        fout.write("\n");
                        fout.write("lina;");
                        fout.write("juarez;");
                        fout.write("556666");
                        fout.write("\n");
                        fout.write("camilo;");
                        fout.write("marco;");
                        fout.write("33444444");
                        fout.write("\n");
                        fout.write("juana;");
                        fout.write("loca;");
                        fout.write("1111222");
                        fout.write("\n");
                        fout.write("gloria;");
                        fout.write("diaz;");
                        fout.write("77755533");
                        fout.write("\n");
                        fout.write("hugo;");
                        fout.write("bueno;");
                        fout.write("88884433");


                        String tex = fout.toString();
                        int cont = 0;
                        if (tex == "\n") {
                            cont++;
                        }
                        // Toast.makeText(getContext(),"cont: "+cont,Toast.LENGTH_LONG).show();

                        fout.close();
                        fout1.close();

                        String cadena_f = f.toString();
                        //leerArchivo(cadena_f);

                    } catch (Exception ex) {
                        Log.e("Ficheros", "Error al escribir fichero en la tarjeta SD");
                        txt_mensaje.setText("Error al escribir fichero en la tarjeta SD, f :" + f);
                    }
                }

                break;
        }//sw
    }//onclick

    private void mostrarDialogOpciones() {
        //final boolean flag1=false;
        final CharSequence[] opciones = {"Mis archivos", "Elegir de Galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Mis archivos")) {
                    //irCarpeta();
                    //insertContact("andres", "correo@correo");
                    Toast.makeText(getContext(), "RUTA" + Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_LONG).show();
                    Log.e("RUTAS", "RUTA" + Environment.getExternalStorageDirectory().getPath());

                    /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//sirve
                    //Intent intent = new Intent(Intent.DownloadManager.ACTION_VIEW_DOWNLOADS);//prueba
                    //intent.setType("file/*");
                    startActivityForResult(intent, 0);*/

                    Intent intent4 = new Intent(getActivity(), NormalFilePickActivity.class);
                    intent4.putExtra(Constant.MAX_NUMBER, 9);
                    intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[] {"csv", "xls"});
                    startActivityForResult(intent4, 0);



            /*       Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
                    Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
                    chooser.addCategory(Intent.CATEGORY_OPENABLE);*/
                    // chooser.setDataAndType(uri, "*/*");
                /*    startActivity(chooser);
                    try {
                        //startActivityForResult(chooser, 0);
                        startActivityForResult(Intent.createChooser(chooser, "Select a File to Upload"), 0);

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                    }*/


                    // openFolder();//sirve

                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione"), COD_SELECCIONA);
                        //cont = 1;
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });

        builder.show();
        //return flag1;
    }

    public void openFolder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + File.separator + "myFolder" + File.separator);
        intent.setDataAndType(uri, "text/csv");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0: {
                //what you want to do
                //file = new File(uri.getPath());
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);

                if(list.size() == 1) {

                    NormalFile file = list.get(0);

                    fileEscogido = new File(file.getPath());
                    BufferedReader fin1 =
                            null;
                    try {
                        fin1 = new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(fileEscogido)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    String texto;
                    int cont = 0;
                    try {
                        if ((texto = fin1.readLine()) != null) {
                            cont++;
                            System.out.println("fileEscogido: " + fileEscogido.getAbsolutePath());
                            System.out.println("fin1.readline: " + texto);
                            //txt_mensaje.setText(texto);
                            cuentaSaltos(fileEscogido.toString());
                            if (texto.equals("\n")) {
                                cont++;
                            }
                            //Toast.makeText(getContext(), "fileEscogido: " + fileEscogido.getAbsolutePath(), Toast.LENGTH_LONG).show();
                            //Toast.makeText(getContext(), "cont_: " + cont, Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
              /*  System.out.println("File_Escogido : "+fileEscogido);
                Log.i("info","uri.data"+uri);
                Toast.makeText(getContext(), "File Escogido : uri -> "+fileEscogido, Toast.LENGTH_LONG).show();*/

            }
        }
    }

    public void cuentaSaltos(String archivo) {
        //String cadena = "MetroBikeShare_2016_Q3_trips.csv";
        String cadena = archivo;
        String elemento = null;
        boolean seguir = true;
        Scanner entrada = null;
        ArrayList<String> lisEstudiantes = new ArrayList<>();
        int cont = 0;

        try {
            entrada = new Scanner(new File(cadena));
        } catch (FileNotFoundException fnE) {
            System.out.println(fnE.getMessage());
            seguir = false;
        }
        if (seguir) {
            //entrada.nextLine();
            while (entrada.hasNext()) {
                //cont++;
                elemento = entrada.next();
                lisEstudiantes.add(elemento);
                //Toast.makeText(getContext(), "elemento: " + elemento, Toast.LENGTH_LONG).show();

                //  String palabra = "sql";
                //  String texto = "lenguaje sql";
                boolean resultado = elemento.contains(";");
                //ContarCharUnicos(elemento);

                //cuentcv(elemento);
                if (resultado) {
                    //  System.out.println("palabra encontrada");
                    cont++;
                } else {
                    System.out.println("palabra no encontrada");
                }
            }
            // cuentcv("caballo;;;");
            cuentcv(lisEstudiantes);
            ArrayAdapter<String> adapterListView = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_multiple_choice, lisEstudiantes);
            lv_lista_est.setAdapter(adapterListView);
            System.out.println("ListEstudiantes: " + lisEstudiantes);
            //System.out.println("cont: " + cont);
            //Toast.makeText(getContext(), "cont: " + cont, Toast.LENGTH_LONG).show();
        }

    }//pruebas

    public void cuentcv(ArrayList<String> cadena) {

        char[] Arraycadena;
        //char caracter;
        char caracter = ';';
        char caracter1 = ',';


        //Arraycadena = cadena.toCharArray();
        String[] arrayCadena = cadena.toArray(new String[cadena.size()]);


       /* char[] caracteres = new char[cadena.length()];
        int[] cuantasVeces = new int[cadena.length()];     */
        char[] caracteres = new char[cadena.size()];
        int[] cuantasVeces = new int[cadena.size()];
        int cont = 0;

        //  for(int i =0;i<Arraycadena.length;i++){
        //caracter = Arraycadena[i];
        // caracteres[i] = caracter;
        // for(int j = i; j < Arraycadena.length; j++)   {

        // for (int j = 0; j < Arraycadena.length; j++) {

        String elem = "";
        ArrayList<String> listElem = new ArrayList<>();
        ArrayList<String> listCad = new ArrayList<>();
        for (int j = 0; j < cadena.size(); j++) {
            // if (Arraycadena[j] == caracter) {
            char[] array1;
            array1 = cadena.get(j).toCharArray();
            // if (cadena.get(j).contentEquals(";") ) {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] == caracter) {
                    // cuantasVeces[j]++;
                    // Arraycadena[j] = ' ';
                    listElem.add(elem);
                    elem = "";
                    cont++;
                } else {

                    elem = elem + array1[i];


                }
            }//2do for
            listCad.add(cadena.get(j));
            separaElem(cadena.get(j));
            //System.out.println(" cadena : " + j + ": j : " + cadena.get(j));


            // if(caracteres[j] != ' ')
            // System.out.println(caracteres[i] +" "+cuantasVeces[i]+" veces.");
        }//1er for

      /*  System.out.println(" cadena lst : " + listCad.get(0));
        System.out.println(" elem : " + elem);
        System.out.println(" lstelems : " + listElem);
        System.out.println(Arrays.toString(arrayCadena));
        System.out.println(cont + " veces");*/


    }

    public void separaElem(String cadena) {
        char[] Arraycadena;
        Arraycadena = cadena.toCharArray();
        //String[] cadenaArray = new String[] {cadena};
        String[] cadenaArray = cadena.split("");
        char caracter = ';';
        int cont = 0;
        String elem = "";
        String elem1 = "";
        ArrayList<String> listElem = new ArrayList<>();
        ArrayList<String> listCad = new ArrayList<>();
        Estudiante estudiante = new Estudiante();

        for (int i = 0; i < Arraycadena.length; i++) {
            if (Arraycadena[i] == caracter) {
                // cuantasVeces[j]++;
                // Arraycadena[j] = ' ';
                listElem.add(elem);



                System.out.println(" el elemento : " + elem);
                elem = "";
                cont++;
                if (cont == 3 && (Arraycadena[i] < Arraycadena.length)) {
                    elem = elem + Arraycadena[i];
                }
                if (cont == 3 && (Arraycadena[i] == (Arraycadena.length - 1))) {
                    listElem.add(elem);
                }
            } else {
                elem = elem + Arraycadena[i];
                /*if (cont == 3 && Arraycadena[i] < Arraycadena.length) {
                    elem = elem + Arraycadena[i];
                }*/
                //estudiante.setIdestudiante(Arraycadena[i]);
            }

        }//2do for


        System.out.println(" separa elems : " + listElem);
        creaEstudiante(listElem);//***********************************************
        System.out.println(" elemento1 : " + elem);
        System.out.println(" contador : " + cont);
    }

    public Estudiante creaEstudiante(ArrayList<String> lista) {
        Estudiante estudiante = null;
        estudiante = new Estudiante();
        estudiante.setIdestudiante(Integer.parseInt(lista.get(0).toString()));
        estudiante.setNameestudiante(lista.get(1).toString());
        estudiante.setDniestudiante(Integer.parseInt(lista.get(2).toString()));
        System.out.println("estudiante id: " + estudiante.getIdestudiante());
        System.out.println("estudiante nombre: " + estudiante.getNameestudiante());
        System.out.println("lista enviada: " + lista.size());
        cargarWebServiceEstudiante(estudiante);
        return estudiante;
    }

    private void cargarWebServiceEstudiante(final Estudiante estudiante) {

        progreso.setMessage("Cargando...");
        progreso.show();
        String url_lh=Globals.url;
        String url =
                // "http://192.168.0.13/proyecto_dconfo/wsJSONCrearEstudiante.php?";
                "http://"+url_lh+"/proyecto_dconfo_v1/wsJSONCrearEstudiante.php?";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//recibe respuesta del webservice,cuando esta correcto
                progreso.hide();
                if (response.trim().equalsIgnoreCase("registra")) {

                    Toast.makeText(getContext(), "Se ha cargado con éxito", Toast.LENGTH_LONG).show();
                    System.out.println("cargado con éxito estudiante en BBDD: " );
                } else {
                    System.out.println("carga fallida estudiante en BBDD: " );
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
                String idestudiante = estudiante.getIdestudiante().toString();
                String nameestudiante = estudiante.getNameestudiante().toString();
                String dniestudiante = estudiante.getDniestudiante().toString();
                String acudienteestudiante = "N/A";



                Map<String, String> parametros = new HashMap<>();
                parametros.put("idestudiante", idestudiante);
                parametros.put("nameestudiante", nameestudiante);
                parametros.put("dniestudiante", dniestudiante);
                parametros.put("acudienteestudiante", acudienteestudiante);


                return parametros;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(stringRequest);//p21

    }//cargarwebserviceestudiante



  /*  public static int ContarCharUnicos(String input) {
        boolean[] comprobarChar = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < input.length(); i++) {
            comprobarChar[input.charAt(i)] = true;
        }

        int cuenta = 0;
        for (int i = 0; i < comprobarChar.length; i++) {
            if (comprobarChar[i] == true) {
                cuenta++;
            }
        }
        System.out.println("cuenta: " + cuenta);
        return cuenta;

    }*/




    public void insertContact(String name, String email) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void irCarpeta() {
        File root = new File(Environment.getExternalStorageDirectory().getPath()
                + "/Download/");
        Uri uri = Uri.fromFile(root);

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setData(uri);
        // startActivityForResult(intent, 1);
    }


    public void leerArchivo(String archivo) {
        //String csvFile = "archivo.csv";
        String csvFile = "archivo";
        BufferedReader br = null;
        String line = "";
//Se define separador ","
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                //Imprime datos.
                System.out.println(datos[0] + ", " + datos[1] + ", " + datos[2] + ", " + datos[3] + ", " + datos[4] + ", " + datos[5]);
                Toast.makeText(getContext(), "Datos: " + datos, Toast.LENGTH_LONG).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
