package suggestion;

public class Suggestion {
    private String Name;
    private String suggestion;

    public Suggestion(){};

    public Suggestion(String name, String sug){
        this.Name = name;
        this.suggestion = sug;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getName() {
        return Name;
    }

    public String getSuggestion() {
        return suggestion;
    }
}
