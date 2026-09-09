# Practical 05 - Part B: MiniBank Polymorphic Account Hierarchy

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v5.0`

---

## 1. Objective & Problem Overview
Practical 5 refactors Account into an abstract superclass and introduces concrete polymorphic derivatives: SavingsAccount (with minimum balance thresholds), CurrentAccount (with overdraft buffers), and FixedDepositAccount (with maturity calculation).

---

## 2. Core OOP Principles & Java Features
- **Inheritance**
- **Abstract Classes**
- **Polymorphism**
- **Dynamic Method Dispatch**
- **Overdraft Limits**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-05
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Can an abstract class have constructors?**  
*A:* Yes, abstract class constructors are called via super() during concrete subclass instantiation to initialize common inherited state.

**Q: What is dynamic method dispatch?**  
*A:* It is the runtime mechanism whereby the JVM invokes the overriding method of the actual runtime object rather than the compile-time reference type.

