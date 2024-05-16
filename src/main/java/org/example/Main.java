package org.example;

import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


        class Man {
            private String name;
            private int age;
            private List<String> favoriteBooks;

            public Man(String name, int age, List<String> favoriteBooks) {
                this.name = name;
                this.age = age;
                this.favoriteBooks = favoriteBooks;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public List<String> getFavoriteBooks() {
                return favoriteBooks;
            }
            public void setFavoriteBooks(List<String> favoriteBooks) {
                this.favoriteBooks = favoriteBooks;
            }

            @Override
            public String toString() {
                return "Man{name='" + name + "', age=" + age + ", favoriteBooks=" + favoriteBooks + '}';
            }
        }

        List<String> books = new ArrayList<>();
        books.add("Book1");
        books.add("Book2");

        Man man = new Man("John Doe", 30, books);

        Man copy = (Man) CopyUtils.deepCopy(man);

        // Testing the deep copy
        System.out.println("Original: " + man);
        System.out.println("Copy: " + copy);

        // Modifying the copy to ensure deep copy
        copy.setName("Jane Doe");
        copy.setAge(25);
        copy.getFavoriteBooks().add("Book3");

        System.out.println("Modified Copy: " + copy);
        System.out.println("Original After Copy Modification: " + man);
    }


}