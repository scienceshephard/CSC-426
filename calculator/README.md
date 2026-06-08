# CSC426 Calculator (Java Swing)

A user friendly GUI calculator built with Java Swing for the CSC426 Weekly Dev exercise.

## Features

The keypad covers every operator listed in the brief:

| Button | Operation                  |
|--------|----------------------------|
| `+`    | Addition                   |
| `-`    | Subtraction                |
| `*`    | Multiplication             |
| `/`    | Division                   |
| `\`    | Integer (floor) division   |
| `^`    | Exponentiation (power)     |
| `%`    | Modulo (remainder)         |
| `C`    | Clear all                  |
| `<-`   | Backspace (delete a digit) |
| `.`    | Decimal point              |
| `=`    | Evaluate                   |

Extra touches:

* Division and modulo by zero are caught and shown as a readable error instead of crashing.
* Whole number results display without a trailing `.0`.
* Full keyboard support, so you can type digits and operators, press Enter to evaluate, Backspace to delete, and Esc to clear.

## Requirements

* JDK 17 or newer (the code uses switch expressions). It was checked against OpenJDK 21.

## Build and run

```bash
javac Calculator.java
java Calculator
```

On Java 11 and above you can also run it directly without a separate compile step:

```bash
java Calculator.java
```

## How it works

The calculator uses a simple two operand state machine. It keeps a running
accumulator, the operator that is waiting to be applied, and a flag for whether
the next digit should start a fresh number. Pressing an operator while one is
already pending evaluates the previous step first, so chained input such as
`2 + 3 + 4 =` behaves like a running total. All arithmetic lives in a single
`compute` method, which keeps the logic easy to read and test.
