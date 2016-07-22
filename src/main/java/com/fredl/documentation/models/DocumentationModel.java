package com.fredl.documentation.models;

/**
 * @desc:   This is the master gson-model class. It defines how the output should look and function.
 *
 * @structure: The following is an example of the structure for a json documentation object for JavaScript
 *
 *          {
 *              function: "getNameFromDB(name, scheema)",
 *              parameters: [
 *                  {
 *                      name: "name",
 *                      type: String,
 *                      details: "Is the name we want to search for in the database"
 *                  },
 *                  {
 *                      name: "scheema",
 *                      type: Object,
 *                      details: "The scheema we want to search for the name in"
 *                  }
 *              ],
 *              description: "The function takes two arguments name and scheema. The function will then search in the database
 *                            for occurenses of the provided name",
 *              return: {
 *                  type: Object,
 *                  details: "If the name is found, then all the details about him is returned in an object. If not found,
 *                            return null"
 *              }
 *          }
 *
 */
public class DocumentationModel {



    public DocumentationModel(){

    }
}
