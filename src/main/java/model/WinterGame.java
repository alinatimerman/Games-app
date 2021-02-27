package model;

import java.time.LocalDate;

//----------------------------------------------------Class Carpet----------------------------------------------------//
public class WinterGame implements Identifiable<Integer>{
    private Integer id;
    private String name;
    private String type;
    private int minAge;
    private int maxAge;
    private LocalDate date;

//----------------------------------------------------Constructor----------------------------------------------------//

    public WinterGame(int id, String name, String type, int minAge, int maxAge, LocalDate date){
        this.id = id;
        this.name = name;
        this.type = type;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.date = date;
    }

    public WinterGame(String name, String type, int minAge, int maxAge, LocalDate date){
        this.name = name;
        this.type = type;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.date = date;
    }

    public WinterGame(int id){
        this.id = id;
        this.name = "";
        this.type = "";
        this.minAge = 0;
        this.maxAge = 0;
        this.date = LocalDate.of(0,0,0);
    }

//----------------------------------------------------Getters----------------------------------------------------//
    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public LocalDate getDate(){
        return date;
    }

//----------------------------------------------------Setters----------------------------------------------------//
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setDate(boolean status){
        this.date = date;
    }

//----------------------------------------------------ToString----------------------------------------------------//
    @Override
    public String toString() {
        final StringBuilder carpetString = new StringBuilder();
        carpetString.append(id).append("; ").append(name).append("; ").append(type).append("; ").append(minAge).append("; ").append(maxAge).append("; ").append(date);
        return carpetString.toString();
    }

//----------------------------------------------------Equals----------------------------------------------------//
    @Override
    public boolean equals(Object object){
        if(object instanceof WinterGame) {
            WinterGame winterGame = (WinterGame) object;
            return this.id.equals(winterGame.id);
        }
        return false;
    }
}
