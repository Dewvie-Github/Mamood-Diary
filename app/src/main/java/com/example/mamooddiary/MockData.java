package com.example.mamooddiary;

import java.util.ArrayList;

public class MockData {

    public static ArrayList<MockNote> getMockNotes() {
        ArrayList<MockNote> notes = new ArrayList<>();

        notes.add(new MockNote(22, 3, 2023, "This is a note"));
        notes.add(new MockNote(1, 4, 2023, "This is a note"));
        notes.add(new MockNote(15, 4, 2023, "This is a note"));
        notes.add(new MockNote(2, 5, 2023, "This is a note"));

        return notes;
    }

}

