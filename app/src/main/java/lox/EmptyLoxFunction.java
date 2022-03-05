package lox;

import java.util.List;

public class EmptyLoxFunction extends LoxFunction {
    private final int arity;

    EmptyLoxFunction(int arity) {
        super(null, null, false);
        this.arity = arity;
    }

    @Override
    LoxFunction bind(LoxInstance instance) {
        return this;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        return null;
    }

    @Override
    public int arity() {
        return arity;
    }

    @Override
    public String toString() {
        return "<fn>";
    }
}
