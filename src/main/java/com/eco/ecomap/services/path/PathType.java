package com.eco.ecomap.services.path;

public enum PathType {
    ALL("all"),
    ECO ("eco"),
    BALANCE ("balance"),
    SHORT ("short");

    final String pathTypeName;

    PathType(String pathTypeName){
        this.pathTypeName = pathTypeName;
    }

    @Override
    public String toString(){
        return pathTypeName;
    }
}
