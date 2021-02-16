package lox;

import java.util.List;

public class LoxGetter implements LoxCallable {
    private final Stmt.Getter declaration;
    private final Environment clojure;

    LoxGetter(Stmt.Getter declaration, Environment clojure) {
        this.declaration = declaration;
        this.clojure = clojure;
    }

    LoxGetter bind(LoxInstance instance) {
        Environment environment = new Environment(clojure);
        environment.define("this", instance);
        return new LoxGetter(declaration, environment);
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(clojure);
        try {
            interpreter.executeBlock(declaration.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        return null;
    }
}
