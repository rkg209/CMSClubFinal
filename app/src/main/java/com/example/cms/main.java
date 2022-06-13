package com.example.cms;

public class main {
   String Name,Position,Photo;
   public main(){

   }

    public main(String name, String position, String photo) {
        Name = name;
        Position = position;
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public String getPosition() {
        return Position;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
