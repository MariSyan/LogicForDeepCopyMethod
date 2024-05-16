Deep Copy Utility in Java
Overview
This project provides a utility to perform deep copying of objects in Java. The deep copy method handles objects of arbitrary complexity, including nested objects, arrays, collections, and maps. It avoids using java.io.Serializable and java.lang.Cloneable, relying instead on Java's reflection capabilities to inspect and copy object fields.

Features
Deep copy of complex objects including arrays, collections, and maps.
Handles cyclic references to avoid infinite loops.
Uses reflection to access and copy fields.
Manages classes without no-arg constructors using UnsafeAllocator.
Requirements
Java 17 or above
Usage
Deep Copy Utility
The CopyUtils class provides the deepCopy method for creating deep copies of objects.