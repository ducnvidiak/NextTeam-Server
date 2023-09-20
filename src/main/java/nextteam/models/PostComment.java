/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

import java.util.Date;

/**
 *
 * @author vnitd
 */
public class PostComment {

    private int id;
    private int postId;
    private int refPostCommentId;
    private Date createdAt;
    private Date updatedAt;

    public PostComment(int id, int postId, int refPostCommentId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.postId = postId;
        this.refPostCommentId = refPostCommentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getRefPostCommentId() {
        return refPostCommentId;
    }

    public void setRefPostCommentId(int refPostCommentId) {
        this.refPostCommentId = refPostCommentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
