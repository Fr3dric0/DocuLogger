# DocuLogger
Java program which locates and extracts function comments from programming languages, and stores the data in a JSON-document

## Implemented languages

### JavaScript
To a sertain degree. Will search for comments in the following format

```js
/**
 * @param:  {type}  name  Description
 *          {type}  name  New Description
 * Description of function
 * 
 * @return: {type}  Description
 */
function funcName(name, name) { }
```
#### Futuregoal
Use the JSDoc standard format, support Markdown and general stability optimization
