# Practical 13 - Part B: MiniBank Generic Repository Pattern with Reflection @Id

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v13.0`

---

## 1. Objective & Problem Overview
Practical 13 designs an enterprise-grade Generic Repository pattern (Repository<T>). It dynamically scans entities using Reflection to discover primary keys annotated with @Id, providing clean, reusable CRUD abstraction for banking models.

---

## 2. Core OOP Principles & Java Features
- **Generics (Type Parameters)**
- **Repository Pattern**
- **Reflection Metadata Discovery**
- **Decoupled Architecture**
- **CRUD Abstraction**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-13
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: What are the advantages of the Generic Repository pattern?**  
*A:* It abstracts data storage operations behind a uniform type-safe interface, decouples domain logic from persistence mechanisms, and eliminates duplicated CRUD code across entity types.

**Q: How does type erasure affect Java Generics at runtime?**  
*A:* Java compiler replaces generic type parameters with their upper bounds (or Object) during compilation, so type parameters cannot be directly instantiated at runtime (e.g. new T() is invalid).

