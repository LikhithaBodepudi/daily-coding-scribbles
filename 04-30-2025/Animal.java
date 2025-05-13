/*
Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly "first in, first out" basis.
 People must adopt either the "oldest" (based on arrival time) of all animals at the shelter, or they can select whether they wouc prefer a dog or a cat (and will receive the oldest animal of that type). 
 They cannot select which specific animal they would like. 
 Create the data structures to maintain this system and implement operations such as enqueue, dequeue Any dequeueDog, and dequeueCat. You may use the built-in LinkedList data structure.
*/ 
import java.util.LinkedList;

abstract class Animal {
    private int order;
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public boolean isOlderThan(Animal animal) {
        return this.order < animal.getOrder();
    }

    public String getName() {
        return name;
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }
}

class AnimalShelter {
    private LinkedList<Dog> dogs = new LinkedList<>();
    private LinkedList<Cat> cats = new LinkedList<>();
    private int order = 0;

    public void enqueue(Animal animal) {
        animal.setOrder(order++);
        if (animal instanceof Dog) {
            dogs.addLast((Dog) animal);
        } else if (animal instanceof Cat) {
            cats.addLast((Cat) animal);
        }
    }

    public Animal dequeueAny() {
        if (dogs.isEmpty()) return dequeueCat();
        if (cats.isEmpty()) return dequeueDog();

        Dog dog = dogs.peek();
        Cat cat = cats.peek();

        if (dog.isOlderThan(cat)) {
            return dequeueDog();
        } else {
            return dequeueCat();
        }
    }

    // Adopt the oldest dog
    public Dog dequeueDog() {
        return dogs.poll();
    }

    // Adopt the oldest cat
    public Cat dequeueCat() {
        return cats.poll();
    }

    public void printShelter() {
        System.out.println("Dogs in shelter:");
        for (Dog d : dogs) System.out.println("- " + d.getName() + " (order " + d.getOrder() + ")");
        System.out.println("Cats in shelter:");
        for (Cat c : cats) System.out.println("- " + c.getName() + " (order " + c.getOrder() + ")");
    }
}

public class Main {
    public static void main(String[] args) {
        AnimalShelter shelter = new AnimalShelter();

        shelter.enqueue(new Dog("Buddy"));
        shelter.enqueue(new Cat("Whiskers"));
        shelter.enqueue(new Dog("Charlie"));
        shelter.enqueue(new Cat("Luna"));

        System.out.println("Adopt any: " + shelter.dequeueAny().getName());
        System.out.println("Adopt dog: " + shelter.dequeueDog().getName());
        System.out.println("Adopt cat: " + shelter.dequeueCat().getName());

        shelter.printShelter();
    }
}
