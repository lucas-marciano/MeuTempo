package br.com.cafecomandroid.meutempo.entitys;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lucas Marciano on 25/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.entitys
 */

public class WetherEntity implements Parcelable {
    private String date;
    private long dateTime;
    private String cityName;
    private double cityLatitude;
    private double cityLongitude;
    private int pressure;
    private int humidity;
    private int evening;
    private int night;
    private double windSpeed;
    private double windDirection;
    private int day;
    private int high;
    private int low;
    private String description;
    private String main;
    private String imageView;
    private long weatherId;

    public WetherEntity() {}

    private WetherEntity(Parcel in) {
        date = in.readString();
        dateTime = in.readLong();
        cityName = in.readString();
        cityLatitude = in.readDouble();
        cityLongitude = in.readDouble();
        pressure = in.readInt();
        humidity = in.readInt();
        evening = in.readInt();
        night = in.readInt();
        windSpeed = in.readDouble();
        windDirection = in.readDouble();
        day = in.readInt();
        high = in.readInt();
        low = in.readInt();
        description = in.readString();
        main = in.readString();
        imageView = in.readString();
        weatherId = in.readLong();
    }

    public static final Creator<WetherEntity> CREATOR = new Creator<WetherEntity>() {
        @Override
        public WetherEntity createFromParcel(Parcel in) {
            return new WetherEntity(in);
        }

        @Override
        public WetherEntity[] newArray(int size) {
            return new WetherEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeLong(dateTime);
        dest.writeString(cityName);
        dest.writeDouble(cityLatitude);
        dest.writeDouble(cityLongitude);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
        dest.writeInt(evening);
        dest.writeInt(night);
        dest.writeDouble(windSpeed);
        dest.writeDouble(windDirection);
        dest.writeInt(day);
        dest.writeInt(high);
        dest.writeInt(low);
        dest.writeString(description);
        dest.writeString(main);
        dest.writeString(imageView);
        dest.writeLong(weatherId);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(double cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public double getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(double cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getEvening() {
        return evening;
    }

    public void setEvening(int evening) {
        this.evening = evening;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(long weatherId) {
        this.weatherId = weatherId;
    }
}
