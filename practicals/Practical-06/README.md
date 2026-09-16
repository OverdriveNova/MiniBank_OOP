# Practical 06 - Part B: MiniBank Transaction Processing & Auditable Contract

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v6.0`

---

## 1. Objective & Problem Overview
Practical 6 establishes a contract-driven architecture using interfaces. Accounts decouple operations by implementing Transactable, InterestBearing, and pluggable WithdrawRule strategies to enforce dynamic runtime withdrawal restrictions.

---

## 2. Core OOP Principles & Java Features
- **Interfaces**
- **Multiple Interface Inheritance**
- **Strategy Pattern**
- **Contract-Driven Design**
- **Default Methods**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-06
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why use interfaces instead of abstract classes here?**  
*A:* Interfaces support multiple inheritance of type and define decoupled capabilities (capabilities like Transactable or InterestBearing) without constraining class hierarchy.

**Q: What is the role of the WithdrawRule interface?**  
*A:* It implements the Strategy pattern, enabling different account types to inject custom withdrawal validation rules without modifying the core account class.

