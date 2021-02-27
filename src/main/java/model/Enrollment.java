package model;

import java.time.LocalDate;

//----------------------------------------------------Class Order----------------------------------------------------//
public class Enrollment implements Identifiable<Integer>{
    private Integer id;
    private String childName;
    private String parentName;
    private int age;
    private Integer winterGame;

//----------------------------------------------------Constructor----------------------------------------------------//
    public Enrollment(Integer id, String childName, String parentName, int age, Integer winterGame) {
        this.id = id;
        this.childName = childName;
        this.parentName = parentName;
        this.age = age;
        this.winterGame = winterGame;
    }

    public Enrollment(String childName, String parentName, int age, Integer winterGame) {
        this.childName = childName;
        this.parentName = parentName;
        this.age = age;
        this.winterGame = winterGame;
    }

    public Enrollment(Integer id) {
        this.id = id;
        this.childName = "";
        this.parentName = "";
        this.age = age;
        this.winterGame = 0;
    }

//----------------------------------------------------Getters----------------------------------------------------//
    @Override
    public Integer getId() {
        return id;
    }

    public String getChildName() {
        return childName;
    }

    public String getParentName() {
        return parentName;
    }

    public int getAge() {
        return age;
    }

    public Integer getWinterGame() {
        return winterGame;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWinterGame(Integer winterGame) {
        this.winterGame = winterGame;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    @Override
    public String toString() {
        final StringBuilder orderString = new StringBuilder();
        orderString.append(id).append("; ").append(childName).append("; ").append(parentName).append("; ").append(age).append("; ").append(winterGame);
        return orderString.toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof Enrollment){
            Enrollment enrollment = (Enrollment) object;
            return this.id.equals(enrollment.id);
        }
        return false;
    }
}
