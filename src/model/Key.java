package model;

public class Key {
    int key1,key2;
    public Key(int key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;

    }

    public Key() {
    }

    public int getKey1() {
        return key1;
    }

    public void setKey1(int key1) {
        this.key1 = key1;
    }

    public int getKey2() {
        return key2;
    }

    public void setKey2(int key2) {
        this.key2 = key2;
    }

    public void setKeys(int key1, int key2){
        this.key1 = key1;
        this.key2 = key2;
    }
}
