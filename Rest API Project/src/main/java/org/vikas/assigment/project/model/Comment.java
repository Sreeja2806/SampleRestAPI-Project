package org.vikas.assigment.project.model;

import java.util.Date;

/**
 * Created by Vikas on 9/12/16.
 */
public class Comment {

    private long commentId;
    private String comment;
    private Date created;
    private String author;
    private String name;
    public Comment() {

    }

    public Comment(long commentId, String comment, String author) {
        this.commentId = commentId;
        this.comment = comment;
        this.author = author;
        this.created = new Date();
        this.name = getName();
    }

    public long getCommentId() {
        return commentId;
    }
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}