// IPerson.aidl
package com.example.myapplication;

// Declare any non-default types here with import statements

interface IPerson {
    void setName(String name);
    void setSex(String sex);
    void setAge(int age);
    String getPerson();
}
