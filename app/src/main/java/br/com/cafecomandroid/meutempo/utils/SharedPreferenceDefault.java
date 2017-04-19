package br.com.cafecomandroid.meutempo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lucas Marciano on 24/02/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.utils
 */

public class SharedPreferenceDefault {

    private final static String SHARED_CIDADE = "SahredPreferenceCidade";
    private final static String SHARED_CNT = "SahredPreferenceCNT";
    private final static String SHARED_UNIDADE = "SahredPreferenceUNIDADE";
    private final static String SHARED_CONFIGURACAO = "SahredPreferenceCONFIGURACAO";

    public static String getSahredPreferenceCidade(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_CIDADE, Context.MODE_PRIVATE);
        return sharedPref.getString(SHARED_CIDADE, "");
    }

    public static void setSahredPreferenceCidade(Context context, String valor){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_CIDADE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_CIDADE, valor);
        editor.apply();
    }

    public static String getSahredPreferenceCNT(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_CNT, Context.MODE_PRIVATE);
        return sharedPref.getString(SHARED_CNT, "1");
    }

    public static void setSahredPreferenceCNT(Context context, String valor){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_CNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_CNT, valor);
        editor.apply();
    }

    public static String getSahredPreferenceUnidade(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_UNIDADE, Context.MODE_PRIVATE);
        return sharedPref.getString(SHARED_UNIDADE, "metric");
    }

    public static void setSahredPreferenceUnidade(Context context, String valor){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_UNIDADE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_UNIDADE, valor);
        editor.apply();
    }

    public static boolean isNovaConfiguracao(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                SHARED_CONFIGURACAO, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(SHARED_CONFIGURACAO, false);
    }

    public static void novaConfiguracao(Context context, boolean valor){
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_CONFIGURACAO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SHARED_CONFIGURACAO, valor);
        editor.apply();
    }
}
