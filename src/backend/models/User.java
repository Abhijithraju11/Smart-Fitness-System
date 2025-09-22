package models;

public class User {
    private int userID;
    private String name;
    private int age;
    private String gender;
    private double height;
    private double weight;
    private String username;
    private String password;

    public User(int userID, String name, int age, String gender, double height,
                double weight, String username, String password) {
        this.userID = userID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.username = username;
        this.password = password;
    }

    public boolean register() {
        System.out.println(name + " registered successfully!");
        return true;
    }

    public boolean login() {
        System.out.println(username + " logged in successfully!");
        return true;
    }
}
