/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.mprasad.config;

import jakarta.annotation.sql.DataSourceDefinition;

/**
 *
 * @author monisha
 */

@DataSourceDefinition(
        name = "java:app/jdbc/itmd4515DS",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        serverName = "localhost",
        portNumber = 3306,
        databaseName = "itmd4515",
        user = "itmd4515",
        password = "itmd4515",
        properties = {
            "zeroDateTimeBehavior=CONVERT_TO_NULL",
            "useSSL=false"
        }
)
public class ITMD4515DataDourceConfig {
//    jdbc:mysql://localhost:3306/itmd4515?zeroDateTimeBehavior=CONVERT_TO_NULL    
}
