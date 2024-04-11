package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Character> algoPath = new ArrayList<>();

    /**
     * Initialize an empty Path.
     */
    public Path() {
    }

    /**
     * Initialize path from a Path String.
     *
     * @param pathStr The Path String
     */
    public Path(String pathStr) {
        String expanded = expandFactorizedStringPath(pathStr);
        for (Character c : expanded.toCharArray()) {
            if (c != ' ') {
                if (c != 'F' && c != 'L' && c != 'R') {
                    throw new IllegalArgumentException("Instruction '" + c + "' is invalid. Must be 'F', 'L', or 'R'.");
                }
                addStep(c);
            }
        }
    }

    /**
     * Expand a factorized string path into a canonical one.
     *
     * @param path String path
     * @return Expanded string path
     */
    public String expandFactorizedStringPath(String path) {
        StringBuilder expanded = new StringBuilder();
        int i = 0;
        while(i < path.length()){
            if (!Character.isDigit(path.charAt(i))) {
                expanded.append(path.charAt(i));
            } else {
                int count = 0;
                int digit = 0;
                do {
                    count *= (int) Math.pow(10, digit++);
                    count += Character.getNumericValue(path.charAt(i++));
                } while (Character.isDigit(path.charAt(i)));

                String step = String.valueOf(path.charAt(i)).repeat(count);
                expanded.append(step);
            }
            i++;
        }

        return expanded.toString();
    }

    /**
     * Get steps of Path.
     *
     * @return Chars of Path
     */
    public List<Character> getPathSteps() {
        return new ArrayList<>(this.algoPath);
    }

    /**
     * Adds a step to the path.
     *
     * @param step The step that needs to be added.
     */
    public void addStep(Character step) {
        algoPath.add(step);
    }

    /**
     * Generates the canonical form of the maze path.
     *
     * @return A string of the canonical form of a path.
     */
    public String getCanonicalForm() {
        StringBuilder sb = new StringBuilder();

        for (Character c : algoPath) {
            if (sb.isEmpty() || sb.charAt(sb.length() - 1) == c) {
                sb.append(c);
            } else {
                sb.append(' ');
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Generates the factorized form of the maze path.
     *
     * @return A string of the factorized form of a path.
     */
    public String getFactorizedForm() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < algoPath.size()){
            Character current = algoPath.get(i);
            int count = 0;
            while (i < algoPath.size() && current.equals(algoPath.get(i))) {
                count++;
                i++;
            }

            if (count == 1) {
                sb.append(current);
            } else {
                sb.append(count);
                sb.append(current);
            }

            if (i != algoPath.size()) {
                sb.append(' ');
            }
            i--;
        }

        return sb.toString();
    }
}
