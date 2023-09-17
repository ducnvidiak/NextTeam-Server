/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author admin
 */
public class PostComment {
    private int id;
    private int newsPostId;
    private int refPostComment;

    public PostComment(int newsPostId, int refPostComment) {
        this.newsPostId = newsPostId;
        this.refPostComment = refPostComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsPostId() {
        return newsPostId;
    }

    public void setNewsPostId(int newsPostId) {
        this.newsPostId = newsPostId;
    }

    public int getRefPostComment() {
        return refPostComment;
    }

    public void setRefPostComment(int refPostComment) {
        this.refPostComment = refPostComment;
    }
    
    
}
