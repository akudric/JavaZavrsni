package hr.java.zavrsni.entities.UserRelated;

import java.io.Serializable;

//java je najgori jezik iz nekog ralzoga ako nema get ispred imena varijabili propertyvaluefactory nece skuzit varijable kao valjanje
public record User (int getID, String getName, String getIsAdmin) implements Serializable {
}
