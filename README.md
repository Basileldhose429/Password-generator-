# Password Generator

A Java application that generates strong, random passwords based on the user's
requirements: length and character types (uppercase, lowercase, numbers, special characters).

**Group Members:** Adithyan Saji, Basil Eldhose, Nithin Raghavan

## Features
- Custom length (4–128 characters)
- Choose any combination of character types
- Guarantees at least one character of each selected type
- Cryptographically secure randomness (`java.security.SecureRandom`)
- Fisher-Yates shuffle so character positions are unpredictable
- Strength rating (Weak / Medium / Strong / Very Strong)
- Two interfaces: console and Swing GUI (with copy-to-clipboard)

## Java Concepts Demonstrated
Classes and objects, methods, conditional statements, loops, strings, arrays,
exception handling, random number generation, and Swing event handling.

## Project Structure
```
PasswordGenerator/
├── src/
│   ├── PasswordGenerator.java      # core logic
│   ├── Main.java                   # console interface
│   └── PasswordGeneratorGUI.java   # Swing GUI
├── run.sh
└── README.md
```

## Build & Run
Requires JDK 8 or later.

```bash
mkdir -p out
javac -d out src/*.java

java -cp out Main                   # console version
java -cp out PasswordGeneratorGUI   # GUI version
```
Or run `./run.sh` (console) / `./run.sh gui`.
