/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class PostComment {
    private int id;
    private int postId;
    private int repPostCommentId;

    public PostComment(int id,int postId, int repPostCommentId) {
        this.postId = postId;
        this.repPostCommentId = repPostCommentId;
         this.id = id;
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

    public int getRepPostCommentId() {
        return repPostCommentId;
    }

    public void setRepPostCommentId(int repPostCommentId) {
        this.repPostCommentId = repPostCommentId;
    }

    
    
}
