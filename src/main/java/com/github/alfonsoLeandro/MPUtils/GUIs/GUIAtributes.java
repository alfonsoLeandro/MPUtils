package com.github.alfonsoLeandro.MPUtils.GUIs;

class GUIAtributes {


    final private int page;
    final private GUIType guiType;

    public GUIAtributes(int page, GUIType guiType){
        this.page = page;
        this.guiType = guiType;
    }

    public int getPage() {
        return page;
    }

    public GUIType getGuiType() {
        return guiType;
    }

}
