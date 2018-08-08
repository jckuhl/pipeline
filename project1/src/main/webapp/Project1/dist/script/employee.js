export default class Employee {
    
   /**
    * 
    * @param {string} fname 
    * @param {string} lname 
    * @param {string} employeeId 
    * @param {string} position 
    * @param {object} contact 
    * @param {number} id used to identify record in SQL, 0 if not yet pulled or put to DB
    */
    constructor(fname, lname, employeeId, position, street, city, state, zip, phone, email, isManager, id = 0) {
        this.id = id;
        this.fname = fname.slice(0,1).toUpperCase() + fname.slice(1);
        this.lname = lname.slice(0,1).toUpperCase() + lname.slice(1);
        this.fullname = fname + ' ' + lname;
        this.employeeId = employeeId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.isManager = isManager;
        this.position = position;
    }
}