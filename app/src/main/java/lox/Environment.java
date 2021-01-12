package lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    final Environment enclosing;
    private Map<String, Object> values;
    private ArrayList<Object> indexedValues;

    Environment() {
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    void define(String name, Object value) {
        if (values == null) {
            values = new HashMap<>();
        }
        values.put(name, value);
    }

    void defineIndexed(Object value) {
        if (indexedValues == null) {
            indexedValues = new ArrayList<>();
        }
        indexedValues.add(value);
    }

    void assign(Token name, Object value) {
        if (values != null && values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    void assignAt(VariableLocation location, Object value) {
        Environment targetEnvironment = ancestor(location.depth);
        if (targetEnvironment.indexedValues == null) {
            targetEnvironment.indexedValues = new ArrayList<>();
        }
        targetEnvironment.indexedValues.add(location.index, value);
    }

    Object get(Token name) {
        if (values != null && values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }

        if (enclosing != null) return enclosing.get(name);

        throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
    }

    Object getAt(VariableLocation location) {
        return ancestor(location.depth).indexedValues.get(location.index);
    }

    Environment ancestor(int distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            environment = environment.enclosing;
        }

        return environment;
    }
}
