import java.util.ArrayList;

public class SymbolT {
   private String id;
   private String attribute;

    public SymbolT(String id,String attribute){
        this.id=id;
        this.attribute=attribute;
    }

    String getId(){
        return id;
    }
    void setId(String newid){
        id=newid;
    }

    String getAttribute(){
        return attribute;
    }
    void setAttribute(String newattribute){
        attribute=newattribute;
    }

    @Override
    public String toString() {
        return "SymbolT{" +
                "id='" + id + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
