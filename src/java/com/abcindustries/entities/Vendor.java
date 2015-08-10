/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abcindustries.entities;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class Vendor {

    // TODO: Create this entity based on the documentation provided
    int vendorId;
    String name;
    String contactName;
    String phoneNumber;

    public Vendor() {
    }
    //Creates json obkect
    public Vendor(JsonObject json) {
        vendorId = json.getInt("vendorId");
        name = json.getString("name");
        contactName = json.getString("contactName");
        phoneNumber = json.getString("phoneNumber");
    }

    //Constructor
    public Vendor(int vendorId, String name, String contactName, String phoneNumber) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }
    
    //get vendor Id
    public int getVendorId() {
        return vendorId;
    }
    
    //set vendor Id
    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }
    //get vendor name
    public String getName() {
        return name;
    }
    
    //set vendor name
    public void setName(String name) {
        this.name = name;
    }
    
    //get vendor contact name
    public String getContactName() {
        return contactName;
    }
    
    //set vendor contact name
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    //get phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //set phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    //toJson 
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("vendorId", vendorId)
                .add("name", name)
                .add("contactName", contactName)
                .add("phoneNumber", phoneNumber)
                .build();
    }

}
