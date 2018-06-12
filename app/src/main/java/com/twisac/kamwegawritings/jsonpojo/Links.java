
package com.twisac.kamwegawritings.jsonpojo;

import java.util.ArrayList;
import java.util.List;

public class Links {

    private List<Self> self = new ArrayList<Self>();
    private List<Collection> collection = new ArrayList<Collection>();
    private List<Author> author = new ArrayList<Author>();
    private List<Reply> replies = new ArrayList<Reply>();
    private List<VersionHistory> versionHistory = new ArrayList<VersionHistory>();
    private List<HttpV2WpApiOrgAttachment> httpV2WpApiOrgAttachment = new ArrayList<HttpV2WpApiOrgAttachment>();
    private List<HttpV2WpApiOrgTerm> httpV2WpApiOrgTerm = new ArrayList<HttpV2WpApiOrgTerm>();
    private List<HttpV2WpApiOrgMetum> httpV2WpApiOrgMeta = new ArrayList<HttpV2WpApiOrgMetum>();

    /**
     * 
     * @return
     *     The self
     */
    public List<Self> getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(List<Self> self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The collection
     */
    public List<Collection> getCollection() {
        return collection;
    }

    /**
     * 
     * @param collection
     *     The collection
     */
    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    /**
     * 
     * @return
     *     The author
     */
    public List<Author> getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The replies
     */
    public List<Reply> getReplies() {
        return replies;
    }

    /**
     * 
     * @param replies
     *     The replies
     */
    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    /**
     * 
     * @return
     *     The versionHistory
     */
    public List<VersionHistory> getVersionHistory() {
        return versionHistory;
    }

    /**
     * 
     * @param versionHistory
     *     The version-history
     */
    public void setVersionHistory(List<VersionHistory> versionHistory) {
        this.versionHistory = versionHistory;
    }

    /**
     * 
     * @return
     *     The httpV2WpApiOrgAttachment
     */
    public List<HttpV2WpApiOrgAttachment> getHttpV2WpApiOrgAttachment() {
        return httpV2WpApiOrgAttachment;
    }

    /**
     * 
     * @param httpV2WpApiOrgAttachment
     *     The http://v2.wp-api.org/attachment
     */
    public void setHttpV2WpApiOrgAttachment(List<HttpV2WpApiOrgAttachment> httpV2WpApiOrgAttachment) {
        this.httpV2WpApiOrgAttachment = httpV2WpApiOrgAttachment;
    }

    /**
     * 
     * @return
     *     The httpV2WpApiOrgTerm
     */
    public List<HttpV2WpApiOrgTerm> getHttpV2WpApiOrgTerm() {
        return httpV2WpApiOrgTerm;
    }

    /**
     * 
     * @param httpV2WpApiOrgTerm
     *     The http://v2.wp-api.org/term
     */
    public void setHttpV2WpApiOrgTerm(List<HttpV2WpApiOrgTerm> httpV2WpApiOrgTerm) {
        this.httpV2WpApiOrgTerm = httpV2WpApiOrgTerm;
    }

    /**
     * 
     * @return
     *     The httpV2WpApiOrgMeta
     */
    public List<HttpV2WpApiOrgMetum> getHttpV2WpApiOrgMeta() {
        return httpV2WpApiOrgMeta;
    }

    /**
     * 
     * @param httpV2WpApiOrgMeta
     *     The http://v2.wp-api.org/meta
     */
    public void setHttpV2WpApiOrgMeta(List<HttpV2WpApiOrgMetum> httpV2WpApiOrgMeta) {
        this.httpV2WpApiOrgMeta = httpV2WpApiOrgMeta;
    }

}
