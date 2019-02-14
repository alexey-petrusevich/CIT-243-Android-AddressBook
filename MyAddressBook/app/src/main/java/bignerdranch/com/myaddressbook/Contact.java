package bignerdranch.com.myaddressbook;

import java.io.Serializable;
import java.util.UUID;

public class Contact implements Serializable{
    private UUID mId;
    private String mName;
    private String mPhone;
    private String mEmail;
    private String mStreet;
    private String mCity;
    private String mState;
    private String mZip;

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(String name, String phone, String email, String street, String city, String state, String zip) {
        mId = UUID.randomUUID();
        mName = name;
        mPhone = phone;
        mEmail = email;
        mStreet = street;
        mCity = city;
        mState = state;
        mZip = zip;
    }

    public Contact(UUID uuid){
        mId = uuid;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setStreet(String street) {
        mStreet = street;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setState(String state) {
        mState = state;
    }

    public void setZip(String zip) {
        mZip = zip;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getStreet() {
        return mStreet;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }

    public String getZip() {
        return mZip;
    }
}
