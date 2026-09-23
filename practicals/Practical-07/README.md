# Practical 07 - Part B: MiniBank Runtime Metadata & Annotation Validator

**Course:** Object Oriented Programming (CEUE203)  
**Student:** RUDRA MODI (Enrollment: 25DCS058)  
**Institute:** DEPSTAR, CHARUSAT University  
**Milestone Version:** `v7.0`

---

## 1. Objective & Problem Overview
Practical 7 introduces custom annotations to declare entity constraints (@Id, @Positive, @MaxLength). An AnnotationValidator engine uses the Java Reflection API at runtime to inspect private fields and enforce declarative validation rules.

---

## 2. Core OOP Principles & Java Features
- **Custom Annotations**
- **RetentionPolicy.RUNTIME**
- **Java Reflection API**
- **Field Inspection**
- **Declarative Validation**


---

## 3. How to Compile & Run
To compile and execute this practical's standalone implementation:
```bash
cd practicals/Practical-07
javac PartB.java
java PartB
```

---

## 4. Key Viva Questions & Answers
**Q: Why must custom validation annotations have RUNTIME retention?**  
*A:* By default, annotations have CLASS retention and are discarded by the JVM; RUNTIME retention preserves them in bytecode for inspection via Reflection at runtime.

**Q: How does field.setAccessible(true) work in Reflection?**  
*A:* It suppresses Java language access control checks, allowing the reflection framework to read or validate private field values dynamically.

