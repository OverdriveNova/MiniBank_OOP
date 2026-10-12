# Practical 11 - Part B: MiniBank Persistence, Audit Log & NIO Report

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v11.0`

---

## 1. Objective & Problem Overview
Practical 11 introduces permanent data storage. Accounts implement Serializable and are saved/loaded across sessions using ObjectOutputStream. It uses Java NIO to generate human-readable bank statement reports and persistent audit trails.

---

## 2. Core OOP Principles & Java Features
- **Java Serialization**
- **ObjectOutputStream/ObjectInputStream**
- **serialVersionUID**
- **Java NIO**
- **Audit Trails**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-11
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: What is the purpose of serialVersionUID in Serializable classes?**  
*A:* It serves as a version control identifier for serialized objects. If the class structure changes without updating it, an InvalidClassException is thrown upon deserialization.

**Q: Why use Java NIO (java.nio.file.Files) instead of legacy java.io.File?**  
*A:* Java NIO provides modern, efficient, non-blocking file operations, better error handling with specific exceptions, and streamlined path manipulation via Paths.

