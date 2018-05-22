package com.company;

import java.io.IOException;
import java.util.*;

public class Compressor extends CustomFile {

    public void compress(String fileName) throws IOException {
        List<String> strings = super.read(fileName);

        CustomHuffman.buildTree(strings);

        super.save(fileName + ".compressed", strings);
    }

    public void buildCodetable(Node root) {

    }


    private class Node {
        private Integer frequency;   // Частота символа
        private Character character;        // Символ
        private Node leftChild;        // Левый потомок узла
        private Node rightChild;      // Правый потомок узла

        public Node(Integer frequency, Character character) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(Node left, Node right) {
            frequency = left.frequency + right.frequency;
            leftChild = left;
            rightChild = right;
        }
    }
}
