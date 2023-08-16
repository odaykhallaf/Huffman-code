package edu.birzeit.algo.projecthuff2.huffmanalgoproj;

import java.util.Arrays;

public class MinHeap {

    private HuffmanNode[] heap ;
    private int heapSize;

    private int n = 0;

    public MinHeap() {
        heapSize = 4;
        heap = new HuffmanNode[heapSize];
    }

    public  MinHeap(HuffmanNode[] huffNodes ) {
        heapSize = 4;
        heap = new HuffmanNode[heapSize];
        for (HuffmanNode huffNode : huffNodes) {
            if (huffNode != null)
                addNode(huffNode);
            else
                throw new NullPointerException("Some Nodes Are Null");
        }
    }

    public HuffmanNode getRoot () {
        return heapSize == 0 ? null : heap[0];
    }

    public HuffmanNode  extractRoot  () {
        if ( heapSize == 0 )
            return null;

        HuffmanNode root = heap[0];
        heap[0] = heap[n - 1];
        n--;
        heapifyDown () ;
        return root;
    }

    private void heapifyDown() {

        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftNodeIndex(index);
            if (hasRightChild(index) && getNodeRightChild(index).compareTo(getNodeLeftChild(index)) < 0 )
                smallerChildIndex = getRightNodeIndex(index);

            if (heap[index].compareTo(heap[smallerChildIndex]) > 0 )
                swapNodes(index,smallerChildIndex);
            else
                break;
            index = smallerChildIndex;
        }
    }

    public void addNode (HuffmanNode node) {
        extendSize();
        heap[n] =  node;
        n++;
        heapifyUp();
    }

    public void promptHeap () {

        for (int i = 0 ; i < n ; i++) {
            System.out.println("["+ heap[i].getCharacter() +":"+heap[i].getCharFreq()+"]" + i);
        }
    }

    private void heapifyUp() {
        int index = n - 1;
        while (hasParent(index) && getNodeParent(index).compareTo(heap[index]) > 0) {
            swapNodes(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    public int nodeCount() {
        return n;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private boolean hasLeftChild(int index) {
        return getLeftNodeIndex(index) < n;
    }

    private boolean hasRightChild(int index) {
        return getRightNodeIndex(index) < n;
    }

    private int getParentIndex (int indexAt) {
        return ( indexAt - 1 ) / 2 ;
    }
    private HuffmanNode getNodeParent (int index) {
        return heap[ getParentIndex (index) ];
    }

    private int getLeftNodeIndex (int index) {
        return 2 * index + 1;
    }
    private HuffmanNode getNodeLeftChild (int index) {
        return heap [getLeftNodeIndex (index)]  ;
    }

    private int getRightNodeIndex (int index) {
        return 2 * index + 2;
    }
    private HuffmanNode getNodeRightChild (int index) {
        return heap [getRightNodeIndex (index)];
    }

    private void swapNodes(int node1Index, int node2Index) {
        HuffmanNode temp = heap[node1Index];
        heap[node1Index] = heap[node2Index];
        heap[node2Index] = temp;
    }
    private void extendSize() {

        if (n == heapSize) {
            heapSize *= 2;
            heap = Arrays.copyOf(heap, heapSize);
        }
    }


}
