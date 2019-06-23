package i235762.my.projekt;




public class Message {
    private String message;
    private String user;
    private int  messageNumber;

    Message(){
    }

    public Message(String message, String user,int messageNumber){
          this.message=message;
          this.user= user;
          this.messageNumber=messageNumber;


    }

    public String getMessage(){
        return message;
    }
    public String getUser(){
        return user;
    }
}
