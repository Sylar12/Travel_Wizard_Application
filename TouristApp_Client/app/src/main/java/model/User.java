package model;

import java.io.Serializable;

/**
 * Created by vincent on 15/4/9.
 */
/*The user model describe the basic information of a user, which is stored in the user table of database. The user can sign up
 * to create a new user object from app.*/
public class User implements Serializable {
    private static final long serialVersionUID = -6396163511776410594L;
    private String userID;
    private String password;
    private String name;
    private int age;
    private int sex;    // 0: Female, 1: Male
    private String email;
    private String phone;   // Must contain only numbers or '(', ')', '-'
    private String address;
    private String country;
    public User(String id, String password, String name, int age, int sex,
         String email, String phone, String address, String country) {
        this.userID = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.country = country;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public void print() {
        System.out.println(toString());
    }
}
