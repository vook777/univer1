package com.foxminded.univer.models;

public class Student {

    private Integer id;
    private String studentCardNumber;
    private String firstName;
    private String lastName;
    private Integer groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(String studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((studentCardNumber == null) ? 0 : studentCardNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (studentCardNumber == null) {
            if (other.studentCardNumber != null)
                return false;
        } else if (!studentCardNumber.equals(other.studentCardNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [ID = " + id + ", Name = " + firstName + ", Last Name = " + lastName + ", Student Card Number = "
                + studentCardNumber + ", Group ID = " + groupId + "]";
    }
}
