/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.models;

/**
 *
 * @author vnitd
 */
public class PostComment {

    private int id;
    private int postId;
    private int refPostCommentId;

    public PostComment(int id, int postId, int refPostCommentId) {
        this.postId = postId;
        this.refPostCommentId = refPostCommentId;
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

    public int getRefPostCommentId() {
        return refPostCommentId;
    }

    public void setRefPostCommentId(int refPostCommentId) {
        this.refPostCommentId = refPostCommentId;
    }
}
