# Practical 01 - Part B: MiniBank Console Menu Shell

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v1.0`

---

## 1. Objective & Problem Overview
Practical 1 initializes the semester-long MiniBank application. It uses modern Java records (BankInfo) as lightweight immutable data carriers and type-safe enums (MenuOption) to model banking actions, dispatched through arrow-syntax switch expressions.

---

## 2. Core OOP Principles & Java Features
- **Java Records**
- **Enumerations**
- **Arrow-Syntax Switch Expressions**
- **Scanner Input Validation**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-01
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: What is the advantage of using record for BankInfo over a standard class?**  
*A:* Records automatically generate final fields, canonical constructor, equals(), hashCode(), and concise accessors, eliminating boilerplate code for read-only data.

**Q: Why use switch expressions instead of traditional switch statements?**  
*A:* Switch expressions evaluate directly to a value, eliminate fall-through bugs without requiring break statements, and provide compile-time exhaustiveness checking.

