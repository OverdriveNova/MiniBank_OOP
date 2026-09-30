# Practical 08 - Part B: MiniBank Robust Exception Handling Architecture

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v8.0`

---

## 1. Objective & Problem Overview
Practical 8 replaces ad-hoc error codes with a structured custom exception hierarchy rooted at BankException. Specialized subclasses handle insufficient funds and missing accounts, wrapped inside BankSession for atomic execution safety.

---

## 2. Core OOP Principles & Java Features
- **Checked Exceptions**
- **Exception Hierarchy**
- **Custom Exception Classes**
- **Try-Catch-Finally**
- **Transaction Rollback**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-08
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why create custom exceptions rather than throwing RuntimeException?**  
*A:* Custom checked exceptions force calling code to explicitly handle domain-specific failures (e.g., prompt for smaller amount on InsufficientFundsException) and convey clear semantic meaning.

**Q: Why are banking exceptions typically checked instead of unchecked?**  
*A:* Banking operations involve critical transactional invariants where failures are recoverable business conditions rather than unrecoverable programmer errors.

