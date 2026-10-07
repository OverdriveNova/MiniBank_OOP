# Practical 09 - Part B: MiniBank Multithreaded Account Synchronization

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v9.0`

---

## 1. Objective & Problem Overview
Practical 9 addresses concurrent operations in multi-user banking. It demonstrates how race conditions corrupt account balances without synchronization and implements synchronized blocks on account monitors to ensure atomicity and thread safety.

---

## 2. Core OOP Principles & Java Features
- **Multithreading**
- **Runnable Interface**
- **Race Conditions**
- **Thread Synchronization**
- **Intrinsic Locks**
- **Atomicity**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-09
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: What is a race condition in a banking context?**  
*A:* A race condition occurs when multiple threads concurrently read and modify shared account balance without coordination, leading to lost updates where one transaction overwrites another.

**Q: What is an intrinsic monitor lock in Java?**  
*A:* Every Java object has an intrinsic monitor lock. When a thread enters a synchronized block on an object, it acquires the monitor, forcing other threads to wait until it exits.

