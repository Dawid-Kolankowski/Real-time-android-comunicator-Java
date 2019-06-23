package i235762.my.projekt;




public class Message {
    public String message;
    public String user;
    public int  messageNumber;

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
