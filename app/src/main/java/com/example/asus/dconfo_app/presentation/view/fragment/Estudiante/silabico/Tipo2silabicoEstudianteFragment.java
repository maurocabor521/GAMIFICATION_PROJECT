package com.example.asus.dconfo_app.presentation.view.fragment.Estudiante.silabico;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tipo2silabicoEstudianteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tipo2silabicoEstudianteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tipo2silabicoEstudianteFragment extends Fragment
        implements Response.Listener<JSONObject>, Response.ErrorListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CircleImageView cv_est_st2_c1f1;
    private CircleImageView cv_est_st2_c1f2;
    private CircleImageView cv_est_st2_c1f3;
    private CircleImageView cv_est_st2_c1f4;

    private CircleImageView cv_est_st2_c2f1;
    private CircleImageView cv_est_st2_c2f2;
    private CircleImageView cv_est_st2_c2f3;
    private CircleImageView cv_est_st2_c2f4;


    private TextView txt_resultado;

    private LinearLayout ll_cv_c1f1;
    private LinearLayout ll_cv_c1f2;
    private LinearLayout ll_cv_c1f3;
    private LinearLayout ll_cv_c1f4;

    private LinearLayout ll_cv_c2f1;
    private LinearLayout ll_cv_c2f2;
    private LinearLayout ll_cv_c2f3;
    private LinearLayout ll_cv_c2f4;

    private Button btn_enviar_est_st2;

    private int idImagen1;
    private int idImagen2;
    private int idImagen3;
    private int idImagen4;
    private int idImagen5;
    private int idImagen6;
    private int idImagen7;
    private int idImagen8;

    private int filejercicio1;
    private int filejercicio2;
    private int filejercicio3;
    private int filejercicio4;

    private int colejercicio1;
    private int colejercicio2;
    private int colejercicio3;
    private int colejercicio4;

    private String letrac1f1;
    private String letrac1f2;
    private String letrac1f3;
    private String letrac1f4;

    private String usuario;

    private boolean cv_c1f1_isactived = false;
    private boolean cv_c1f2_isactived = false;
    private boolean cv_c1f3_isactived = false;
    private boolean cv_c1f4_isactived = false;

    private boolean cv_c1f1_desactivado = false;
    private boolean cv_c1f2_desactivado = false;
    private boolean cv_c1f3_desactivado = false;
    private boolean cv_c1f4_desactivado = false;


    private boolean cv_c2f1_isactived = false;
    private boolean cv_c2f2_isactived = false;
    private boolean cv_c2f3_isactived = false;
    private boolean cv_c2f4_isactived = false;

    private boolean cv_c2f1_desactivado = false;
    private boolean cv_c2f2_desactivado = false;
    private boolean cv_c2f3_desactivado = false;
    private boolean cv_c2f4_desactivado = false;

    private boolean col_imgs = false;
    private boolean col_letras = false;
    private boolean parejaCreada = false;
    private int contadorParejas = 0;

    private int contadorColImgs = 0;
    private int contadorColLetras = 0;

    private boolean pareja1 = false;
    private boolean pareja2 = false;
    private boolean pareja3 = false;
    private boolean pareja4 = false;

    private boolean resPareja1 = false;
    private boolean resPareja2 = false;
    private boolean resPareja3 = false;
    private boolean resPareja4 = false;

    ArrayList<Integer> listaIdImagens;
    ArrayList<Integer> listafilImagenes;
    ArrayList<Integer> listacolImagenes;

    ArrayList<Integer> listaIdImagenes;

    ArrayList<Integer> pareja_1;
    ArrayList<Integer> pareja_2;
    ArrayList<Integer> pareja_3;
    ArrayList<Integer> pareja_4;

    private String letraInicialc1f1;
    private String letraFinalc1f1;

    private String letraInicialc1f2;
    private String letraFinalc1f2;

    private String letraInicialc1f3;
    private String letraFinalc1f3;

    private String letraInicialc1f4;
    private String letraFinalc1f4;

    private String letraInicialc2f1;
    private String letraFinalc2f1;

    private String letraInicialc2f2;
    private String letraFinalc2f2;

    private String letraInicialc2f3;
    private String letraFinalc2f3;

    private String letraInicialc2f4;
    private String letraFinalc2f4;


    private String letraInicial;
    private String letraFinal;

    private String letrainicial;
    private String letrafinal;


    ArrayList<String> listaLetras;
    ArrayList<Integer> listafilLetras;
    ArrayList<Integer> listacolLetras;

    private Imagen imagen;
    String urlImagen;
    int f = -1;
    int iddeber;

    StringRequest stringRequest;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public Tipo2silabicoEstudianteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tipo2silabicoEstudianteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Tipo2silabicoEstudianteFragment newInstance(String param1, String param2) {
        Tipo2silabicoEstudianteFragment fragment = new Tipo2silabicoEstudianteFragment();
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
        View view = inflater.inflate(R.layout.fragment_tipo2silabico_estudiante, container, false);

        cv_est_st2_c1f1 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c1_f1);
        cv_est_st2_c1f2 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c1_f2);
        cv_est_st2_c1f3 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c1_f3);
        cv_est_st2_c1f4 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c1_f4);

        cv_est_st2_c1f1.setOnClickListener(this);
        cv_est_st2_c1f2.setOnClickListener(this);
        cv_est_st2_c1f3.setOnClickListener(this);
        cv_est_st2_c1f4.setOnClickListener(this);

        cv_est_st2_c2f1 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c2_f1);
        cv_est_st2_c2f2 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c2_f2);
        cv_est_st2_c2f3 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c2_f3);
        cv_est_st2_c2f4 = (CircleImageView) view.findViewById(R.id.iv_estudiante_sil2_c2_f4);

        cv_est_st2_c2f1.setOnClickListener(this);
        cv_est_st2_c2f2.setOnClickListener(this);
        cv_est_st2_c2f3.setOnClickListener(this);
        cv_est_st2_c2f4.setOnClickListener(this);

        ll_cv_c1f1 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c1_f1);
        ll_cv_c1f2 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c1_f2);
        ll_cv_c1f3 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c1_f3);
        ll_cv_c1f4 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c1_f4);

        ll_cv_c2f1 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c2_f1);
        ll_cv_c2f2 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c2_f2);
        ll_cv_c2f3 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c2_f3);
        ll_cv_c2f4 = (LinearLayout) view.findViewById(R.id.ll_estudiante_sil2_c2_f4);

        btn_enviar_est_st2 = (Button) view.findViewById(R.id.btn_estudiante_sil2_enviar);
        btn_enviar_est_st2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarResultado();
                reiniciarVariables();
            }
        });

        pareja_1 = new ArrayList<>();
        pareja_2 = new ArrayList<>();
        pareja_3 = new ArrayList<>();
        pareja_4 = new ArrayList<>();

        listaIdImagenes = new ArrayList<>();

        usuario = getArguments().getString("usuario");

        iddeber = getArguments().getInt("idesthasdeber");
        System.out.println("iddeber: " + iddeber);

        letrainicial = getArguments().getString("letrainicial");
        System.out.println("letrainicial: " + letrainicial);

        letrafinal = getArguments().getString("letrafinal");
        System.out.println("letrafinal: " + letrafinal);

        idImagen1 = getArguments().getInt("idejercicio1");
        listaIdImagenes.add(idImagen1);
        System.out.println("id imagen 1: " + idImagen1);
        idImagen2 = getArguments().getInt("idejercicio2");
        listaIdImagenes.add(idImagen2);
        System.out.println("id imagen 2: " + idImagen2);
        idImagen3 = getArguments().getInt("idejercicio3");
        listaIdImagenes.add(idImagen3);
        System.out.println("id imagen 3: " + idImagen3);
        idImagen4 = getArguments().getInt("idejercicio4");
        listaIdImagenes.add(idImagen4);
        System.out.println("id imagen 4: " + idImagen4);
        idImagen5 = getArguments().getInt("idejercicio5");
        listaIdImagenes.add(idImagen5);
        System.out.println("id imagen 5: " + idImagen5);
        idImagen6 = getArguments().getInt("idejercicio6");
        listaIdImagenes.add(idImagen6);
        System.out.println("id imagen 6: " + idImagen6);
        idImagen7 = getArguments().getInt("idejercicio7");
        listaIdImagenes.add(idImagen7);
        System.out.println("id imagen 7: " + idImagen7);
        idImagen8 = getArguments().getInt("idejercicio8");
        listaIdImagenes.add(idImagen8);
        System.out.println("id imagen 8: " + idImagen8);

        // listaIdImagens
        listaIdImagens = new ArrayList<>();
        listacolImagenes = new ArrayList<>();
        listafilImagenes = new ArrayList<>();

        listaIdImagens.add(idImagen1);
        listaIdImagens.add(idImagen2);
        listaIdImagens.add(idImagen3);
        listaIdImagens.add(idImagen4);
        listaIdImagens.add(idImagen5);
        listaIdImagens.add(idImagen6);
        listaIdImagens.add(idImagen7);
        listaIdImagens.add(idImagen8);

        txt_resultado = (TextView) view.findViewById(R.id.txt_resultado_sil2);

        llamarWebService();

        return view;
    }

    private void reiniciarVariables() {
        cv_c1f1_isactived = false;
        cv_c1f2_isactived = false;
        cv_c1f3_isactived = false;
        cv_c1f4_isactived = false;
        cv_c2f1_isactived = false;
        cv_c2f2_isactived = false;
        cv_c2f3_isactived = false;
        cv_c2f4_isactived = false;

        cv_c1f1_desactivado = false;
        cv_c1f2_desactivado = false;
        cv_c1f3_desactivado = false;
        cv_c1f4_desactivado = false;
        cv_c2f1_desactivado = false;
        cv_c2f2_desactivado = false;
        cv_c2f3_desactivado = false;
        cv_c2f4_desactivado = false;

        pareja1 = false;
        pareja2 = false;
        pareja3 = false;
        pareja4 = false;
    }

    //----------------------------------------------------------------------------------------------

    public void onClick(View v) {//f2
        switch (v.getId()) {
            case R.id.iv_estudiante_sil2_c1_f1:
                if (cv_c1f1_isactived == false && cv_c1f1_desactivado == false) {

                    cv_c1f1_isactived = true;
                    col_imgs = true;
                    contadorColImgs++;

                    cv_c1f2_isactived = false;
                    cv_c1f3_isactived = false;
                    cv_c1f4_isactived = false;

                    ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));

                    verificaParejas();

                }
                System.out.println("imagen 1");
                //Drawable drawable=;
                //ll_cv_c1f1.setBackgroundColor(Color.WHITE);
                // Toast.makeText(getContext(), "imagen c1f1 :" + cv_c1f1_isactived, Toast.LENGTH_SHORT).show();
                System.out.println("c1f1 cv_c1f3_isactived: " + cv_c1f3_isactived);
                break;
            case R.id.iv_estudiante_sil2_c1_f2:

                if (cv_c1f2_isactived == false && cv_c1f2_desactivado == false) {
                    cv_c1f2_isactived = true;
                    col_imgs = true;
                    contadorColImgs++;
                    ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));
                  /*  ll_cv_c1f2.setBackgroundColor(Color.WHITE);

                    ll_cv_c1f1.setBackgroundColor(Color.RED);
                    ll_cv_c1f3.setBackgroundColor(Color.RED);
                    ll_cv_c1f4.setBackgroundColor(Color.RED);*/

                    cv_c1f1_isactived = false;
                    cv_c1f3_isactived = false;
                    cv_c1f4_isactived = false;

                    verificaParejas();

                }
                System.out.println("imagen 2");
                System.out.println("c1f2 cv_c1f3_isactived: " + cv_c1f3_isactived);
                break;
            case R.id.iv_estudiante_sil2_c1_f3:
                if (cv_c1f3_isactived == false && cv_c1f3_desactivado == false) {
                    System.out.println("1 cv_c1f3_isactived: " + cv_c1f3_isactived);
                    cv_c1f3_isactived = true;
                    System.out.println("2 cv_c1f3_isactived: " + cv_c1f3_isactived);
                    col_imgs = true;
                    contadorColImgs++;
                    ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));
                    cv_c1f1_isactived = false;
                    cv_c1f2_isactived = false;
                    cv_c1f4_isactived = false;

                    verificaParejas();
                }
                System.out.println("imagen 3");
                System.out.println("3 cv_c1f3_isactived: " + cv_c1f3_isactived);
                break;
            case R.id.iv_estudiante_sil2_c1_f4:

                if (cv_c1f4_isactived == false && cv_c1f4_desactivado == false) {
                    cv_c1f4_isactived = true;
                    col_imgs = true;
                    contadorColImgs++;
                    ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));
                  /*  ll_cv_c1f4.setBackgroundColor(Color.WHITE);

                    ll_cv_c1f1.setBackgroundColor(Color.RED);
                    ll_cv_c1f3.setBackgroundColor(Color.RED);
                    ll_cv_c1f2.setBackgroundColor(Color.RED);*/

                    cv_c1f1_isactived = false;
                    cv_c1f3_isactived = false;
                    cv_c1f2_isactived = false;

                    verificaParejas();
                }
                System.out.println("imagen 4");
                System.out.println("c1f4 cv_c1f3_isactived: " + cv_c1f3_isactived);
                break;
            case R.id.iv_estudiante_sil2_c2_f1:
                if (cv_c2f1_isactived == false && cv_c2f1_desactivado == false) {
                    cv_c2f1_isactived = true;
                    col_letras = true;
                    contadorColLetras++;

                    cv_c2f2_isactived = false;
                    cv_c2f3_isactived = false;
                    cv_c2f4_isactived = false;

                    ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));
                    //col_imgs = false;

                    verificaParejas();
                    System.out.println("imagen 5");
                    System.out.println("c2f1 cv_c1f3_isactived: " + cv_c1f3_isactived);
                }
                break;
            case R.id.iv_estudiante_sil2_c2_f2:
                if (cv_c2f2_isactived == false && cv_c2f2_desactivado == false) {
                    cv_c2f2_isactived = true;
                    col_letras = true;
                    contadorColLetras++;

                    cv_c2f1_isactived = false;
                    cv_c2f3_isactived = false;
                    cv_c2f4_isactived = false;
                    //col_imgs = false;
                    ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));

                    verificaParejas();
                    System.out.println("imagen 6");
                }
                System.out.println("c2f2 cv_c1f3_isactived: " + cv_c1f3_isactived);
                break;
            case R.id.iv_estudiante_sil2_c2_f3:
                if (cv_c2f3_isactived == false && cv_c2f3_desactivado == false) {
                    cv_c2f3_isactived = true;
                    col_letras = true;
                    contadorColLetras++;

                    cv_c2f1_isactived = false;
                    cv_c2f2_isactived = false;
                    cv_c2f4_isactived = false;
                    //col_imgs = false;
                    ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));

                    verificaParejas();
                    System.out.println("imagen 7");
                }
                break;
            case R.id.iv_estudiante_sil2_c2_f4:
                if (cv_c2f4_isactived == false && cv_c2f4_desactivado == false) {
                    cv_c2f4_isactived = true;
                    col_letras = true;
                    contadorColLetras++;

                    cv_c2f1_isactived = false;
                    cv_c2f2_isactived = false;
                    cv_c2f3_isactived = false;
                    //col_imgs = false;
                    ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.bb_inActiveBottomBarItemColor));

                    verificaParejas();
                    System.out.println("imagen 8");
                }
                break;
        }
    }

    //----------------------------------------------------------------------------------------------


    private void verificaParejas() {
        if ((col_imgs && col_letras) == true) {

            if ((contadorColImgs == 1 && contadorColLetras == 1)) {
                System.out.println(" VALIDO --- cont imagenes :" + contadorColImgs + " cont letras :" + contadorColLetras);
                contadorColLetras--;
                contadorColImgs--;
                col_letras = false;
                col_imgs = false;
                System.out.println(" parejas 1: " + pareja1 + " parejas 2: " + pareja2 + " parejas 3: " + pareja3);
                crearParejas();

            } else {
                System.out.println("NO VALIDO --- cont imagenes :" + contadorColImgs + " cont letras :" + contadorColLetras);
            }
        } else if (contadorColImgs > 1) {
            System.out.println("ACCIÓN NO VALIDA img");
            contadorColImgs--;
        }
        if (contadorColLetras > 1) {
            System.out.println("ACCIÓN NO VALIDA letra");
            contadorColLetras--;
        }
    }

    private void crearParejas() {


        if (pareja1 == false) {//---------------------------------------pareja 1
            //**************************************************** c1
            if (cv_c1f1_isactived && cv_c2f1_isactived) {

                pareja_1.add(1);
                System.out.println("pareja 1: " + pareja_1.toString());
                pareja1 = true;
                cv_c1f1_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f1_isactived && cv_c2f2_isactived) {
                pareja_1.add(2);
                pareja1 = true;
                cv_c1f1_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f1_isactived && cv_c2f3_isactived) {
                pareja_1.add(3);
                pareja1 = true;
                cv_c1f1_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f1_isactived && cv_c2f4_isactived) {
                pareja_1.add(4);
                pareja1 = true;
                cv_c1f1_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f2_isactived && cv_c2f1_isactived) {// **************************************************** c2

                pareja_1.add(5);
                pareja1 = true;
                cv_c1f2_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f2_isactived && cv_c2f2_isactived) {
                pareja_1.add(6);
                pareja1 = true;
                cv_c1f2_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f2_isactived && cv_c2f3_isactived) {
                pareja_1.add(7);
                pareja1 = true;
                cv_c1f2_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f2_isactived && cv_c2f4_isactived) {
                pareja_1.add(8);
                pareja1 = true;
                cv_c1f2_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f3_isactived && cv_c2f1_isactived) {// **************************************************** c3

                pareja_1.add(9);
                pareja1 = true;
                cv_c1f3_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f3_isactived && cv_c2f2_isactived) {
                pareja_1.add(10);
                pareja1 = true;
                cv_c1f3_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f3_isactived && cv_c2f3_isactived) {
                pareja_1.add(11);
                pareja1 = true;
                cv_c1f3_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f3_isactived && cv_c2f4_isactived) {
                pareja_1.add(12);
                pareja1 = true;
                cv_c1f3_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
            } else if (cv_c1f4_isactived && cv_c2f1_isactived) {// **************************************************** c4

                pareja_1.add(13);
                pareja1 = true;
                cv_c1f4_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f4_isactived && cv_c2f2_isactived) {
                pareja_1.add(14);
                pareja1 = true;
                cv_c1f4_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f4_isactived && cv_c2f3_isactived) {
                pareja_1.add(15);
                pareja1 = true;
                cv_c1f4_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));

            } else if (cv_c1f4_isactived && cv_c2f4_isactived) {
                pareja_1.add(16);
                pareja1 = true;
                cv_c1f4_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.editTextColorWhite));
            }
            //****************************************************
        }//***************************************************************************if pareja 1 false


        else if (pareja1 == true && pareja2 == false) {//---------------------------------------pareja 2
            //**************************************************** c1
            if (cv_c1f1_isactived && cv_c2f1_isactived) {

                pareja_2.add(1);
                System.out.println("pareja 1: " + pareja_1.toString());
                pareja2 = true;
                cv_c1f1_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f1_isactived && cv_c2f2_isactived) {
                pareja_2.add(2);
                pareja2 = true;
                cv_c1f1_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f1_isactived && cv_c2f3_isactived) {
                pareja_2.add(3);
                pareja2 = true;
                cv_c1f1_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f1_isactived && cv_c2f4_isactived) {
                pareja_2.add(4);
                pareja2 = true;
                cv_c1f1_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f2_isactived && cv_c2f1_isactived) {// **************************************************** c2

                pareja_2.add(5);
                pareja2 = true;
                cv_c1f2_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f2_isactived && cv_c2f2_isactived) {
                pareja_2.add(6);
                pareja2 = true;
                cv_c1f2_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f2_isactived && cv_c2f3_isactived) {
                pareja_2.add(7);
                pareja2 = true;
                cv_c1f2_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f2_isactived && cv_c2f4_isactived) {
                pareja_2.add(8);
                pareja2 = true;
                cv_c1f2_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f3_isactived && cv_c2f1_isactived) {// **************************************************** c3

                pareja_2.add(9);
                pareja2 = true;
                cv_c1f3_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f3_isactived && cv_c2f2_isactived) {
                pareja_2.add(10);
                pareja2 = true;
                cv_c1f3_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f3_isactived && cv_c2f3_isactived) {
                pareja_2.add(11);
                pareja2 = true;
                cv_c1f3_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f3_isactived && cv_c2f4_isactived) {
                pareja_2.add(12);
                pareja2 = true;
                cv_c1f3_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.green));
            } else if (cv_c1f4_isactived && cv_c2f1_isactived) {// **************************************************** c4

                pareja_2.add(13);
                pareja2 = true;
                cv_c1f4_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f4_isactived && cv_c2f2_isactived) {
                pareja_2.add(14);
                pareja2 = true;
                cv_c1f4_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f4_isactived && cv_c2f3_isactived) {
                pareja_2.add(15);
                pareja2 = true;
                cv_c1f4_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.green));

            } else if (cv_c1f4_isactived && cv_c2f4_isactived) {
                pareja_2.add(16);
                pareja2 = true;
                cv_c1f4_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.green));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.green));
            }
            //****************************************************
        }//***************************************************************************if pareja 2 false

        else if (pareja1 == true && pareja2 == true && pareja3 == false) {//---------------------------------------pareja 3
            //**************************************************** c1
            if (cv_c1f1_isactived && cv_c2f1_isactived) {

                pareja_3.add(1);
                System.out.println("pareja 3: " + pareja_3.toString());
                pareja3 = true;
                cv_c1f1_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f1_isactived && cv_c2f2_isactived) {
                pareja_3.add(2);
                pareja3 = true;
                cv_c1f1_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f1_isactived && cv_c2f3_isactived) {
                pareja_3.add(3);
                pareja3 = true;
                cv_c1f1_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f1_isactived && cv_c2f4_isactived) {
                pareja_3.add(4);
                pareja3 = true;
                cv_c1f1_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f2_isactived && cv_c2f1_isactived) {// **************************************************** c2

                pareja_3.add(5);
                pareja3 = true;
                cv_c1f2_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f2_isactived && cv_c2f2_isactived) {
                pareja_3.add(6);
                pareja3 = true;
                cv_c1f2_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f2_isactived && cv_c2f3_isactived) {
                pareja_3.add(7);
                pareja3 = true;
                cv_c1f2_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f2_isactived && cv_c2f4_isactived) {
                pareja_3.add(8);
                pareja3 = true;
                cv_c1f2_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f3_isactived && cv_c2f1_isactived) {// **************************************************** c3

                pareja_3.add(9);
                pareja3 = true;
                cv_c1f3_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f3_isactived && cv_c2f2_isactived) {
                pareja_3.add(10);
                pareja3 = true;
                cv_c1f3_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f3_isactived && cv_c2f3_isactived) {
                pareja_3.add(11);
                pareja3 = true;
                cv_c1f3_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f3_isactived && cv_c2f4_isactived) {
                pareja_3.add(12);
                pareja3 = true;
                cv_c1f3_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            } else if (cv_c1f4_isactived && cv_c2f1_isactived) {// **************************************************** c4

                pareja_3.add(13);
                pareja3 = true;
                cv_c1f4_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f4_isactived && cv_c2f2_isactived) {
                pareja_3.add(14);
                pareja3 = true;
                cv_c1f4_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f4_isactived && cv_c2f3_isactived) {
                pareja_3.add(15);
                pareja3 = true;
                cv_c1f4_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            } else if (cv_c1f4_isactived && cv_c2f4_isactived) {
                pareja_3.add(16);
                pareja3 = true;
                cv_c1f4_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
            //****************************************************
        }//***************************************************************************if pareja 3 false


        else if (pareja1 == true && pareja2 == true && pareja3 == true && pareja4 == false) {//---------------------------------------pareja 4
            //**************************************************** c1
            if (cv_c1f1_isactived && cv_c2f1_isactived) {

                pareja_4.add(1);
                System.out.println("pareja 4: " + pareja_4.toString());
                pareja4 = true;
                cv_c1f1_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f1_isactived && cv_c2f2_isactived) {
                pareja_4.add(2);
                pareja4 = true;
                cv_c1f1_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f1_isactived && cv_c2f3_isactived) {
                pareja_4.add(3);
                pareja4 = true;
                cv_c1f1_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f1_isactived && cv_c2f4_isactived) {
                pareja_4.add(4);
                pareja4 = true;
                cv_c1f1_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f2_isactived && cv_c2f1_isactived) {// **************************************************** c2

                pareja_4.add(5);
                pareja4 = true;
                cv_c1f2_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f2_isactived && cv_c2f2_isactived) {
                pareja_4.add(6);
                pareja4 = true;
                cv_c1f2_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f2_isactived && cv_c2f3_isactived) {
                pareja_4.add(7);
                pareja4 = true;
                cv_c1f2_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f2_isactived && cv_c2f4_isactived) {
                pareja_4.add(8);
                pareja4 = true;
                cv_c1f2_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f3_isactived && cv_c2f1_isactived) {// **************************************************** c3

                pareja_4.add(9);
                pareja4 = true;
                cv_c1f3_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f3_isactived && cv_c2f2_isactived) {
                pareja_4.add(10);
                pareja4 = true;
                cv_c1f3_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f3_isactived && cv_c2f3_isactived) {
                pareja_4.add(11);
                pareja4 = true;
                cv_c1f3_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f3_isactived && cv_c2f4_isactived) {
                pareja_4.add(12);
                pareja4 = true;
                cv_c1f3_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } else if (cv_c1f4_isactived && cv_c2f1_isactived) {// **************************************************** c4

                pareja_4.add(13);
                pareja4 = true;
                cv_c1f4_desactivado = true;
                cv_c2f1_desactivado = true;

                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f4_isactived && cv_c2f2_isactived) {
                pareja_4.add(14);
                pareja4 = true;
                cv_c1f4_desactivado = true;
                cv_c2f2_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f4_isactived && cv_c2f3_isactived) {
                pareja_4.add(15);
                pareja4 = true;
                cv_c1f4_desactivado = true;
                cv_c2f3_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            } else if (cv_c1f4_isactived && cv_c2f4_isactived) {
                pareja_4.add(16);
                pareja4 = true;
                cv_c1f4_desactivado = true;
                cv_c2f4_desactivado = true;
                ll_cv_c1f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ll_cv_c2f4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            //****************************************************
        }//***************************************************************************if pareja 4 false


    }//******************crear parejas

    //**********************************************************************************************

    //**********************************************************************************************

    private void verificarResultado() {
        if (pareja4 == true) {
            System.out.println("pareja 1: " + pareja_1.toString() + " pareja 2: " + pareja_2.toString() +
                    " pareja 3: " + pareja_3.toString() + " pareja 4: " + pareja_4.toString());

            System.out.println(
                    "  letra inicial c1f1: " + letraInicialc1f1 +
                            ", letra inicial c1f2: " + letraInicialc1f2 +
                            ", letra inicial c1f3: " + letraInicialc1f3 +
                            ", letra inicial c1f4: " + letraInicialc1f4 +
                            "  letra inicial c2f1: " + letraInicialc2f1 +
                            ", letra inicial c2f2: " + letraInicialc2f2 +
                            ", letra inicial c2f3: " + letraInicialc2f3 +
                            ", letra inicial c2f4: " + letraInicialc2f4
            );

           /* System.out.println(
                    "  letra  c2f1: " + letrac1f1 +
                            ", letra  c2f2: " + letrac1f2 +
                            ", letra  c2f3: " + letrac1f3 +
                            ", letra  c2f4: " + letrac1f4);*/

            verificarPareja1();
            verificarPareja2();
            verificarPareja3();
            verificarPareja4();

           /* System.out.println(
                    "  letra final c1f1: " + letraFinalc1f1 +
                    ", letra final c1f2: " + letraFinalc1f2 +
                    ", letra final c1f3: " + letraFinalc1f3 +
                    ", letra final c1f4: " + letraFinalc1f4);*/


            if (resPareja1 == true && resPareja2 == true && resPareja3 == true && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡ BIEN HECHO 100% !!!");
            } else if (resPareja1 == true && resPareja2 == true && resPareja3 == true && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡ 1 CASI 75% !!!");
            } else if (resPareja1 == true && resPareja2 == true && resPareja3 == false && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡ 2 CASI 75% !!!");
            } else if (resPareja1 == true && resPareja2 == false && resPareja3 == true && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡ 3 CASI 75% !!!");
            } else if (resPareja1 == false && resPareja2 == true && resPareja3 == true && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡ 4 CASI 75% !!!");
            } else if (resPareja1 == false && resPareja2 == false && resPareja3 == true && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡  50% !!!");
            } else if (resPareja1 == false && resPareja2 == true && resPareja3 == false && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡  50% !!!");
            } else if (resPareja1 == false && resPareja2 == true && resPareja3 == true && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  50% !!!");
            } else if (resPareja1 == true && resPareja2 == true && resPareja3 == false && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  50% !!!");
            } else if (resPareja1 == true && resPareja2 == false && resPareja3 == false && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡  50% !!!");
            } else if (resPareja1 == false && resPareja2 == true && resPareja3 == false && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡  50% !!!");

            } else if (resPareja1 == false && resPareja2 == false && resPareja3 == false && resPareja4 == true) {
                txt_resultado.setText(" ¡¡¡  25% !!!");
            } else if (resPareja1 == false && resPareja2 == false && resPareja3 == true && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  25% !!!");
            } else if (resPareja1 == false && resPareja2 == true && resPareja3 == false && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  25% !!!");
            } else if (resPareja1 == true && resPareja2 == false && resPareja3 == false && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  25% !!!");
            } else if (resPareja1 == false && resPareja2 == false && resPareja3 == false && resPareja4 == false) {
                txt_resultado.setText(" ¡¡¡  NO ACERTASTE NINGUNA !!!");
            }

        }
    }

    //**********************************************************************************************

    private void verificarPareja1() {
        //--------------------------------------------------------------------------------------GRUPO1
        System.out.println("verificarPareja1");
        if (pareja_1.get(0) == 1) {
            if (letraInicialc1f1.equals(letraInicialc2f1)) {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f1: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f1: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f1);
            }
        } else if (pareja_1.get(0) == 2) {
            if (letraInicialc1f1.equals(letraInicialc2f2)) {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f2: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f2: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f2);
            }
        } else if (pareja_1.get(0) == 3) {
            if (letraInicialc1f1.equals(letraInicialc2f3)) {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f3: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f3: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f3);
            }
        } else if (pareja_1.get(0) == 4) {
            if (letraInicialc1f1.equals(letraInicialc2f4)) {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f4: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f1 y letra c1f4: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f4);
            }
        } else
            //-------------------------------------------------------------------------------------- I1
            // --------------------------------------------------------------------------------------I2

            if (pareja_1.get(0) == 5) {
                if (letraInicialc1f2.equals(letraInicialc2f1)) {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f1: SON IGUALES ");
                    resPareja1 = true;
                } else {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f1: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f1);
                }
            } else if (pareja_1.get(0) == 6) {
                if (letraInicialc1f2.equals(letraInicialc2f2)) {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f2: SON IGUALES ");
                    resPareja1 = true;
                } else {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f2: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f2);
                }
            } else if (pareja_1.get(0) == 7) {
                if (letraInicialc1f2.equals(letraInicialc2f3)) {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f3: SON IGUALES ");
                    resPareja1 = true;
                } else {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f3: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f3);
                }
            } else if (pareja_1.get(0) == 8) {
                if (letraInicialc1f2.equals(letraInicialc2f4)) {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f4: SON IGUALES ");
                    resPareja1 = true;
                } else {
                    System.out.println("G1 letra inicial imagen c1f2 y letra c1f4: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f4);
                }
            }
        //--------------------------------------------------------------------------------------I2
        // --------------------------------------------------------------------------------------I3

        if (pareja_1.get(0) == 9) {
            if (letraInicialc1f3.equals(letraInicialc2f1)) {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f1: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f1: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f1);
            }
        } else if (pareja_1.get(0) == 10) {
            if (letraInicialc1f3.equals(letraInicialc2f2)) {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f2: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f2: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f2);
            }
        } else if (pareja_1.get(0) == 11) {
            if (letraInicialc1f3.equals(letraInicialc2f3)) {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f3: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f3: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f3);
            }
        } else if (pareja_1.get(0) == 12) {
            if (letraInicialc1f3.equals(letraInicialc2f4)) {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f4: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f3 y letra c1f4: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f4);
            }
        }
        //--------------------------------------------------------------------------------------I3
        // --------------------------------------------------------------------------------------I4

        if (pareja_1.get(0) == 13) {
            if (letraInicialc1f4.equals(letraInicialc2f1)) {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f1: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f1: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f1);
            }
        } else if (pareja_1.get(0) == 14) {
            if (letraInicialc1f4.equals(letraInicialc2f2)) {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f2: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f2: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f2);
            }
        } else if (pareja_1.get(0) == 15) {
            if (letraInicialc1f4.equals(letraInicialc2f3)) {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f3: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f3: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f3);
            }
        } else if (pareja_1.get(0) == 16) {
            if (letraInicialc1f4.equals(letraInicialc2f4)) {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f4: SON IGUALES ");
                resPareja1 = true;
            } else {
                System.out.println("G1 letra inicial imagen c1f4 y letra c1f4: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f4);
            }
        }
        //--------------------------------------------------------------------------------------I4 GRUPO 1
    }

    private void verificarPareja2() {
        System.out.println("verificarPareja2");
        //--------------------------------------------------------------------------------------GRUPO2

        if (pareja_2.get(0) == 1) {
            if (letraInicialc1f1.equals(letraInicialc2f1)) {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f1: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f1: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f1);
            }
        } else if (pareja_2.get(0) == 2) {
            if (letraInicialc1f1.equals(letraInicialc2f2)) {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f2: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f2: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f2);
            }
        } else if (pareja_2.get(0) == 3) {
            if (letraInicialc1f1.equals(letraInicialc2f3)) {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f3: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f3: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f3);
            }
        } else if (pareja_2.get(0) == 4) {
            if (letraInicialc1f1.equals(letraInicialc2f4)) {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f4: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f1 y letra c1f4: NO SON IGUALES " + letraInicialc1f1 + " - " + letraInicialc2f4);
            }
        } else
            //-------------------------------------------------------------------------------------- I1
            // --------------------------------------------------------------------------------------I2

            if (pareja_2.get(0) == 5) {
                if (letraInicialc1f2.equals(letraInicialc2f1)) {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f1: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f1: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f1);
                }
            } else if (pareja_2.get(0) == 6) {
                if (letraInicialc1f2.equals(letraInicialc2f2)) {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f2: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f2: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f2);
                }
            } else if (pareja_2.get(0) == 7) {
                if (letraInicialc1f2.equals(letraInicialc2f3)) {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f3: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f3: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f3);
                }
            } else if (pareja_2.get(0) == 8) {
                if (letraInicialc1f2.equals(letraInicialc2f4)) {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f4: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f2 y letra c1f4: NO SON IGUALES " + letraInicialc1f2 + " - " + letraInicialc2f4);
                }
            }
        //--------------------------------------------------------------------------------------I2
        // --------------------------------------------------------------------------------------I3

        if (pareja_2.get(0) == 9) {
            if (letraInicialc1f3.equals(letraInicialc2f1)) {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f1: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f1: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f1);
            }
        } else if (pareja_2.get(0) == 10) {
            if (letraInicialc1f3.equals(letraInicialc2f2)) {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f2: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f2: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f2);
            }
        } else if (pareja_2.get(0) == 11) {
            if (letraInicialc1f3.equals(letraInicialc2f3)) {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f3: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f3: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f3);
            }
        } else if (pareja_2.get(0) == 12) {
            if (letraInicialc1f3.equals(letraInicialc2f4)) {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f4: SON IGUALES ");
                resPareja2 = true;
            } else {
                System.out.println("G2 letra inicial imagen c1f3 y letra c1f4: NO SON IGUALES " + letraInicialc1f3 + " - " + letraInicialc2f4);
            }
        } else
            //--------------------------------------------------------------------------------------I3
            // --------------------------------------------------------------------------------------I4

            if (pareja_2.get(0) == 13) {
                if (letraInicialc1f4.equals(letraInicialc2f1)) {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f1: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f1: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f1);
                }
            } else if (pareja_2.get(0) == 14) {
                if (letraInicialc1f4.equals(letraInicialc2f2)) {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f2: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f2: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f2);
                }
            } else if (pareja_2.get(0) == 15) {
                if (letraInicialc1f4.equals(letraInicialc2f3)) {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f3: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f3: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f3);
                }
            } else if (pareja_2.get(0) == 16) {
                if (letraInicialc1f4.equals(letraInicialc2f4)) {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f4: SON IGUALES ");
                    resPareja2 = true;
                } else {
                    System.out.println("G2 letra inicial imagen c1f4 y letra c1f4: NO SON IGUALES " + letraInicialc1f4 + " - " + letraInicialc2f4);
                }
            }
        //--------------------------------------------------------------------------------------I4 GRUPO 2
    }

    private void verificarPareja3() {
        System.out.println("verificarPareja3");
        //--------------------------------------------------------------------------------------GRUPO3

        if (pareja_3.get(0) == 1) {
            if (letraInicialc1f1.equals(letraInicialc2f1)) {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f1: SON IGUALES ");
                resPareja3 = true;
            } else {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f1: NO SON IGUALES ");
            }
        } else if (pareja_3.get(0) == 2) {
            if (letraInicialc1f1.equals(letraInicialc2f2)) {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f2: SON IGUALES ");
                resPareja3 = true;
            } else {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f2: NO SON IGUALES " + letraInicialc1f1 + " - " + letrac1f2);
            }
        } else if (pareja_3.get(0) == 3) {
            if (letraInicialc1f1.equals(letraInicialc2f3)) {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f3: SON IGUALES ");
                resPareja3 = true;
            } else {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f3: NO SON IGUALES ");
            }
        } else if (pareja_3.get(0) == 4) {
            if (letraInicialc1f1.equals(letraInicialc2f4)) {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f4: SON IGUALES ");
                resPareja3 = true;
            } else {
                System.out.println("G3 letra inicial imagen c1f1 y letra c1f4: NO SON IGUALES " + letraInicialc1f1 + " - " + letrac1f2);
            }
        } else
            //-------------------------------------------------------------------------------------- I1
            // --------------------------------------------------------------------------------------I2

            if (pareja_3.get(0) == 5) {
                if (letraInicialc1f2.equals(letraInicialc2f1)) {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f1: SON IGUALES ");
                    resPareja3 = true;
                } else {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f1: NO SON IGUALES ");
                }
            } else if (pareja_3.get(0) == 6) {
                if (letraInicialc1f2.equals(letraInicialc2f2)) {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f2: SON IGUALES ");
                    resPareja3 = true;
                } else {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f2: NO SON IGUALES " + letraInicialc1f2 + " - " + letrac1f2);
                }
            } else if (pareja_3.get(0) == 7) {
                if (letraInicialc1f2.equals(letraInicialc2f3)) {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f3: SON IGUALES ");
                    resPareja3 = true;
                } else {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f3: NO SON IGUALES ");
                }
            } else if (pareja_3.get(0) == 8) {
                if (letraInicialc1f2.equals(letraInicialc2f4)) {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f4: SON IGUALES ");
                    resPareja3 = true;
                } else {
                    System.out.println("G3 letra inicial imagen c1f2 y letra c1f4: NO SON IGUALES " + letraInicialc1f2 + " - " + letrac1f2);
                }
            } else
                //--------------------------------------------------------------------------------------I2
                // --------------------------------------------------------------------------------------I3

                if (pareja_3.get(0) == 9) {
                    if (letraInicialc1f3.equals(letraInicialc2f1)) {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f1: SON IGUALES ");
                        resPareja3 = true;
                    } else {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f1: NO SON IGUALES ");
                    }
                } else if (pareja_3.get(0) == 10) {
                    if (letraInicialc1f3.equals(letraInicialc2f2)) {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f2: SON IGUALES ");
                        resPareja3 = true;
                    } else {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f2: NO SON IGUALES " + letraInicialc1f3 + " - " + letrac1f2);
                    }
                } else if (pareja_3.get(0) == 11) {
                    if (letraInicialc1f3.equals(letraInicialc2f3)) {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f3: SON IGUALES ");
                        resPareja3 = true;
                    } else {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f3: NO SON IGUALES ");
                    }
                } else if (pareja_3.get(0) == 12) {
                    if (letraInicialc1f3.equals(letraInicialc2f4)) {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f4: SON IGUALES ");
                        resPareja3 = true;
                    } else {
                        System.out.println("G3 letra inicial imagen c1f3 y letra c1f4: NO SON IGUALES " + letraInicialc1f3 + " - " + letrac1f2);
                    }
                } else
                    //--------------------------------------------------------------------------------------I3
                    // --------------------------------------------------------------------------------------I4

                    if (pareja_3.get(0) == 13) {
                        if (letraInicialc1f4.equals(letraInicialc2f1)) {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f1: SON IGUALES ");
                            resPareja3 = true;
                        } else {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f1: NO SON IGUALES ");
                        }
                    } else if (pareja_3.get(0) == 14) {
                        if (letraInicialc1f4.equals(letraInicialc2f2)) {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f2: SON IGUALES ");
                            resPareja3 = true;
                        } else {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f2: NO SON IGUALES " + letraInicialc1f4 + " - " + letrac1f2);
                        }
                    } else if (pareja_3.get(0) == 15) {
                        if (letraInicialc1f4.equals(letraInicialc2f3)) {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f3: SON IGUALES ");
                            resPareja3 = true;
                        } else {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f3: NO SON IGUALES ");
                        }
                    } else if (pareja_3.get(0) == 16) {
                        if (letraInicialc1f4.equals(letraInicialc2f4)) {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f4: SON IGUALES ");
                            resPareja3 = true;
                        } else {
                            System.out.println("G3 letra inicial imagen c1f4 y letra c1f4: NO SON IGUALES " + letraInicialc1f4 + " - " + letrac1f2);
                        }
                    }
        //--------------------------------------------------------------------------------------I4 GRUPO 3
    }

    private void verificarPareja4() {
        System.out.println("verificarPareja4");
        //--------------------------------------------------------------------------------------GRUPO4

        if (pareja_4.get(0) == 1) {
            if (letraInicialc1f1.equals(letraInicialc2f1)) {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f1: SON IGUALES ");
                resPareja4 = true;
            } else {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f1: NO SON IGUALES ");
            }
        } else if (pareja_4.get(0) == 2) {
            if (letraInicialc1f1.equals(letraInicialc2f2)) {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f2: SON IGUALES ");
                resPareja4 = true;
            } else {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f2: NO SON IGUALES " + letraInicialc1f1 + " - " + letrac1f2);
            }
        } else if (pareja_4.get(0) == 3) {
            if (letraInicialc1f1.equals(letraInicialc2f3)) {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f3: SON IGUALES ");
                resPareja4 = true;
            } else {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f3: NO SON IGUALES ");
            }
        } else if (pareja_4.get(0) == 4) {
            if (letraInicialc1f1.equals(letraInicialc2f4)) {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f4: SON IGUALES ");
                resPareja4 = true;
            } else {
                System.out.println("G4 letra inicial imagen c1f1 y letra c1f4: NO SON IGUALES " + letraInicialc1f1 + " - " + letrac1f2);
            }
        } else
            //-------------------------------------------------------------------------------------- I1
            // --------------------------------------------------------------------------------------I2

            if (pareja_4.get(0) == 5) {
                if (letraInicialc1f2.equals(letraInicialc2f1)) {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f1: SON IGUALES ");
                    resPareja4 = true;
                } else {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f1: NO SON IGUALES ");
                }
            } else if (pareja_4.get(0) == 6) {
                if (letraInicialc1f2.equals(letraInicialc2f2)) {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f2: SON IGUALES ");
                    resPareja4 = true;
                } else {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f2: NO SON IGUALES " + letraInicialc1f2 + " - " + letrac1f2);
                }
            } else if (pareja_4.get(0) == 7) {
                if (letraInicialc1f2.equals(letraInicialc2f3)) {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f3: SON IGUALES ");
                    resPareja4 = true;
                } else {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f3: NO SON IGUALES ");
                }
            } else if (pareja_4.get(0) == 8) {
                if (letraInicialc1f2.equals(letraInicialc2f4)) {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f4: SON IGUALES ");
                    resPareja4 = true;
                } else {
                    System.out.println("G4 letra inicial imagen c1f2 y letra c1f4: NO SON IGUALES " + letraInicialc1f2 + " - " + letrac1f2);
                }
            } else
                //--------------------------------------------------------------------------------------I2
                // --------------------------------------------------------------------------------------I3

                if (pareja_4.get(0) == 9) {
                    if (letraInicialc1f3.equals(letraInicialc2f1)) {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f1: SON IGUALES ");
                        resPareja4 = true;
                    } else {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f1: NO SON IGUALES ");
                    }
                } else if (pareja_4.get(0) == 10) {
                    if (letraInicialc1f3.equals(letraInicialc2f2)) {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f2: SON IGUALES ");
                        resPareja4 = true;
                    } else {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f2: NO SON IGUALES " + letraInicialc1f3 + " - " + letrac1f2);
                    }
                } else if (pareja_4.get(0) == 11) {
                    if (letraInicialc1f3.equals(letraInicialc2f3)) {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f3: SON IGUALES ");
                        resPareja4 = true;
                    } else {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f3: NO SON IGUALES ");
                    }
                } else if (pareja_4.get(0) == 12) {
                    if (letraInicialc1f3.equals(letraInicialc2f4)) {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f4: SON IGUALES ");
                        resPareja4 = true;
                    } else {
                        System.out.println("G4 letra inicial imagen c1f3 y letra c1f4: NO SON IGUALES " + letraInicialc1f3 + " - " + letrac1f2);
                    }
                } else
                    //--------------------------------------------------------------------------------------I3
                    // --------------------------------------------------------------------------------------I4

                    if (pareja_4.get(0) == 13) {
                        if (letraInicialc1f4.equals(letraInicialc2f1)) {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f1: SON IGUALES ");
                            resPareja4 = true;
                        } else {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f1: NO SON IGUALES ");
                        }
                    } else if (pareja_4.get(0) == 14) {
                        if (letraInicialc1f4.equals(letraInicialc2f2)) {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f2: SON IGUALES ");
                            resPareja4 = true;
                        } else {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f2: NO SON IGUALES " + letraInicialc1f4 + " - " + letrac1f2);
                        }
                    } else if (pareja_4.get(0) == 15) {
                        if (letraInicialc1f4.equals(letraInicialc2f3)) {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f3: SON IGUALES ");
                            resPareja4 = true;
                        } else {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f3: NO SON IGUALES ");
                        }
                    } else if (pareja_4.get(0) == 16) {
                        if (letraInicialc1f4.equals(letraInicialc2f4)) {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f4: SON IGUALES ");
                            resPareja4 = true;
                        } else {
                            System.out.println("G4 letra inicial imagen c1f4 y letra c1f4: NO SON IGUALES " + letraInicialc1f4 + " - " + letrac1f2);
                        }
                    }
        //--------------------------------------------------------------------------------------I4 GRUPO 4
    }


    //**********************************************************************************************

    public void cargarWebService(int idejercicio) {

        //if ()

        String url_lh = Globals.url;
        String url = "http://" + url_lh + "/proyecto_dconfo_v1/wsJSONConsultarImagen.php?idImagen_Ejercicio="+idejercicio;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);//p21

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
            letraInicial = imagen.getLetraInicialImagen();
            letraFinal = imagen.getLetraFinalImagen();

            String url_lh = Globals.url;

            final String rutaImagen = imagen.getRutaImagen();

            urlImagen = "http://" + url_lh + "/proyecto_dconfo_v1/" + rutaImagen;
            urlImagen = urlImagen.replace(" ", "%20");

            ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    //holder.imagen.setImageBitmap(response);


                    if (f == 0) {
                        //letraf1_c1 = imagen.getLetraInicialImagen();
                        //System.out.println("  letraf1_c1: " + letraf1_c1);
                        cv_est_st2_c1f1.setBackground(null);
                        cv_est_st2_c1f1.setImageBitmap(response);
                        letraInicialc1f1 = letraInicial;
                        letraFinalc1f1 = letraFinal;


                    } else if (f == 1) {
                        //letraf1_c2 = imagen.getLetraInicialImagen();
                        cv_est_st2_c1f2.setBackground(null);
                        cv_est_st2_c1f2.setImageBitmap(response);
                        letraInicialc1f2 = letraInicial;
                        letraFinalc1f2 = letraFinal;

                    } else if (f == 2) {
                        //letraf1_c3 = imagen.getLetraInicialImagen();
                        cv_est_st2_c1f3.setBackground(null);
                        cv_est_st2_c1f3.setImageBitmap(response);
                        letraInicialc1f3 = letraInicial;
                        letraFinalc1f3 = letraFinal;

                    } else if (f == 3) {
                        //letraf1_c4 = imagen.getLetraInicialImagen();
                        cv_est_st2_c1f4.setBackground(null);
                        cv_est_st2_c1f4.setImageBitmap(response);
                        letraInicialc1f4 = letraInicial;
                        letraFinalc1f4 = letraFinal;

                    } else if (f == 4) {
                        //letraf1_c1 = imagen.getLetraInicialImagen();
                        //System.out.println("  letraf1_c1: " + letraf1_c1);
                        cv_est_st2_c2f1.setBackground(null);
                        cv_est_st2_c2f1.setImageBitmap(response);
                        letraInicialc2f1 = letraInicial;
                        letraFinalc2f1 = letraFinal;

                    } else if (f == 5) {
                        //letraf1_c2 = imagen.getLetraInicialImagen();
                        cv_est_st2_c2f2.setBackground(null);
                        cv_est_st2_c2f2.setImageBitmap(response);
                        letraInicialc2f2 = letraInicial;
                        letraFinalc2f2 = letraFinal;

                    } else if (f == 6) {
                        //letraf1_c3 = imagen.getLetraInicialImagen();
                        cv_est_st2_c2f3.setBackground(null);
                        cv_est_st2_c2f3.setImageBitmap(response);
                        letraInicialc2f3 = letraInicial;
                        letraFinalc2f3 = letraFinal;

                    } else if (f == 7) {
                        //letraf1_c4 = imagen.getLetraInicialImagen();
                        cv_est_st2_c2f4.setBackground(null);
                        cv_est_st2_c2f4.setImageBitmap(response);
                        letraInicialc2f4 = letraInicial;
                        letraFinalc2f4 = letraFinal;

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


    //----------------------------------------------------------------------------------------------
    public void llamarWebService() {
        //for (int i = 0; i < listaIdImagens.size(); i++) {
        if (f < 7) {
            f++;
            System.out.println("valor f: " + f);
            cargarWebService(listaIdImagens.get(f));
        }


        //}
    }

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
