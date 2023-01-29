package com.caregiver.healthcare;

public class Product {
    private String id;
    private String user_id;
    private String user_name;
    private String user_phone;
    private String appointment_date;
    private String appointment_time;
    private String user_address;
    private String user_gender;
    private String user_note;
    private String admin_note;


    public Product() {

    }

    public Product(String id, String user_id, String user_name, String user_phone, String appointment_date, String appointment_time, String user_address, String user_gender, String user_note, String admin_note) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.appointment_date = appointment_date;
        this.appointment_time = appointment_time;
        this.user_address = user_address;
        this.user_gender = user_gender;
        this.user_note = user_note;
        this.admin_note = admin_note;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getAdmin_note() {
        return admin_note;
    }

    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }
}
