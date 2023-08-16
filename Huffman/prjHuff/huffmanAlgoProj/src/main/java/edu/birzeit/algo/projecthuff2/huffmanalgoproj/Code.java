package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

public class Code implements Comparable<Code> {

	private char character;
	private int charFreq;
	private int codeLength;
	private String huffmanCode;

	public Code() {

	}

	public Code(char character, String huffmanCode, int charFreq) {
		this.character = character;
		this.huffmanCode = huffmanCode;
		this.charFreq = charFreq;
		codeLength = this.huffmanCode.length();
	}

	public int compareTo(Code c) {
		if (c.character > this.character)
			return -1;
		else if (c.character < this.character)
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

	public String getHuffmanCode() {
		return huffmanCode;
	}

	public void setHuffmanCode(String huffmanCode) {
		this.huffmanCode = huffmanCode;
	}

	public int getCharFreq() {
		return charFreq;
	}

	public void setCharFreq(int charFreq) {
		this.charFreq = charFreq;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

}
