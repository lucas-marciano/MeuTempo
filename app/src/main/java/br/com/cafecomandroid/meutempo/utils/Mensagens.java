package br.com.cafecomandroid.meutempo.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Lucas Marciano on 24/02/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.utils
 */

public class Mensagens {

    public static void showSnackBar(View view, String mensagem){
        Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String mensagem){
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
