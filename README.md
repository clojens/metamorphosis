# metamorphosis

Macro protocols for clojure.  Lets you write a form using macros defined by a
macro protocol, and have that form expanded differently depending on which
implementation of the protocol it is wrapped in.

## Usage

The library provides a `defmprotocol` form for defining a macro protocol.

```clj
(defmprotocol arith
  (add [_ & args] "Add all arguments")
  (mul [_ & args] "Multiply all arguments"))
```

The protocol can then be implemented using the `defmtype` form.

```clj
(defmtype arith-exec arith
  (:default [arg] arg)
  (add [op & args] `(+ ~@(mapv #(do `(arith ~%)) args)))
  (mul [op & args] `(* ~@(mapv #(do `(arith ~%)) args))))
```

To use the macro protocol, write your forms within the scope of an
implementation:

```clj
(arith-exec (add 1 (mul 2 3))) => 7
```
A second implementation could just produce a data-structure.

```clj
(defmtype arith-data arith
  (:default [arg] (if (symbol? arg) (list 'quote arg) arg))
  (add [op & args] `{:op '+ :args ~(mapv #(do `(arith ~%)) args)})
  (mul [op & args] `{:op '* :args ~(mapv #(do `(arith ~%)) args)}))
```

```clj
(arith-data (add 1 (mul 2 3)))
  => {:op 'clojure.core/+ :args [1 {:op clojure.core/* :args [2 3]}]}
```

[API docs](http://hugoduncan.github.com/metamorphosis/api/0.1) &#xb7;
[Annotated Source](http://hugoduncan.github.com/metamorphosis/annotated/0.1/uberdoc.html)

## Artifacts

Metamorphosis is released to [Clojars](https://clojars.org/metamorphosis).

With Leiningen:

```clj
[metamorphosis "0.1.0"]
```

With Maven:

```xml
<dependency>
  <groupId>metamorphosis</groupId>
  <artifactId>metamorphosis</artifactId>
  <version>0.1.0</version>
</dependency>
```

## License

Copyright © 2013 Hugo Duncan

Distributed under the Eclipse Public License.
