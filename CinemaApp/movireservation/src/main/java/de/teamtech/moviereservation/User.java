package de.teamtech.moviereservation;

public class User {
    
    private String firstName;
    private String lastName;
    private int id;
    private String userName;
    private int age;
    private char gender;
    private String password;
    private boolean loginStatus;

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setId(int id){
        this.id = id;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLoginStatus(boolean loginStatus){
        this.loginStatus = loginStatus;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public int getId(){
        return id;
    }
    
    public String getUserName(){
        return userName;
    }

    public int getAge(){
        return age;
    }

    public char getGender(){
        return gender;
    }

    public String getPassword(){
        return password;
    }

    public boolean getLoginStatus(){
        return loginStatus;
    }



}
