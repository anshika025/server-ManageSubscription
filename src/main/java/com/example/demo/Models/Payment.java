package com.example.demo.Models;

public class Payment {
    Items ItemsObject;

    // Getter Methods
    public Items getItems() {
        return ItemsObject;
    }

    // Setter Methods
    public void setItems(Items itemsObject) {
        this.ItemsObject = itemsObject;
    }

    public class Items {
        private String id;


        // Getter Methods
        public String getId() {
            return id;
        }

        // Setter Methods
        public void setId(String id) {
            this.id = id;
        }
    }
}
