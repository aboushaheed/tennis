package fr.tennis.kata.model.enums;

public enum Person {

    ANNABELLE("Annabelle"),
    CHARLOTTE("Charlotte");

    private String personName;

    Person(String name) {
        this.personName = name;
    }

    public String getPersonName() {
        return personName;
    }
}
