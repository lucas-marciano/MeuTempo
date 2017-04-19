package br.com.cafecomandroid.meutempo.entitys;

/**
 * Created by Lucas Marciano on 24/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.entitys
 */

public class UserModel {
    private final String mName;
    private final String mCity;
    private final String mDescription;
    private int mAge;

    public UserModel(String name, String city, String description, int age) {
        mName = name;
        mCity = city;
        mDescription = description;
        mAge = age;
    }

    public String getName() {
        return mName;
    }

    public String getCity() {
        return mCity;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getAge() {
        return mAge;
    }

    public void incrementAge() {
        mAge++;
    }
}
