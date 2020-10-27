package com.github.alfonsoLeandro.mpUtils.guis;

class GUIAttributes {


    final private int page;
    final private GUIType guiType;
    final private String guiTags;
    final private GUI gui;

    public GUIAttributes(int page, GUIType guiType, String guiTags, GUI gui){
        this.page = page;
        this.guiType = guiType;
        this.guiTags = guiTags;
        this.gui = gui;
    }

    public int getPage() {
        return page;
    }

    public GUIType getGuiType() {
        return guiType;
    }

    public String getGuiTags(){
        return guiTags;
    }

    public GUI getGui(){
        return gui;
    }

}
