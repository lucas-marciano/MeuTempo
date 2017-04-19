package br.com.cafecomandroid.meutempo.sync;

import android.content.Context;
import android.net.Uri;
import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.cafecomandroid.meutempo.entitys.WetherEntity;
import br.com.cafecomandroid.meutempo.utils.SharedPreferenceDefault;

/**
 * Created by Lucas Marciano on 25/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.sync
 */

public class SyncAdapter{
    private final String LOG_TAG = SyncAdapter.class.getSimpleName();
    private ArrayList<WetherEntity> wethers;
    private Context context;

    public ArrayList<WetherEntity> executeSyncTask(){
        this.performSync();
        return  wethers;
    }

    public SyncAdapter(Context context) {
        this.context = context;
    }

    private void performSync() {
        Log.d(LOG_TAG, "Starting sync");

        String locationQuery = SharedPreferenceDefault.getSahredPreferenceCidade(context);
        String lang = "pt";

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String format = "json";
        String units = SharedPreferenceDefault.getSahredPreferenceUnidade(context);
        int numDays = Integer.parseInt(SharedPreferenceDefault.getSahredPreferenceCNT(context));

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String LANG = "lang";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String DATAE_PARAM = "dt";
            final String APPID_PARAM = "APPID";
            final String OPEN_WEATHER_MAP_API_KEY = "dad6b4b7f4ff578662486324d88d0b73";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, locationQuery)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(LANG, lang)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(APPID_PARAM, OPEN_WEATHER_MAP_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            forecastJsonStr = buffer.toString();
            getWeatherDataFromJson(forecastJsonStr, locationQuery);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    private void getWeatherDataFromJson(String forecastJsonStr, String locationSetting) throws JSONException {
        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        // Location information
        final String OWM_CITY = "city";
        final String OWM_CITY_NAME = "name";
        final String OWM_COORD = "coord";

        // Location coordinate
        final String OWM_LATITUDE = "lat";
        final String OWM_LONGITUDE = "lon";

        // Weather information.  Each day's forecast info is an element of the "list" array.
        final String OWM_LIST = "list";

        final String OWM_PRESSURE = "pressure";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_WINDSPEED = "speed";
        final String OWM_WIND_DIRECTION = "deg";

        // Temperatures in day light and the night
        final String OWN_TEMPERATURE_NIGHT = "night";
        final String OWN_TEMPERATURE_EVENING = "eve";

        // All temperatures are children of the "temp" object.
        final String OWM_TEMPERATURE = "temp";
        final String OWM_DAY = "day";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_MAIN = "main";
        final String OWM_DESCRIPTION = "description";
        final String OWM_WEATHER_ID = "id";

        try {
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            JSONObject cityJson = forecastJson.getJSONObject(OWM_CITY);
            String cityName = cityJson.getString(OWM_CITY_NAME);

            JSONObject cityCoord = cityJson.getJSONObject(OWM_COORD);
            double cityLatitude = cityCoord.getDouble(OWM_LATITUDE);
            double cityLongitude = cityCoord.getDouble(OWM_LONGITUDE);

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
            Calendar calendar = Calendar.getInstance();

            // now we work exclusively in UTC
            dayTime = new Time();
            wethers = new ArrayList<>();

            for(int i = 0; i < weatherArray.length(); i++) {
                WetherEntity wheter = new WetherEntity();
                wheter.setCityLatitude(cityLatitude);
                wheter.setCityLongitude(cityLongitude);
                wheter.setCityName(cityName);
                wheter.setDate(
                        calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH) + 1) + "/" +
                        calendar.get(Calendar.YEAR));

                calendar.add(Calendar.DAY_OF_MONTH, i);

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // Cheating to convert this to UTC time, which is what we want anyhow
                wheter.setDateTime(dayTime.setJulianDay(julianStartDay+i));

                wheter.setPressure(dayForecast.getInt(OWM_PRESSURE));
                wheter.setHumidity(dayForecast.getInt(OWM_HUMIDITY));
                wheter.setWindSpeed(dayForecast.getDouble(OWM_WINDSPEED));
                wheter.setWindDirection(dayForecast.getDouble(OWM_WIND_DIRECTION));

                // Description is in a child array called "weather", which is 1 element long.
                // That element also contains a weather code.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                wheter.setDescription(weatherObject.getString(OWM_DESCRIPTION));
                wheter.setMain(weatherObject.getString(OWM_MAIN));
                wheter.setWeatherId(weatherObject.getInt(OWM_WEATHER_ID));

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);

                wheter.setDay(temperatureObject.getInt(OWM_DAY));
                wheter.setHigh(temperatureObject.getInt(OWM_MAX));
                wheter.setLow(temperatureObject.getInt(OWM_MIN));
                wheter.setEvening(temperatureObject.getInt(OWN_TEMPERATURE_EVENING));
                wheter.setNight(temperatureObject.getInt(OWN_TEMPERATURE_NIGHT));

                wethers.add(wheter);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
