package model.entity;

import java.util.Objects;

public class NoteEnglish {
    private int id;
    private String word;

    public NoteEnglish() {
    }

    public NoteEnglish(String word) {
       this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteEnglish that = (NoteEnglish) o;
        return id == that.id && Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NoteEnglish{");
        sb.append("id=").append(id);
        sb.append(", word='").append(word).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
