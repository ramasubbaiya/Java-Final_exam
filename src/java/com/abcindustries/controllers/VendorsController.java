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
package com.abcindustries.controllers;

import com.abcindustries.entities.Vendor;
import com.abcindustries.utilities.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class VendorsController {
    // TODO: I guess this will be similar to the ProductsController, right? yes
    
    //Declaring Collection List - List of ventors
    List<Vendor> vendorList;
    
    //Constructor vendorsController that selects all the vendors
    public VendorsController() {
        vendorList = new ArrayList<>();
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM Vendors";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vendorList.add(new Vendor(
                        rs.getInt("vendorId"),
                        rs.getString("name"),
                        rs.getString("contactName"),
                        rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendorsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //List that selects all the vendors
    public List<Vendor> getAll() {
        return vendorList;
    }

    // Creates a JSON 
    public JsonArray toJson() {
        JsonArrayBuilder json = Json.createArrayBuilder();
        vendorList.stream().forEach((p) -> {
            json.add(p.toJson());
        });
        return json.build();
    }
    
    //Results by vendor Id
    public Vendor getById(int id) {
        Vendor result = null;
        for (int i = 0; i < vendorList.size() && result == null; i++) {
            if (vendorList.get(i).getVendorId() == id) {
                result = vendorList.get(i);
            }
        }
        return result;
    }

    //To insert a vendor
    public void add(Vendor v) throws SQLException {
        Connection conn = Database.getConnection();
        String query = "INSERT INTO Vendors (Name, ContactName, PhoneNumber) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, v.getName());
        pstmt.setString(2, v.getContactName());
        pstmt.setString(3, v.getPhoneNumber());
        int result = pstmt.executeUpdate();

        if (result == 1) {
            vendorList.add(v);
        }
    }
    
    //To update(edit) a vendor(existing)
    public void set(int id, Vendor v) throws SQLException {
        Connection conn = Database.getConnection();
        String query = "UPDATE Vendors SET name = ?, contactName = ?, phoneNumber = ?,  WHERE vendorId =  ";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, v.getName());
        pstmt.setString(2, v.getContactName());
        pstmt.setString(3, v.getPhoneNumber());
        int result = pstmt.executeUpdate();

        if (result == 1) {
            Vendor original = getById(id);
            original.setName(v.getName());
            original.setContactName(v.getContactName());
            original.setPhoneNumber(v.getPhoneNumber());
        }

    }
    
    //To delete a record(row) from vendor
    public void delete(int id) throws SQLException {
        Connection conn = Database.getConnection();
        String query = "DELETE FROM Vendors WHERE vendorId =  ";
        PreparedStatement pstmt = conn.prepareStatement(query);
        int result = pstmt.executeUpdate();

        if (result == 1) {
            Vendor original = getById(id);
            vendorList.remove(original);
        }
    }

}
