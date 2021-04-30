package com.yutian.contract.entity;

public enum FieldType {
    Text(1),Date(2),Decimal(3),AutoNumber(4),
    Option(5),PostCode(6),IDCard(7),PhoneNumber(8),
    Email(9),Table(10),List(11),Numbering(12);
    private int value;
    FieldType(int value)
    {
        
        this.value=value;
    }

    
    public int value() {  
        return this.value;  
    }  

    public static FieldType getFieldType(int type)
    {
        for (FieldType t : values()) {
            if (t.value==type)
               return t;
        }
        return null;
    }



}
