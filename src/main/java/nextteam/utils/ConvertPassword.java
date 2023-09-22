/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nextteam.utils;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author baopg
 */
public class ConvertPassword {
    //md5
    //sha-1 =>Thường được sử dụng
    public static String toSHA1(String str){
        String salt = "nextteam";
        String result = null;
        
        str=str+salt;
        try {
            byte[] dataBytes = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result= Base64.encodeBase64String(md.digest(dataBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
