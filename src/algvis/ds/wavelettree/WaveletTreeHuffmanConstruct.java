package algvis.ds.wavelettree;

import algvis.core.Algorithm;
import algvis.core.Node;
import algvis.core.visual.VisualElement;

import java.util.*;
import java.lang.Math.*;

public class WaveletTreeHuffmanConstruct extends Algorithm {
    private WaveletTree WT;
    private final String s;

    public WaveletTreeHuffmanConstruct(WaveletTree WT, String s) {
        super(WT.panel);
        this.WT = WT;
        this.s = s;
    }

    public static Map<String,Integer> getCharFreq(String s) {
        Map<String,Integer> charFreq = new HashMap<String,Integer>();
        if (s != null) {
            for (Character c : s.toCharArray()) {
                Integer count = charFreq.get(c.toString());
                int newCount = (count==null ? 1 : count+1);
                charFreq.put(c.toString(), newCount);
            }
        }
        return charFreq;
    }

    public static Map.Entry<String, Integer> getMin(Map<String,Integer> map) {
        int minValue = Collections.min(map.values());
        for (Map.Entry<String, Integer> entry: map.entrySet()){
            if (entry.getValue() == minValue) {
                map.remove(entry.getKey());
                return entry;
            }
        }
        return null;
    }

    public void positionTrees(List<WaveletTree> trees){
        int x = 0;
        int y = 100;
        for (WaveletTree tree : trees) {
            WaveletTreeNode root = tree.getRoot();
            root.tox = root.x = x;
            root.toy = root.y = y;
            root.reposition();
            x += root.getString().length() * root.char_h;
        }
    }

    public void linkTrees(WaveletTreeNode root, WaveletTreeNode left, WaveletTreeNode right) {
        root.setLeftChild(left);
        left.setParent(root);
        removeFromScene(left.D);

        root.setRightChild(right);
        right.setParent(root);
        removeFromScene(right.D);
    }

    public String subsetString(String full, String included) {
        StringBuilder sb = new StringBuilder();
        Set<Character> includedLetters = new HashSet<>();
        for (Character c: included.toCharArray()) {
            includedLetters.add(c);
        }
        for(Character c : full.toCharArray()) {
            if (includedLetters.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String createBits(String full, String left, String right) {
        StringBuilder sb = new StringBuilder();
        Set<Character> leftLetters = new HashSet<>();
        for (Character c: left.toCharArray()) {
            leftLetters.add(c);
        }
        Set<Character> rightLetters = new HashSet<>();
        for (Character c: right.toCharArray()) {
            rightLetters.add(c);
        }
        for(Character c : full.toCharArray()) {
            if (leftLetters.contains(c)) {
                sb.append('0');
            }
            if (rightLetters.contains(c)){
                sb.append('1');
            }
        }
        return sb.toString();
    }

    @Override
    public void runAlgorithm () {
        WT.clear();
        Map<String, Integer> charFreqs = getCharFreq(s);
        Map<String, WaveletTree> stringToTree = new HashMap<>();

        for (Map.Entry<String, Integer> entry : charFreqs.entrySet()) {
            WaveletTree tree = new WaveletTree(WT.panel);
            addToScene(tree);

            stringToTree.put(entry.getKey(), tree);
            WaveletTreeNode root = tree.getRoot();
            root.setString(entry.getKey().repeat(entry.getValue()));
            root.setBits(entry.getKey());

            positionTrees(new ArrayList<>(stringToTree.values()));
            pause();
        }

        WaveletTree newTree = null;
        while ( charFreqs.size() != 1 ) {
            Map.Entry<String, Integer> min1 = getMin(charFreqs);
            assert min1 != null;
            WaveletTree u = stringToTree.get(min1.getKey());
            stringToTree.remove(min1.getKey());

            Map.Entry<String, Integer> min2 = getMin(charFreqs);
            assert min2 != null;
            WaveletTree v = stringToTree.get(min2.getKey());
            stringToTree.remove(min2.getKey());

            newTree = new WaveletTree(WT.panel);
            addToScene(newTree);

            charFreqs.put(min1.getKey() + min2.getKey(), min1.getValue() + min2.getValue());
            stringToTree.put(min1.getKey() + min2.getKey(), newTree);

            WaveletTreeNode rootNode = newTree.getRoot();
            WaveletTreeNode leftNode = u.getRoot();
            WaveletTreeNode rightNode = v.getRoot();

            linkTrees(rootNode, leftNode, rightNode);

            rootNode.setString(subsetString(s, min1.getKey() + min2.getKey()));
            rootNode.setBits(createBits(s, min1.getKey(), min2.getKey()));

            positionTrees(new ArrayList<>(stringToTree.values()));
            pause();
        }
        WT.setRoot(newTree.getRoot());
        positionTrees(new ArrayList<>(stringToTree.values()));
        pause();
    }
}

