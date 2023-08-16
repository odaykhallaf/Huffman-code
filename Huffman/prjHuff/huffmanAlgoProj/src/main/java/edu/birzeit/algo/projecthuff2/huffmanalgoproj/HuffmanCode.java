package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

import java.util.Hashtable;
import java.util.PriorityQueue;
 
public class HuffmanCode {

	Hashtable<Character, Code> codes = new Hashtable<>();

	void generateCodes(int[] rep) {
		HuffmanNode left, right, top;

		PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<>();

		for (int i = 0; i < rep.length; i++) {
			if (rep[i] > 0) {
				minHeap.add(new HuffmanNode((char) i, rep[i], true));

			}
		}

		while (minHeap.size() != 1) {
			left = minHeap.poll();
			right = minHeap.poll();

			if (left == null)
				left = new HuffmanNode();
			if (right == null)
				right = new HuffmanNode();

			top = new HuffmanNode('\0', left.getCharFreq() + right.getCharFreq(), false);
			top.setLeft(left);
			top.setRight(right);

			minHeap.add(top);

		}

		getBuiltCodeTree(minHeap.peek(), "", rep);

	}

	int count = 0;

	void getBuiltCodeTree(HuffmanNode root, String str, int[] rep) {
		if (root == null)
			return;

		if (root.isLeaf()) {
			codes.put(root.getCharacter(), new Code(root.getCharacter(), str, rep[(int) root.getCharacter()]));
			count++;
		}

		getBuiltCodeTree(root.getLeft(), str + "0", rep);
		getBuiltCodeTree(root.getRight(), str + "1", rep);
	}

}
