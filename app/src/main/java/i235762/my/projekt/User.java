package i235762.my.projekt;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String uid;
    private String email;

    public User() {

    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;

    }
    public String getEmail(){
        return email;
    }
    public String getUid(){return uid;}
}
