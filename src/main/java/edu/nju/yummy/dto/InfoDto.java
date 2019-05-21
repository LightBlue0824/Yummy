package edu.nju.yummy.dto;

public class InfoDto {
    private String infoType;
    private String info;
    private String comment;

    public InfoDto() {
    }

    public InfoDto(String infoType, String info, String comment) {
        this.infoType = infoType;
        this.info = info;
        this.comment = comment;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
