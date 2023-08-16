package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private char character;
    private int charFreq;
    private boolean isLeaf;

    private HuffmanNode right, left;

    public HuffmanNode() {
    }

    public HuffmanNode(char character, int charFreq, boolean isLeaf) {
        left = right = null;
        this.character = character;
        this.charFreq = charFreq;
        this.isLeaf = isLeaf;
    }

    public int compareTo(HuffmanNode t) {
        if (t.charFreq > this.charFreq)
            return -1;
        else if (t.charFreq < this.charFreq)
            return 1;
        else
            return 0;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getCharFreq() {
        return charFreq;
    }

    public void setCharFreq(int charFreq) {
        this.charFreq = charFreq;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }
}
