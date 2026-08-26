# Practical 03 - Part B: MiniBank Deep-Copy Cloning & Address Subsystem

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v3.0`

---

## 1. Objective & Problem Overview
Practical 3 integrates the full standard Object contract into Account and Customer, ensuring hash bucket consistency. It introduces a static nested Address class and demonstrates deep-copy cloning to prevent mutable reference aliasing bugs.

---

## 2. Core OOP Principles & Java Features
- **Object Contract (toString, equals, hashCode)**
- **Interface Cloneable**
- **Deep vs Shallow Copying**
- **Static Nested Classes**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-03
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why must equals() and hashCode() be overridden together?**  
*A:* Java enforces that if two objects evaluate equal via equals(), they MUST produce the exact same integer hash from hashCode() to prevent silent lookup and storage failures in hash collections.

**Q: Why was a deep copy necessary for Customer?**  
*A:* A shallow copy shares the Address object reference between clones; modifying the clone's address would inadvertently alter the original customer's address.

