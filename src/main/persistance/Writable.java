package persistance;

import org.json.JSONObject;

//The following code is copied from the Writable class of the JsonSerializationDemo:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

