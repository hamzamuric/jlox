package lox;

import java.util.List;
import java.util.Map;

public class LoxClass implements LoxCallable {
    final String name;
    final LoxClass superclass;
    private final Map<String, LoxFunction> methods;

    LoxClass(String name, LoxClass superclass, Map<String, LoxFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    LoxFunction findMethod(String name) {
        LoxFunction method = null;
        LoxFunction inner = null;
        LoxClass current = this;
        while (current != null) {
            if (current.methods.containsKey(name)) {
                inner = method;
                method = current.methods.get(name);
            }
            current = current.superclass;
        }
        if (inner == null) {
            inner = new EmptyLoxFunction(method != null ? method.arity() : 0);
        }
        if (method != null) {
            method.defineInner(inner);
        }
        return method;
    }

    LoxFunction findMethodUpwards(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethodUpwards(name);
        }

        return null;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        LoxFunction initializer = findMethodUpwards("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity() {
        LoxFunction initializer = findMethodUpwards("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

    @Override
    public String toString() {
        return name;
    }
}
