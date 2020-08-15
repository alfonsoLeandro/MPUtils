package com.github.alfonsoLeandro.MPUtils.GUIs;

class GUIAtributes {


    final private int page;
    final private GUIType guiType;
    final private String guiTags;

    public GUIAtributes(int page, GUIType guiType, String guiTags){
        this.page = page;
        this.guiType = guiType;
        this.guiTags = guiTags;
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

}
