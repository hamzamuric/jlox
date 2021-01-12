package lox;

public class VariableLocation {
    int depth;
    int index;

    public VariableLocation(int depth, int index) {
        this.depth = depth;
        this.index = index;
    }
}
