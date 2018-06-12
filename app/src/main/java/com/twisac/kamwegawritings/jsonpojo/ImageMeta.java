
package com.twisac.kamwegawritings.jsonpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageMeta {

    @SerializedName("aperture")
    @Expose
    private Integer aperture;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("camera")
    @Expose
    private String camera;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("created_timestamp")
    @Expose
    private Integer createdTimestamp;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("focal_length")
    @Expose
    private Integer focalLength;
    @SerializedName("iso")
    @Expose
    private Integer iso;
    @SerializedName("shutter_speed")
    @Expose
    private Integer shutterSpeed;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("orientation")
    @Expose
    private Integer orientation;

    public Integer getAperture() {
        return aperture;
    }

    public void setAperture(Integer aperture) {
        this.aperture = aperture;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Integer createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Integer focalLength) {
        this.focalLength = focalLength;
    }

    public Integer getIso() {
        return iso;
    }

    public void setIso(Integer iso) {
        this.iso = iso;
    }

    public Integer getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(Integer shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

}
