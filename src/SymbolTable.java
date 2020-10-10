public class SymbolTable {
    public String id;
    public String attribute;

    public SymbolTable(String id, String attribute){
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
        return "SymbolTable{" +
                "id='" + id + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
