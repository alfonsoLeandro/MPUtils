package com.github.alfonsoLeandro.mpUtils.guis;

class GUIAtributes {


    final private int page;
    final private GUIType guiType;
    final private String guiTags;
    final private Object gui;

    public GUIAtributes(int page, GUIType guiType, String guiTags, Object gui){
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

    public Object getGui(){
        return gui;
    }

}
