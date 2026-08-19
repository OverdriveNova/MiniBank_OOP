# Practical 02 - Part B: MiniBank Account Generator & Invariant Protection

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v2.0`

---

## 1. Objective & Problem Overview
Practical 2 establishes the fundamental Account model with private fields, controlled getters, and invariant-protected mutators. It introduces static sequential ID generation and marks accountNumber final for reference integrity.

---

## 2. Core OOP Principles & Java Features
- **Encapsulation**
- **Data Hiding**
- **Static Members & ID Generation**
- **Final Fields**
- **Business Invariants**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-02
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why is accountNumber declared final?**  
*A:* An account identifier must remain immutable throughout the entire object lifecycle to guarantee reference integrity and prevent unauthorized re-assignment.

**Q: What is the difference between static and instance fields in this practical?**  
*A:* The static idCounter is shared across all instances to generate sequentially unique IDs globally, whereas balance and holderName are unique to each individual object instance.

