
package com.twisac.kamwegawritings.jsonpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes {

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("shareaholic-thumbnail")
    @Expose
    private ShareaholicThumbnail shareaholicThumbnail;
    @SerializedName("post-thumbnail")
    @Expose
    private PostThumbnail postThumbnail;
    @SerializedName("main-full")
    @Expose
    private MainFull mainFull;
    @SerializedName("main-slider")
    @Expose
    private MainSlider mainSlider;
    @SerializedName("main-block")
    @Expose
    private MainBlock mainBlock;
    @SerializedName("slider-small")
    @Expose
    private SliderSmall sliderSmall;
    @SerializedName("gallery-block")
    @Expose
    private GalleryBlock galleryBlock;
    @SerializedName("grid-overlay")
    @Expose
    private GridOverlay gridOverlay;
    @SerializedName("simpleSubscribeEmail")
    @Expose
    private SimpleSubscribeEmail simpleSubscribeEmail;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public ShareaholicThumbnail getShareaholicThumbnail() {
        return shareaholicThumbnail;
    }

    public void setShareaholicThumbnail(ShareaholicThumbnail shareaholicThumbnail) {
        this.shareaholicThumbnail = shareaholicThumbnail;
    }

    public PostThumbnail getPostThumbnail() {
        return postThumbnail;
    }

    public void setPostThumbnail(PostThumbnail postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

    public MainFull getMainFull() {
        return mainFull;
    }

    public void setMainFull(MainFull mainFull) {
        this.mainFull = mainFull;
    }

    public MainSlider getMainSlider() {
        return mainSlider;
    }

    public void setMainSlider(MainSlider mainSlider) {
        this.mainSlider = mainSlider;
    }

    public MainBlock getMainBlock() {
        return mainBlock;
    }

    public void setMainBlock(MainBlock mainBlock) {
        this.mainBlock = mainBlock;
    }

    public SliderSmall getSliderSmall() {
        return sliderSmall;
    }

    public void setSliderSmall(SliderSmall sliderSmall) {
        this.sliderSmall = sliderSmall;
    }

    public GalleryBlock getGalleryBlock() {
        return galleryBlock;
    }

    public void setGalleryBlock(GalleryBlock galleryBlock) {
        this.galleryBlock = galleryBlock;
    }

    public GridOverlay getGridOverlay() {
        return gridOverlay;
    }

    public void setGridOverlay(GridOverlay gridOverlay) {
        this.gridOverlay = gridOverlay;
    }

    public SimpleSubscribeEmail getSimpleSubscribeEmail() {
        return simpleSubscribeEmail;
    }

    public void setSimpleSubscribeEmail(SimpleSubscribeEmail simpleSubscribeEmail) {
        this.simpleSubscribeEmail = simpleSubscribeEmail;
    }

}
